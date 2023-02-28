package com.gutefind.mobile.location;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.nexenio.bleindoorpositioning.IndoorPositioning;
import com.nexenio.bleindoorpositioning.ble.advertising.AdvertisingPacket;
import com.nexenio.bleindoorpositioning.ble.beacon.Beacon;
import com.nexenio.bleindoorpositioning.ble.beacon.BeaconManager;
import com.nexenio.bleindoorpositioning.ble.beacon.BeaconUpdateListener;
import com.nexenio.bleindoorpositioning.ble.beacon.IBeacon;
import com.nexenio.bleindoorpositioning.ble.beacon.filter.IBeaconFilter;
import com.nexenio.bleindoorpositioning.location.Location;
import com.nexenio.bleindoorpositioning.location.LocationListener;
import com.nexenio.bleindoorpositioning.location.provider.LocationProvider;
import com.polidea.rxandroidble.RxBleClient;
import com.polidea.rxandroidble.scan.ScanResult;
import com.polidea.rxandroidble.scan.ScanSettings;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import rx.Observer;
import rx.Subscription;

public class BluetoothClient {

    public interface BluetoothClientCallback {

        void onLocationChanged(Location location);

    }

    private static Logger log = LoggerFactory.getLogger(BluetoothClient.class);
    private static IBeaconFilter uuidFilter = new IBeaconFilter(UUID.fromString("12345678-abcd-abcd-abcd-12345678abcd"));

    private static volatile BluetoothClient INSTANCE;
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private RxBleClient rxBleClient;
    private Subscription scanningSubscription;
    private static BluetoothClientCallback bluetoothClientCallback;

    private BluetoothClient() {
    }

    public static BluetoothClient getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BluetoothClient();
        }
        return INSTANCE;
    }

    public static void initialize(@NonNull Context context) {
        log.debug("Initialize");
        BluetoothClient instance = getInstance();
        instance.rxBleClient = RxBleClient.create(context);
        instance.bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        instance.bluetoothAdapter = instance.bluetoothManager.getAdapter();
        if (instance.bluetoothAdapter == null) {
            log.error("Bluetooth adapter is not available");
        }
    }

    public static void startScanning() {
        if (isScanning()) {
            return;
        }

        final BluetoothClient instance = getInstance();
        log.debug("Starting to scan for beacons");
        ScanSettings scanSettings = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES).build();

        instance.scanningSubscription = instance.rxBleClient.scanBleDevices(scanSettings).subscribe(new Observer<ScanResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                log.error("Bluetooth scanning error", e);
            }

            @Override
            public void onNext(ScanResult scanResult) {
                instance.processScanResult(scanResult);
            }
        });

        // initialize the indoor positioning singleton
        IndoorPositioning.getInstance().setIndoorPositioningBeaconFilter(uuidFilter);
        IndoorPositioning.registerLocationListener(new LocationListener() {
            @Override
            public void onLocationUpdated(LocationProvider locationProvider, Location location) {
                // log.debug("onLocationUpdated: lat: {}, lng: {}", location.getLatitude(), location.getLongitude());
                if (null != bluetoothClientCallback) {
                    bluetoothClientCallback.onLocationChanged(location);
                }
            }
        });
    }

    public static void stopScanning() {
        if (!isScanning()) {
            return;
        }
        BluetoothClient instance = getInstance();
        log.error("Stopping to scan for beacons");
        instance.scanningSubscription.unsubscribe();
    }

    public static boolean isBluetoothEnabled() {
        BluetoothClient instance = getInstance();
        return instance.bluetoothAdapter != null && instance.bluetoothAdapter.isEnabled();
    }

    private void processScanResult(@NonNull ScanResult scanResult) {
        String macAddress = scanResult.getBleDevice().getMacAddress();
        byte[] advertisingData = scanResult.getScanRecord().getBytes();
        int rssi = scanResult.getRssi();

        AdvertisingPacket advertisingPacket = BeaconManager.processAdvertisingData(macAddress, advertisingData, rssi);

        if (advertisingPacket != null) {
            Beacon beacon = BeaconManager.getBeacon(macAddress, advertisingPacket);
            if (beacon instanceof IBeacon && !beacon.hasLocation()) {
                log.debug("setting location provider for beacon with mac: {}", macAddress);
                beacon.setLocationProvider(BeaconLocationProvider.getBeaconProvider((IBeacon) beacon));
            }
        }
    }

    /*
    This method can provide beacon updates with various information.
    Sample log:
    D/c.g.m.l.BluetoothClient: 2023-02-17 19:47:13,937 DEBUG [main                ] beacon with mac: C5:6D:B7:41:1E:71, distance: 2.072455
    D/c.g.m.l.BluetoothClient: 2023-02-17 19:47:15,980 DEBUG [main                ] beacon with mac: E4:0E:35:CC:3A:6F, distance: 3.1622777
    D/c.g.m.l.BluetoothClient: 2023-02-17 19:47:16,826 DEBUG [main                ] beacon with mac: DF:8F:BE:ED:3C:8C, distance: 3.414549
     */
    private static void testBeaconUpdateListener() {
        BeaconManager.registerBeaconUpdateListener(new BeaconUpdateListener() {
            @Override
            public void onBeaconUpdated(Beacon beacon) {
                log.debug("beacon with mac: {}, distance: {} ", beacon.getMacAddress(), beacon.getDistance());
            }
        });
    }

    public static void setBluetoothClientCallback(BluetoothClientCallback bluetoothClientCallback) {
        BluetoothClient.bluetoothClientCallback = bluetoothClientCallback;
    }

    public static boolean isScanning() {
        Subscription subscription = getInstance().scanningSubscription;
        return subscription != null && !subscription.isUnsubscribed();
    }

}

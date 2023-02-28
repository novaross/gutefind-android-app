package com.gutefind.mobile.ui.map;

import com.gutefind.mobile.location.BluetoothClient;
import com.gutefind.mobile.ui.MainActivity;
import com.nexenio.bleindoorpositioning.IndoorPositioning;
import com.nexenio.bleindoorpositioning.ble.advertising.IndoorPositioningAdvertisingPacket;
import com.nexenio.bleindoorpositioning.ble.beacon.BeaconManager;
import com.nexenio.bleindoorpositioning.ble.beacon.BeaconUpdateListener;
import com.nexenio.bleindoorpositioning.ble.beacon.filter.IBeaconFilter;
import com.nexenio.bleindoorpositioning.location.Location;
import com.nexenio.bleindoorpositioning.location.LocationListener;
import com.nexenio.bleindoorpositioning.location.provider.LocationProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class MapModel implements BluetoothClient.BluetoothClientCallback {

    public interface MapModelCallback {
        void onLocationChanged(Location location);
    }

    private Logger log = LoggerFactory.getLogger(MapModel.class);

    protected BeaconManager beaconManager = BeaconManager.getInstance();
    protected BeaconUpdateListener beaconUpdateListener;
    private MapModelCallback mapModelCallback;
    protected LocationListener deviceLocationListener;

    public MapModel(MapModelCallback mapModelCallback) {
        log.debug("MapModel created");
        this.mapModelCallback = mapModelCallback;
        initialize();
    }

    public void initialize() {
        log.debug("MapModel created");
        BluetoothClient.setBluetoothClientCallback(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        log.debug("onLocationUpdated in the callback: lat: {}, lng: {}", location.getLatitude(), location.getLongitude());
        if (null != mapModelCallback) {
            mapModelCallback.onLocationChanged(location);
        }
    }

//    public void shutDown() {
//        IndoorPositioning.unregisterLocationListener(deviceLocationListener);
//        BeaconManager.unregisterBeaconUpdateListener(beaconUpdateListener);
//    }


}

package com.gutefind.mobile.ui.map;

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

public class MapModel {

    Logger log = LoggerFactory.getLogger(MapModel.class);

    protected BeaconManager beaconManager = BeaconManager.getInstance();
    protected BeaconUpdateListener beaconUpdateListener;
    protected IBeaconFilter uuidFilter = new IBeaconFilter(UUID.fromString("12345678-abcd-abcd-abcd-12345678abcd"));
    protected LocationListener deviceLocationListener;

    public MapModel() {
        log.debug("MapModel created");
        initialize();
    }

    public void initialize() {
        log.debug("MapModel created");
        IndoorPositioning.getInstance().setIndoorPositioningBeaconFilter(uuidFilter);
        // IndoorPositioning.registerLocationListener(deviceLocationListener);
        IndoorPositioning.registerLocationListener(new LocationListener() {
            @Override
            public void onLocationUpdated(LocationProvider locationProvider, Location location) {
                log.debug("onLocationUpdated: lat: {}, lng: {}", location.getLatitude(), location.getLongitude());
            }
        });
    }

//    public void shutDown() {
//        IndoorPositioning.unregisterLocationListener(deviceLocationListener);
//        BeaconManager.unregisterBeaconUpdateListener(beaconUpdateListener);
//    }


}

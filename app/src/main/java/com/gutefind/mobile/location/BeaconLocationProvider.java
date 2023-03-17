package com.gutefind.mobile.location;

import com.nexenio.bleindoorpositioning.ble.beacon.IBeacon;
import com.nexenio.bleindoorpositioning.location.Location;
import com.nexenio.bleindoorpositioning.location.provider.IBeaconLocationProvider;
import com.tencent.mmkv.MMKV;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
Assign a known location for each of the beacons
 */
public class BeaconLocationProvider {

    static Logger log = LoggerFactory.getLogger(BeaconLocationProvider.class);

    public static IBeaconLocationProvider<IBeacon> getBeaconProvider(IBeacon iBeacon) {
        final Location beaconLocation = new Location();
        log.debug("beacon minor is: {}", iBeacon.getMinor());
        MMKV kv = MMKV.defaultMMKV();
        switch (iBeacon.getMinor()) {
            case 1: {
                beaconLocation.setLatitude(kv.decodeDouble("BEACON_TOP_LEFT_LAT"));
                beaconLocation.setLongitude(kv.decodeDouble("BEACON_TOP_LEFT_LNG"));
                beaconLocation.setAltitude(36);
                break;
            }
            case 2: {
                beaconLocation.setLatitude(kv.decodeDouble("BEACON_TOP_RIGHT_LAT"));
                beaconLocation.setLongitude(kv.decodeDouble("BEACON_TOP_RIGHT_LNG"));
                beaconLocation.setAltitude(36);
                break;
            }
            case 3: {
                beaconLocation.setLatitude(kv.decodeDouble("BEACON_BOTTOM_RIGHT_LAT"));
                beaconLocation.setLongitude(kv.decodeDouble("BEACON_BOTTOM_RIGHT_LNG"));
                beaconLocation.setAltitude(36);
                break;
            }
            case 4: {
                beaconLocation.setLatitude(kv.decodeDouble("BEACON_BOTTOM_LEFT_LAT"));
                beaconLocation.setLongitude(kv.decodeDouble("BEACON_BOTTOM_LEFT_LNG"));
                beaconLocation.setAltitude(36);
                break;
            }
        }

        return new IBeaconLocationProvider<IBeacon>(iBeacon) {

            @Override
            protected void updateLocation() {
                this.location = beaconLocation;
            }

            @Override
            protected boolean canUpdateLocation() {
                return true;
            }
        };

    }

}

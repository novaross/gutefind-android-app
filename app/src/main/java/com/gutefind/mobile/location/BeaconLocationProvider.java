package com.gutefind.mobile.location;

import com.gutefind.mobile.util.Constants;
import com.nexenio.bleindoorpositioning.ble.beacon.IBeacon;
import com.nexenio.bleindoorpositioning.location.Location;
import com.nexenio.bleindoorpositioning.location.provider.IBeaconLocationProvider;

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
        switch (iBeacon.getMinor()) {
            case 1: {
                beaconLocation.setLatitude(Constants.BEACON_TOP_LEFT_LAT);
                beaconLocation.setLongitude(Constants.BEACON_TOP_LEFT_LNG);
                beaconLocation.setAltitude(36);
                break;
            }
            case 2: {
                beaconLocation.setLatitude(Constants.BEACON_TOP_RIGHT_LAT);
                beaconLocation.setLongitude(Constants.BEACON_TOP_RIGHT_LNG);
                beaconLocation.setAltitude(36);
                break;
            }
            case 3: {
                beaconLocation.setLatitude(Constants.BEACON_BOTTOM_RIGHT_LAT);
                beaconLocation.setLongitude(Constants.BEACON_BOTTOM_RIGHT_LNG);
                beaconLocation.setAltitude(36);
                break;
            }
//            case 4: {
//                beaconLocation.setLatitude(32.083363);
//                beaconLocation.setLongitude(34.773763);
//                beaconLocation.setAltitude(36);
//                break;
//            }
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

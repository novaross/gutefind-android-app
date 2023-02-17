package com.gutefind.mobile.location;

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
                beaconLocation.setLatitude(32.083400);
                beaconLocation.setLongitude(34.773763);
                beaconLocation.setAltitude(36);
                break;
            }
            case 2: {
                beaconLocation.setLatitude(32.083386);
                beaconLocation.setLongitude(34.773868);
                beaconLocation.setAltitude(36);
                break;
            }
            case 3: {
                beaconLocation.setLatitude(32.083349);
                beaconLocation.setLongitude(34.773856);
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

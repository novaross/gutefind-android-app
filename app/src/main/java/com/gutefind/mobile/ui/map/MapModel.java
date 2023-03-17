package com.gutefind.mobile.ui.map;

import com.gutefind.mobile.location.BluetoothClient;
import com.nexenio.bleindoorpositioning.location.Location;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class MapModel implements BluetoothClient.BluetoothClientCallback {

    public interface MapModelCallback {
        void onLocationChanged(Location location);
    }

    private final Logger log = LoggerFactory.getLogger(MapModel.class);

    private final MapModelCallback mapModelCallback;
    private Queue<Location> lastLocationQueue = new LinkedList<>();

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
            mapModelCallback.onLocationChanged(getAverageLocation(location, 5));
        }
    }

    /**
     * Calculate an average location based on the amount of last locations
     * <p>
     * get all the locations from the queue
     * calculate the average of the latitude
     * calculate the average of the longitude
     * set the average to the average location
     * return to the map the average location
     *
     * @param location the most recent location
     * @param average  number of last locations to take into account
     * @return average location based on the number of last locations
     */
    private Location getAverageLocation(Location location, int average) {

        // add > x > x > x > remove
        lastLocationQueue.add(location);
        if (lastLocationQueue.size() > average) {
            lastLocationQueue.remove();
        }

        Iterator<Location> iterator = lastLocationQueue.iterator();
        double lat = 0;
        double lng = 0;
        while (iterator.hasNext()) {
            Location nextLocation = iterator.next();
            lat += nextLocation.getLatitude();
            lng += nextLocation.getLongitude();
        }

        double averageLat = lat / lastLocationQueue.size();
        double averageLng = lng / lastLocationQueue.size();

        log.debug("average location, original lat: {}, original lng: {}", location.getLatitude(), location.getLongitude());
        log.debug("average location,  average lat: {}, original lng: {}", averageLat, averageLng);

        return new Location(averageLat, averageLng);
    }


}

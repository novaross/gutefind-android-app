package com.gutefind.mobile.ui.map;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.nexenio.bleindoorpositioning.location.Location;

public class MapPresenter implements MapModel.MapModelCallback {

    private FragmentMapViewInt mapView;
    private MapModel mapModel;
    private Context context;

    public MapPresenter(FragmentMapView mapView, Context context) {
        this.mapView = mapView;
        this.context = context;
        mapModel = new MapModel(this);
    }

    public void setSomeText() {
        mapView.setText("test");
    }

    @Override
    public void onLocationChanged(Location location) {

        mapView.drawDevice(location);
    }
}

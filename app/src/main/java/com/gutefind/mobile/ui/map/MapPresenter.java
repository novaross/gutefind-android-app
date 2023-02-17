package com.gutefind.mobile.ui.map;

import android.content.Context;

import androidx.fragment.app.Fragment;

public class MapPresenter {

    private FragmentMapViewInt mapView;
    private MapModel mapModel;
    private Context context;

    public MapPresenter(FragmentMapView mapView, Context context) {
        this.mapView = mapView;
        this.context = context;
        mapModel = new MapModel();
    }

    public void setSomeText() {
        mapView.setText("test");
    }

}

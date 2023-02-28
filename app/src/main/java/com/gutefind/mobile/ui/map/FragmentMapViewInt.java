package com.gutefind.mobile.ui.map;

import android.graphics.PointF;

import com.nexenio.bleindoorpositioning.location.Location;

public interface FragmentMapViewInt {

    void setText(String text);

    void drawDevice(Location location);

}

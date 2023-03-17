package com.gutefind.mobile.util;

import com.gutefind.mobile.R;
import com.gutefind.mobile.ui.products.Product;

import java.util.ArrayList;
import java.util.List;

public final class Constants {

    private void Constant() {
        // No need to instantiate the class, we can hide its constructor
    }

    // below set of constanta are the four corners of the living room
    public static double BEACON_TOP_LEFT_LAT = 32.083400;
    public static double BEACON_TOP_LEFT_LNG = 34.773763;

    public static double BEACON_TOP_RIGHT_LAT = 32.083386;
    public static double BEACON_TOP_RIGHT_LNG = 34.773868;

    public static double BEACON_BOTTOM_RIGHT_LAT = 32.083349;
    public static double BEACON_BOTTOM_RIGHT_LNG = 34.773856;

    public static double BEACON_BOTTOM_LEFT_LAT = 32.083363;
    public static double BEACON_BOTTOM_LEFT_LNG = 34.773763;

    public static final List<Product> PRODUCT_LIST = new ArrayList<Product>() {{
        add(new Product(1, "מסטיקים", R.drawable.item_gums));
        add(new Product(2, "תה", R.drawable.item_tea));
        add(new Product(3, "גפרורים", R.drawable.item_matches));
        add(new Product(4, "קיסמים", R.drawable.item_toothpics));
    }};


}

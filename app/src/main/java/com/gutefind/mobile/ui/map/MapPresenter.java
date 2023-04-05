package com.gutefind.mobile.ui.map;

import android.content.Context;

import com.gutefind.mobile.ui.products.Product;
import com.gutefind.mobile.util.Constants;
import com.nexenio.bleindoorpositioning.location.Location;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapPresenter implements MapModel.MapModelCallback {

    private Logger log = LoggerFactory.getLogger(MapPresenter.class);
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

    public void navigateToProduct(int productId) {
        Product selectedProduct = Constants.PRODUCT_LIST.stream().filter(product -> productId == product.getId()).findAny().orElse(null);
        if (null != selectedProduct) {
            log.debug("found product: {}", selectedProduct.toString());
            // mapView.displayProduct(selectedProduct);
            mapView.displayFourProducts(Constants.PRODUCT_LIST);
        } else {
            log.error("product with id: {}, not found in the product list", productId);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mapView.displayDeviceDot(location);
    }
}

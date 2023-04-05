package com.gutefind.mobile.ui.map;

import com.gutefind.mobile.ui.products.Product;
import com.nexenio.bleindoorpositioning.location.Location;

import java.util.List;

public interface FragmentMapViewInt {

    void setText(String text);

    void displayDeviceDot(Location location);

    void displayProduct(Product product);

    void displayFourProducts(List<Product> productList);

}

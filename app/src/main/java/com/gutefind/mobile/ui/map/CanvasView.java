package com.gutefind.mobile.ui.map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.gutefind.mobile.R;
import com.gutefind.mobile.ui.products.Product;
import com.gutefind.mobile.util.DisplayUtil;
import com.nexenio.bleindoorpositioning.location.Location;
import com.nexenio.bleindoorpositioning.location.projection.CanvasProjection;
import com.tencent.mmkv.MMKV;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class used to draw the beacon on a map
 * Author Ruslan Spivak, 2023-02-21
 */
public class CanvasView extends View {

    private Logger log = LoggerFactory.getLogger(CanvasView.class);

    private Paint backgroundPaint;
    private Paint deviceDotPaint;
    private int canvasWidth;
    private int canvasHeight;
    private PointF canvasCenter;

    private float pixelsPerDip = DisplayUtil.convertDipToPixels(1);
    private CanvasProjection canvasProjection;
    private Location deviceLocation;

    private Product product;


    public CanvasView(Context context) {
        super(context);
        initialize();
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }


    private void initialize() {
        log.debug("initialize canvas");

        // initialize the background
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);
        backgroundPaint.setStyle(Paint.Style.FILL);

        // device location paint
        deviceDotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        deviceDotPaint.setStyle(Paint.Style.FILL);
        deviceDotPaint.setColor(Color.GREEN);

        canvasProjection = new CanvasProjection();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        log.debug("onSizeChanged");
        this.canvasWidth = width;
        this.canvasHeight = height;
        this.canvasCenter = new PointF(width / 2f, height / 2f);
        canvasProjection.setCanvasWidth(width);
        canvasProjection.setCanvasHeight(height);
        updateEdgeLocations();
        log.debug("canvas width: {}, canvas height {}", canvasWidth, canvasHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // this is triggered each time invalidate is called
        log.debug("onDraw");
        drawBackground(canvas);
        drawProduct(canvas);
        drawDevice(canvas);
    }

    private void drawDevice(Canvas canvas) {
        if (null == deviceLocation) {
            return;
        }
        PointF deviceCenter = getPointFromLocation(deviceLocation);
        log.debug("drawDevice, deviceCenter: x:{}, y:{}", deviceCenter.x, deviceCenter.y);
        if (deviceCenter.x > canvasWidth) {
            deviceCenter.x = canvasWidth;
        }
        if (deviceCenter.x < 0) {
            deviceCenter.x = 0;
        }
        if (deviceCenter.y > canvasHeight) {
            deviceCenter.y = canvasHeight;
        }
        if (deviceCenter.y < 0) {
            deviceCenter.y = 0;
        }

        int rectHalfSize = 80;
        Rect rect = new Rect();
        rect.top = Math.round(deviceCenter.y - rectHalfSize);
        rect.left = Math.round(deviceCenter.x - rectHalfSize);
        rect.right = Math.round(deviceCenter.x + rectHalfSize);
        rect.bottom = Math.round(deviceCenter.y + rectHalfSize);

        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.nav_marker);

        if (null != drawable) {
            drawable.setBounds(rect);
            drawable.draw(canvas);
        }

    }

    private void updateEdgeLocations() {
        MMKV kv = MMKV.defaultMMKV();
        // this is actually the location of the top left beacon
        Location topLeftLocation = new Location();
        topLeftLocation.setLatitude(kv.decodeDouble("BEACON_TOP_LEFT_LAT"));
        topLeftLocation.setLongitude(kv.decodeDouble("BEACON_TOP_LEFT_LNG"));

        // this is actually the location of the bottom right beacon
        Location bottomRightLocation = new Location();
        bottomRightLocation.setLatitude(kv.decodeDouble("BEACON_BOTTOM_RIGHT_LAT"));
        bottomRightLocation.setLongitude(kv.decodeDouble("BEACON_BOTTOM_RIGHT_LNG"));

        canvasProjection.setTopLeftLocation(topLeftLocation);
        canvasProjection.setBottomRightLocation(bottomRightLocation);
        log.debug("updated edge locations, invalidating");
        invalidate();
    }

    public void setDeviceLocation(Location location) {
        this.deviceLocation = location;
    }

    private void drawProduct(Canvas canvas) {
        if (null == product) {
            return;
        }
        Rect rect = getProductLocationBasedOnProduct(product.getId());
        Drawable drawable = ContextCompat.getDrawable(getContext(), product.getDrawableId());

        if (null != drawable) {
            drawable.setBounds(rect);
            drawable.draw(canvas);
        }
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawRect(0, 0, canvasWidth, canvasHeight, backgroundPaint);
    }

    private PointF getPointFromLocation(Location location) {
        if (location == null || !location.hasLatitudeAndLongitude()) {
            return new PointF(0, 0);
        }
        float x = canvasProjection.getXFromLocation(location);
        float y = canvasProjection.getYFromLocation(location);
        log.debug("getPointFromLocation: x:{}, y:{}", x, y);
        return new PointF(x, y);
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Get product dimensions based on the product id.
     * This is for POC only, the products are displayed on the device in predetermined locations.
     *
     * @param productId product id for which to get the rect location
     * @return rect object with the dimensions of each of the sides
     */
    private Rect getProductLocationBasedOnProduct(int productId) {
        Rect rect = new Rect();
        switch (productId) {
            // Gum, location: top left
            case 1: {
                rect.top = 0;
                rect.left = 0;
                rect.right = Math.round(DisplayUtil.convertDipToPixels(100));
                rect.bottom = Math.round(DisplayUtil.convertDipToPixels(100));
                break;
            }
            // Tea, location: top right
            case 2: {
                rect.top = 0;
                rect.left = canvasWidth - Math.round(DisplayUtil.convertDipToPixels(100));
                rect.right = canvasWidth;
                rect.bottom = Math.round(DisplayUtil.convertDipToPixels(100));
                break;
            }
            // Matches, location: bottom right
            case 3: {
                rect.top = canvasHeight - Math.round(DisplayUtil.convertDipToPixels(100));
                rect.left = canvasWidth - Math.round(DisplayUtil.convertDipToPixels(100));
                rect.right = canvasWidth;
                rect.bottom = canvasHeight;
                break;
            }
            // Toothpicks, location: bottom left
            case 4: {
                rect.top = canvasHeight - Math.round(DisplayUtil.convertDipToPixels(100));
                rect.left = 0;
                rect.right = Math.round(DisplayUtil.convertDipToPixels(100));
                rect.bottom = canvasHeight;
                break;
            }
        }
        return rect;
    }

}

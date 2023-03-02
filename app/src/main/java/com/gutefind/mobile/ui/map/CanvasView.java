package com.gutefind.mobile.ui.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.gutefind.mobile.R;
import com.gutefind.mobile.ui.products.Product;
import com.gutefind.mobile.util.Constants;
import com.gutefind.mobile.util.DisplayUtil;
import com.nexenio.bleindoorpositioning.location.Location;
import com.nexenio.bleindoorpositioning.location.projection.CanvasProjection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class used to draw the beacon on a map
 * Author Ruslan Spivak, 2023-02-21
 */
public class CanvasView extends View {

    private Logger log = LoggerFactory.getLogger(CanvasView.class);

    private Paint textPaint;
    private Paint backgroundPaint;
    private Paint deviceRangePaint;
    private Paint whiteFillPaint;
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
        // initialize text properties
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(pixelsPerDip * 8);

        // initialize the background
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);
        backgroundPaint.setStyle(Paint.Style.FILL);
        canvasProjection = new CanvasProjection();

        deviceRangePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(Color.RED);
        deviceRangePaint.setStyle(Paint.Style.FILL);
        deviceRangePaint.setAlpha(25);

        whiteFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        whiteFillPaint.setStyle(Paint.Style.FILL);
        whiteFillPaint.setColor(Color.WHITE);
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
        log.debug("onDraw");
        drawBackground(canvas);
        // when is this triggered?
        drawDevice(canvas);
        drawProduct(canvas);
    }

    private void drawDevice(Canvas canvas) {
        // this is the device location, need to pass it somehow to the class
        if (null == deviceLocation) {
            return;
        }
        PointF deviceCenter = getPointFromLocation(deviceLocation);
        log.debug("drawDevice, deviceCenter: x:{}, y:{}", deviceCenter.x, deviceCenter.y);
        canvas.drawCircle(deviceCenter.x, deviceCenter.y, 10, whiteFillPaint);
        canvas.drawCircle(canvasCenter.x, canvasCenter.y, 10, whiteFillPaint);
    }

    private void updateEdgeLocations() {
        // this is actually the location of the top left beacon
        Location topLeftLocation = new Location();
        topLeftLocation.setLatitude(Constants.BEACON_TOP_LEFT_LAT);
        topLeftLocation.setLongitude(Constants.BEACON_TOP_LEFT_LNG);

        // this is actually the location of the bottom right beacon
        Location bottomRightLocation = new Location();
        bottomRightLocation.setLatitude(Constants.BEACON_BOTTOM_RIGHT_LAT);
        bottomRightLocation.setLongitude(Constants.BEACON_BOTTOM_RIGHT_LNG);

        canvasProjection.setTopLeftLocation(topLeftLocation);
        canvasProjection.setBottomRightLocation(bottomRightLocation);
        log.debug("updated edge locations, invalidating");
        invalidate();
    }

    public void setDeviceLocation(Location location) {
        this.deviceLocation = location;
    }

    private void drawProduct(Canvas canvas) {
        Drawable drawable = getResources().getDrawable(product.getDrawableId(), null);
        drawable.setBounds(0, 0, Math.round(DisplayUtil.convertDipToPixels(100)), Math.round(DisplayUtil.convertDipToPixels(100)));
        drawable.draw(canvas);
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

}

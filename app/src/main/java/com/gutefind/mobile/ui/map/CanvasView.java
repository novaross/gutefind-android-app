package com.gutefind.mobile.ui.map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.gutefind.mobile.R;
import com.gutefind.mobile.util.DisplayUtil;
import com.nexenio.bleindoorpositioning.location.Location;
import com.nexenio.bleindoorpositioning.location.projection.CanvasProjection;

/**
 * A class used to draw the beacon on a map
 * Author Ruslan Spivak, 2023-02-21
 */
public class CanvasView extends View {

    private Paint textPaint;
    private Paint backgroundPaint;
    private Paint deviceRangePaint;
    private int canvasWidth;
    private int canvasHeight;
    private PointF canvasCenter;
    private float pixelsPerDip = DisplayUtil.convertDipToPixels(1);
    private CanvasProjection canvasProjection;
    private Location deviceLocation;

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


    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        this.canvasWidth = width;
        this.canvasHeight = height;
        this.canvasCenter = new PointF(width / 2, height / 2);
        canvasProjection.setCanvasWidth(width);
        canvasProjection.setCanvasHeight(height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawDevice(canvas);
    }

    private void drawDevice(Canvas canvas) {
        // this is the device location, need to pass it somehow to the class
        Location location = new Location();
        PointF deviceCenter = getPointFromLocation(location);
        float locationAccuracy = (float) location.getAccuracy();

    }

    public void setDeviceLocation(Location location) {
        this.deviceLocation = location;

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
        return new PointF(x, y);
    }

}

package com.gama.mutamer.libs.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gama.mutamer.R;

public class MyCompassView extends SurfaceView implements
        SurfaceHolder.Callback {
    public static Bitmap compassDir, compassBg, arrowQibla, insideB, outsodeB, frame, boundaryDirArrow;
    public CanvasThread canvasthread;
    Context ctx;
    Bitmap compass;
    Bitmap compass_bg;
    float tmpangle = 0;
    float tmpangle1 = 0;
    int compasstype = -1;
    float speed = 5.0f;
    private float azimuth = 0;
    private float toangle = 0;

    public MyCompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx = context;
        getHolder().addCallback(this);
        setFocusable(true);
        init();
    }

    private void init() {
        compass = BitmapFactory.decodeResource(getResources(),
                R.drawable.compass_dir);
        compass_bg = BitmapFactory.decodeResource(getResources(),
                R.drawable.compass_bg);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // System.out.println("surfaceCreated");

        if (canvasthread == null
                || canvasthread.getState() == Thread.State.TERMINATED) {
            canvasthread = new CanvasThread(getHolder(), this);

        }
        canvasthread.setRunning(true);
        canvasthread.start();

    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // System.out.println("surfaceDestroyed");
        boolean retry = true;
        canvasthread.setRunning(false);
        while (retry) {
            try {
                canvasthread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }

    }

    @SuppressLint({"NewApi", "DrawAllocation"})
    @Override
    protected void onDraw(Canvas canvas) {
        // Paint paint = new Paint();
        if (canvas == null)
            return;
        // canvas.drawColor(Color.WHITE);
        drawCompass(canvas);
    }

    public float convertPixelsToDp(double dp) {
        return (float) (dp * ctx.getResources().getDisplayMetrics().density);
    }

    private void drawCompass(Canvas canvas) {
        if (compasstype < 2) {
            if (tmpangle < toangle) {
                tmpangle += speed;
                if (speed > 1) {
                    speed -= .1f;
                }
            } else if (tmpangle > toangle) {
                tmpangle = toangle;
            }
        } else {
            tmpangle = toangle;
        }

        if (compasstype == 1) {
            if (tmpangle1 > azimuth) {
                tmpangle1 -= speed;

            } else if (tmpangle1 < azimuth) {
                tmpangle1 = azimuth;
            }
        }

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        int l = 0;
        int t = 0;
        int w = (int) convertPixelsToDp(60);
        if (compasstype == 1) {
            w = (int) convertPixelsToDp(300);
        } else if (compasstype > 1) {
            w = (int) convertPixelsToDp(220);
        }
        Rect imrect = new Rect(l, t, w, w);
        // canvas.drawRect(imrect, paint);
        if (compasstype == 0 || compasstype == 2)
            canvas.drawBitmap(compass_bg, null, imrect, paint);
        else {
            canvas.save();
            canvas.rotate(tmpangle1, w / 2, w / 2);
            canvas.drawBitmap(compass_bg, null, imrect, paint);
            canvas.restore();
        }
        if (compasstype != 2) {
            canvas.save();
            canvas.rotate(azimuth + tmpangle, w / 2, w / 2);
            canvas.drawBitmap(compass, null, imrect, paint);
            canvas.restore();
        }

    }

    public void resetAminVal(int n) {
        if (compassDir == null)
            compassDir = BitmapFactory.decodeResource(getResources(),
                    R.drawable.compass_dir);
        if (compassBg == null)
            compassBg = BitmapFactory.decodeResource(getResources(),
                    R.drawable.compass_bg);
        if (arrowQibla == null)
            arrowQibla = BitmapFactory.decodeResource(getResources(),
                    R.drawable.arrow_qibla);
        if (insideB == null)
            insideB = BitmapFactory.decodeResource(getResources(),
                    R.drawable.inside_boundary);
        if (outsodeB == null)
            outsodeB = BitmapFactory.decodeResource(getResources(),
                    R.drawable.outside_boundary);
        if (frame == null)
            frame = BitmapFactory.decodeResource(getResources(),
                    R.drawable.frame);

        if (boundaryDirArrow == null)
            boundaryDirArrow = BitmapFactory.decodeResource(getResources(),
                    R.drawable.boundary_dir_arrow);
        speed = 5;
        compasstype = n;
        tmpangle = 0;
        tmpangle = 1;
        if (n == 0) {
            compass = compassDir;
            compass_bg = compassBg;
        } else if (n == 1) {
            compass = arrowQibla;
            compass_bg = frame;
        } else if (n == 2) {
            compass = arrowQibla;
            compass_bg = insideB;
        } else if (n == 3) {
            compass = boundaryDirArrow;
            compass_bg = outsodeB;
        }
        invalidate();
    }

    public void updateData(double toangle, float azimuth) {
        this.azimuth = azimuth;
        this.toangle = (float) toangle;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        invalidate();

        return true;

    }


}

package com.gama.mutamer.libs.views;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class CanvasThread extends Thread {
    private SurfaceHolder _surfaceHolder;
    private MyCompassView _panel;
    private boolean _run = false;

    public CanvasThread(SurfaceHolder surfaceHolder, MyCompassView panel) {
        _surfaceHolder = surfaceHolder;
        _panel = panel;
    }

    public void setRunning(boolean run) {
        _run = run;
    }

    public boolean getRunning() {
        return _run;
    }

    @SuppressLint("WrongCall")
    @Override
    public void run() {
    }

}

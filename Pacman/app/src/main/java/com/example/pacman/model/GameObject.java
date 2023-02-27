package com.example.pacman.model;

import android.graphics.Canvas;

import com.example.pacman.DemoSurfaceView;

public abstract class GameObject {

    protected DemoSurfaceView mView;
    protected Escenari mEscenari;

    public GameObject(DemoSurfaceView view) {
        mView = view;
        mEscenari = this.mView.getEscenari();
    }

    public abstract void onDraw(Canvas canvas);

    public abstract void tick();

}

package com.example.pacman;

import android.graphics.Canvas;
import android.util.Log;

public class SurfaceThread extends Thread{


    private static final int FPS = 60; // frames per second
    private int tempsFrame_Ns;
    private com.example.pacman.DemoSurfaceView mView;
    private boolean esticViu;

    SurfaceThread(com.example.pacman.DemoSurfaceView view){
        mView = view;
        esticViu = true;
        tempsFrame_Ns =  1000000000 / FPS;
    }

    @Override
    public void run() {
        super.run();
        try {
            long tempsFrame;
            long tempsIniciFrame;
            while (esticViu) {
                Canvas canvas = mView.getHolder().lockCanvas();
                if (canvas != null) {
                    tempsIniciFrame = System.nanoTime();
                    mView.tick();
                    mView.onPaintCasola(canvas);
                    tempsFrame = System.nanoTime() - tempsIniciFrame;
                    if (tempsFrame < tempsFrame_Ns) {
                        try {
                            Thread.sleep((int) ((tempsFrame_Ns - tempsFrame) / 1000000));
                        } catch (InterruptedException e) {
                        }
                    }
                    mView.getHolder().unlockCanvasAndPost(canvas); // !IMPORTANT
                }
            }
        }
        catch (Exception ex)
        {
           Log.e("XXX", "", ex);
        }
    }

    public void muerete() {
        esticViu = false;
    }
}

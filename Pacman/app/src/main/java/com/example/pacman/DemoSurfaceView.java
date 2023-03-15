package com.example.pacman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.pacman.model.Blinky;
import com.example.pacman.model.Clyde;
import com.example.pacman.model.Escenari;
import com.example.pacman.model.GameObject;
import com.example.pacman.model.Ghost;
import com.example.pacman.model.Inky;
import com.example.pacman.model.MovimentJoystick;
import com.example.pacman.model.Pacman;
import com.example.pacman.model.Pinky;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DemoSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceThread mThread;

    private SimpleDateFormat mFormatter;
    private Paint mPaint;
    private Escenari mEscenari;
    //------------------------------------------
    List<GameObject> mGameObjects;
    //------------------------------------------

    PointF posicioDelDit;
    MovimentJoystick mMoviment;

    public DemoSurfaceView(Context context) {
        this(context, null);
    }

    public DemoSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        mGameObjects = new ArrayList<>();

    }

    public MovimentJoystick getMovimentJoystick() {
        return mMoviment;
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) { //cuan acaba de baixar el dit
            posicioDelDit = new PointF(event.getX(), event.getY());
        } else if (event.getActionMasked() == MotionEvent.ACTION_UP) { //axeco el dit
            posicioDelDit = null;
            mMoviment = null;
        } else {
            //el dit s'esta arrsegant per la pantalla
            float x = event.getX() - posicioDelDit.x;
            float y = event.getY() - posicioDelDit.y;
            mMoviment = new MovimentJoystick(
                    (int)Math.signum(x) * (Math.abs(x) > Math.abs(y) ? 1 : 0),
                    (int)Math.signum(y) * (Math.abs(x) < Math.abs(y) ? 1 : 0));
        }

        return true;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

        // Creació de l'escenari de joc
        mEscenari = new Escenari(this);
        mGameObjects.add(mEscenari);

        mGameObjects.add(new Pacman(this, 45, 42, mEscenari.getMidaCella()));
        mGameObjects.add(new Blinky(this, 48, 46, mEscenari.getMidaCella()));
        mGameObjects.add(new Clyde(this, 48, 46, mEscenari.getMidaCella()));
        mGameObjects.add(new Pinky(this, 48, 46, mEscenari.getMidaCella()));
        mGameObjects.add(new Inky(this, 48, 46, mEscenari.getMidaCella()));

        mThread = new SurfaceThread(this);
        mThread.start(); // engega el fil
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        if(mThread!=null){
            mThread.muerete();
        }
    }

    public void onPaintCasola(Canvas canvas) {
        canvas.drawColor(Color.BLACK);

        for(GameObject b:mGameObjects){
            b.onDraw(canvas);
        }

    }

    public void tick() {
        for(GameObject b:mGameObjects){
            b.tick();
        }
    }

    public Escenari getEscenari() {
        return mEscenari;
    }

    public List<GameObject> GameObjects() {
        return mGameObjects;
    }
}

package com.example.pacman.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.pacman.DemoSurfaceView;
import com.example.pacman.R;

import java.util.ArrayList;
import java.util.List;

public class Pacman extends Sprite {

    //Distancia en pixels recorreguda per ticks

    private float pasEnPixels;
    protected MovimentJoystick mMove = new MovimentJoystick(0, 0);
    public MovimentJoystick ultimMoviment = new MovimentJoystick(0, 0);

    public Pacman(DemoSurfaceView view, int width, int height, int midaCella) {
        super(view, width, height, midaCella, midaCella);

        mPosicio = mEscenari.getPosicioIniciPacman();

        pasEnPixels = midaCella / 10.0f;

    }

    @Override
    public List<Integer> getSprites() {
        List<Integer> sprites = new ArrayList<>();
        sprites.add(R.drawable.pacman);
        return sprites;
    }

    @Override
    public void tick() {
        super.tick();

        if (mEscenari.esticALaCasella(this.mPosicio)){

            if (mEscenari.esticALaBocaDelTunel(mEscenari.getPosicioALaGraella(mPosicio))) {
                mPosicio = mEscenari.creuarTunel(mEscenari.getPosicioALaGraella(mPosicio));
            }

            if (mView.getMovimentJoystick() != null) {
                MovimentJoystick direccioDemanada = mView.getMovimentJoystick();
                if (mEscenari.emPucMoureEnDireccio(mPosicio, new Point(direccioDemanada.x, direccioDemanada.y))) {
                    this.mMove = direccioDemanada;
                    ultimMoviment = mMove;
                }
            }
            // el joystic no te cap moviment, mantenim la direccio (si es posible)
            if (!mEscenari.emPucMoureEnDireccio(mPosicio, new Point(mMove.x, mMove.y))) {
                this.mMove = new MovimentJoystick(0, 0);
            }

            mEscenari.menjarCocos(mEscenari.getPosicioALaGraella(mPosicio));


        }

        mEscenari.xocoAmbFantasmes(mPosicio, this);

        this.mPosicio.x += mMove.x * pasEnPixels;
        this.mPosicio.y += mMove.y * pasEnPixels;
    }

    @Override
    public void onDraw(Canvas canvas) {
        //ES SOBRESCRIU, no crido al onDraw del pare
        src = new Rect(mWidth * (mFrameActiu), 0, mWidth * (mFrameActiu + 1), mHeight);
        dst = new Rect((int)mPosicio.x, (int)mPosicio.y,
                (int)mPosicio.x + mWidthScreen, (int)mPosicio.y + mHeightScreen);

        canvas.save();
        rotarSegonsDireccio(canvas);
        canvas.drawBitmap(mBitmapSprites.get(mSpriteActiu), src, dst, new Paint());
        canvas.restore();

    }

    private void rotarSegonsDireccio(Canvas canvas) {
        if (ultimMoviment.x == 1 && ultimMoviment.y == 0) {
            canvas.scale(1, 1, (int) (mPosicio.x + mWidthScreen * 0.5), (int) (mPosicio.y + mWidthScreen * 0.5));
        } else if (ultimMoviment.x == -1 && ultimMoviment.y == 0) {
            canvas.scale(-1, 1, (int) (mPosicio.x + mWidthScreen * 0.5), (int) (mPosicio.y + mWidthScreen * 0.5));
        } else if (ultimMoviment.x == 0 && ultimMoviment.y == 1) {
            canvas.rotate(90, (int)(mPosicio.x + mWidthScreen * 0.5), (int)(mPosicio.y + mWidthScreen * 0.5));
        } else if (ultimMoviment.x == 0 && ultimMoviment.y == -1) {
            canvas.rotate(-90, (int)(mPosicio.x + mWidthScreen * 0.5), (int)(mPosicio.y + mWidthScreen * 0.5));
        }
    }
}

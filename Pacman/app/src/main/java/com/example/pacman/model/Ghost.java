package com.example.pacman.model;

import android.graphics.Canvas;
import android.graphics.Point;

import com.example.pacman.DemoSurfaceView;

import java.util.Date;
import java.util.List;

public abstract class Ghost extends Sprite {

    //Distancia en pixels recorreguda per ticks

    protected float pasEnPixels;
    protected boolean mEsticCagat;

    protected Point mPosObjectiu = new Point(4, 8);

    public Ghost(DemoSurfaceView view, int width, int height, int midaCella) {
        super(view, width, height, midaCella, midaCella);

        pasEnPixels = midaCella / 10.0f;

        setModeEspantat(false);

    }

    public void setModeEspantat(boolean esticCagat) {
        mEsticCagat = esticCagat;
        setSpriteActiu(esticCagat ? 1 : 0);
    }

    public Boolean getModeEspantat() { return mEsticCagat;}

    @Override
    public abstract List<Integer> getSprites();

    protected MovimentJoystick mMove = new MovimentJoystick(1, 0);

    @Override
    public void tick() {
        super.tick();

        if (mEscenari.esticALaCasella(this.mPosicio)){

            if (mEscenari.esticALaBocaDelTunel(mEscenari.getPosicioALaGraella(mPosicio))) {
                mPosicio = mEscenari.creuarTunel(mEscenari.getPosicioALaGraella(mPosicio));
            }

            // mirar si he de canviar de direccio
            this.mMove = recalculaDireccio();

        }

        this.mPosicio.x += mMove.x * pasEnPixels;
        this.mPosicio.y += mMove.y * pasEnPixels;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mEscenari.fantasmesDeixenDeEstarEspantats();
    }



    protected abstract MovimentJoystick recalculaDireccio();
}

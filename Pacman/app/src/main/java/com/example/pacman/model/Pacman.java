package com.example.pacman.model;

import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;

import com.example.pacman.DemoSurfaceView;
import com.example.pacman.R;

import java.util.ArrayList;
import java.util.List;

public class Pacman extends Sprite {

    //Distancia en pixels recorreguda per ticks

    private float pasEnPixels;

    public Pacman(DemoSurfaceView view, int width, int height, int midaCella) {
        super(view, width, height, midaCella, midaCella);

        mPosicio = mEscenari.getPosicioIniciPacman();

        pasEnPixels = midaCella / 10.0f;

        modeEspantat(false);
    }

    public void modeEspantat(boolean esticCagat) {
        setSpriteActiu(esticCagat ? 1 : 0);
    }

    @Override
    public List<Integer> getSprites() {
        List<Integer> sprites = new ArrayList<>();
        sprites.add(R.drawable.pacman);
        return sprites;
    }

    private MovimentJoystick mMove = new MovimentJoystick(0, 0);

    @Override
    public void tick() {
        super.tick();

        if (mEscenari.esticALaCasella(this.mPosicio)){

            /*if (mEscenari.getCella(mEscenari.getPosicioALaGraella(mPosicio)).ordinal() == 700) {

            }*/

            if (mEscenari.esticALaBocaDelTunel(mEscenari.getPosicioALaGraella(mPosicio))) {
                mPosicio = mEscenari.creuarTunel(mEscenari.getPosicioALaGraella(mPosicio));
            }

            if (mView.getMovimentJoystick() != null) {
                MovimentJoystick direccioDemanada = mView.getMovimentJoystick();
                if (mEscenari.emPucMoureEnDireccio(mPosicio, new Point(direccioDemanada.x, direccioDemanada.y))) {
                    this.mMove = direccioDemanada;
                }
            }
            // el joystic no te cap moviment, mantenim la direccio (si es posible)
            if (!mEscenari.emPucMoureEnDireccio(mPosicio, new Point(mMove.x, mMove.y))) {
                this.mMove = new MovimentJoystick(0, 0);
            }

            mEscenari.menjarCocos(mEscenari.getPosicioALaGraella(mPosicio));

        }

        this.mPosicio.x += mMove.x * pasEnPixels;
        this.mPosicio.y += mMove.y * pasEnPixels;
    }

}

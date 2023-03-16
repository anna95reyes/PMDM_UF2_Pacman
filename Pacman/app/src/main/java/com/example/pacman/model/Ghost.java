package com.example.pacman.model;

import android.graphics.Canvas;
import android.graphics.Point;

import androidx.annotation.Nullable;

import com.example.pacman.DemoSurfaceView;

import java.util.ArrayList;
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

    protected MovimentJoystick recalculaDireccio() {

        boolean canviDireccio = false;

        // cas 1: me la fotre si continuo aixi
        boolean xoco = !mEscenari.emPucMoureEnDireccio(mPosicio, mMove.getPoint());
        if (xoco) {
            canviDireccio = true;
        }
        // cas 2: estic a una cruilla amb nous camins perpendiculars
        List<Point> direccionsPossibles = new ArrayList<>();
        for (int i = 0; i < mDireccions.length; i++){
            if (mEscenari.emPucMoureEnDireccio(mPosicio, mDireccions[i])) {
                direccionsPossibles.add(mDireccions[i]);
            }

        }
        if (!xoco) {
            canviDireccio = direccionsPossibles.size()>2; //hi ha mes de dos direccions
        }

        if (canviDireccio) {
            return canviaDireccio(canviDireccio, direccionsPossibles);
        }

        return mMove; //no canviem direccio

    }


    protected MovimentJoystick canviaDireccio(boolean canviDireccio, List<Point> direccionsPossibles) {
        int idx = (int)(Math.random() * direccionsPossibles.size());
        Point p = direccionsPossibles.get(idx);
        return new MovimentJoystick(p.x, p.y);
    }
}

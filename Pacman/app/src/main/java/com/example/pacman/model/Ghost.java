package com.example.pacman.model;

import android.graphics.Point;

import com.example.pacman.DemoSurfaceView;
import com.example.pacman.R;

import java.util.ArrayList;
import java.util.List;

public abstract class Ghost extends Sprite {

    //Distancia en pixels recorreguda per ticks

    protected float pasEnPixels;

    protected Point mPosObjectiu = new Point(4, 8);

    public Ghost(DemoSurfaceView view, int width, int height, int midaCella) {
        super(view, width, height, midaCella, midaCella);

        //mPosicio = mEscenari.getPosicioIniciBlinky();

        pasEnPixels = midaCella / 10.0f;

        modeEspantat(false);
    }

    public void modeEspantat(boolean esticCagat) {
        setSpriteActiu(esticCagat ? 1 : 0);
    }

    @Override
    public List<Integer> getSprites() {
        List<Integer> sprites = new ArrayList<>();
        sprites.add(R.drawable.blinky);
        sprites.add(R.drawable.pinky);
        sprites.add(R.drawable.inky);
        sprites.add(R.drawable.clyde);
        sprites.add(R.drawable.espantat);
        return sprites;
    }

    protected MovimentJoystick mMove = new MovimentJoystick(1, 0);

    @Override
    public void tick() {
        super.tick();

        if (mEscenari.esticALaCasella(this.mPosicio)){

            // mirar si he de canviar de direccio
            this.mMove = recalculaDireccio();
        }

        //this.mPosicio.x += mMove.x * pasEnPixels;
        //this.mPosicio.y += mMove.y * pasEnPixels;
    }

    protected abstract MovimentJoystick recalculaDireccio(); /*{

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

        if (canviDireccio){
            int idx = (int)(Math.random() * direccionsPossibles.size());
            Point p = direccionsPossibles.get(idx);
            return new MovimentJoystick(p.x, p.y);
        }

        return mMove; //no canviem direccio

    }*/
}

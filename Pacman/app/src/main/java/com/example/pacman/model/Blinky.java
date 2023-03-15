package com.example.pacman.model;

import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;

import com.example.pacman.DemoSurfaceView;
import com.example.pacman.R;

import java.util.ArrayList;
import java.util.List;

public class Blinky extends Ghost {

    public Blinky(DemoSurfaceView view, int width, int height, int midaCella) {
        super(view, width, height, midaCella);
        mPosicio = mEscenari.getPosicioIniciBlinky();
    }

    @Override
    public List<Integer> getSprites() {
        List<Integer> sprites = new ArrayList<>();
        sprites.add(R.drawable.blinky);
        sprites.add(R.drawable.espantat);
        return sprites;
    }

    @Override
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
            if (getModeEspantat()) {
                int idx = (int) (Math.random() * direccionsPossibles.size());
                Point p = direccionsPossibles.get(idx);
                return new MovimentJoystick(p.x, p.y);
            } else {
                int indexPosMesAprop = 0;
                double distanciaMesCurta = 0;
                double distancia;
                Point posicioPacman = mEscenari.getPosicioActualPacman();
                Log.d("XXX", "pacman: " + posicioPacman);
                for (int i = 0; i < direccionsPossibles.size(); i++){
                    distancia = Math.hypot(
                            (posicioPacman.x - direccionsPossibles.get(i).x),
                            (posicioPacman.y - direccionsPossibles.get(i).y)
                    );
                    Log.d("XXX", "distancia" + i + ": " + distancia);
                    if (i == 0 || distanciaMesCurta > distancia) {
                        distanciaMesCurta = distancia;
                        indexPosMesAprop = i;
                    }

                }
                Log.d("XXX", "distancia mes curta " + indexPosMesAprop + ": " + distanciaMesCurta);
                Point p = direccionsPossibles.get(indexPosMesAprop);
                return new MovimentJoystick(p.x, p.y);
            }
        }

        return mMove; //no canviem direccio

    }

}

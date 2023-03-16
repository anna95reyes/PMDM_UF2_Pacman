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
    protected MovimentJoystick canviaDireccio(boolean canviDireccio, List<Point> direccionsPossibles) {
        if (getModeEspantat()) {
            int idx = (int) (Math.random() * direccionsPossibles.size());
            Point p = direccionsPossibles.get(idx);
            return new MovimentJoystick(p.x, p.y);
        } else {
            int indexPosMesAprop = 0;
            double distanciaMesCurta = Double.MAX_VALUE;
            double distancia;
            Point posicioPacman = mEscenari.getPosicioActualPacman();
            Point posicioGhost = mEscenari.getPosicioALaGraella(mPosicio);
            Log.d("XXX", "pacman: " + posicioPacman);
            for (int i = 0; i < direccionsPossibles.size(); i++){
                distancia = Math.hypot(
                        (posicioPacman.x - (posicioGhost.x + direccionsPossibles.get(i).x)),
                        (posicioPacman.y - (posicioGhost.y + direccionsPossibles.get(i).y))
                );
                Log.d("XXX", "distancia" + i + ": " + distancia);
                if (distanciaMesCurta > distancia) {
                    distanciaMesCurta = distancia;
                    indexPosMesAprop = i;
                }

            }
            Log.d("XXX", "distancia mes curta " + indexPosMesAprop + ": " + distanciaMesCurta);
            Point p = direccionsPossibles.get(indexPosMesAprop);
            return new MovimentJoystick(p.x, p.y);
        }
    }


}

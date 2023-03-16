package com.example.pacman.model;

import android.graphics.Point;
import android.util.Log;

import com.example.pacman.DemoSurfaceView;
import com.example.pacman.R;

import java.util.ArrayList;
import java.util.List;

public class Pinky extends Ghost {

    public Pinky(DemoSurfaceView view, int width, int height, int midaCella) {
        super(view, width, height, midaCella);
        mPosicio = mEscenari.getPosicioIniciPinky();
    }

    @Override
    public List<Integer> getSprites() {
        List<Integer> sprites = new ArrayList<>();
        sprites.add(R.drawable.pinky);
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
            Point posicioPacman = mEscenari.getPosicioActualPacman();
            MovimentJoystick direccioPacman = mEscenari.getUltimMovimentPacman();

            int indexPosMesAprop = 0;
            double distanciaMesCurta = Double.MAX_VALUE;
            double distancia;

            Point posicioGhost = mEscenari.getPosicioALaGraella(mPosicio);

            for (int i = 0; i < direccionsPossibles.size(); i++){
                Point dir = posicioPacman;
                dir.x = dir.x + ((int)Math.signum(direccioPacman.x) * 4);
                dir.y = dir.y + ((int)Math.signum(direccioPacman.y) * 4);

                distancia = Math.hypot(
                        (dir.x - (posicioGhost.x + direccionsPossibles.get(i).x)),
                        (dir.y - (posicioGhost.y + direccionsPossibles.get(i).y))
                );
                if (distanciaMesCurta > distancia) {
                    distanciaMesCurta = distancia;
                    indexPosMesAprop = i;
                }

            }
            Point p = direccionsPossibles.get(indexPosMesAprop);
            return new MovimentJoystick(p.x, p.y);

        }
    }



}

package com.example.pacman.model;

import android.graphics.Point;

import com.example.pacman.DemoSurfaceView;
import com.example.pacman.R;

import java.util.ArrayList;
import java.util.List;

public class Inky extends Ghost {

    public Inky(DemoSurfaceView view, int width, int height, int midaCella) {
        super(view, width, height, midaCella);
        mPosicio = mEscenari.getPosicioIniciInky();
    }

    @Override
    public List<Integer> getSprites() {
        List<Integer> sprites = new ArrayList<>();
        sprites.add(R.drawable.inky);
        sprites.add(R.drawable.espantat);
        return sprites;
    }

}

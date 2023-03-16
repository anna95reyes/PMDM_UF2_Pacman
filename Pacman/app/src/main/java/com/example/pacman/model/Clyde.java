package com.example.pacman.model;

import android.graphics.Point;

import com.example.pacman.DemoSurfaceView;
import com.example.pacman.R;

import java.util.ArrayList;
import java.util.List;

public class Clyde extends Ghost {

    public Clyde(DemoSurfaceView view, int width, int height, int midaCella) {
        super(view, width, height, midaCella);
        mPosicio = mEscenari.getPosicioIniciClyde();
    }

    @Override
    public List<Integer> getSprites() {
        List<Integer> sprites = new ArrayList<>();
        sprites.add(R.drawable.clyde);
        sprites.add(R.drawable.espantat);
        return sprites;
    }

}

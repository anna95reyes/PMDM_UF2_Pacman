package com.example.pacman.model;

import android.graphics.Point;

public class MovimentJoystick {

    public int x, y;

    public MovimentJoystick(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point getPoint(){
        return new Point(x, y);
    }
}

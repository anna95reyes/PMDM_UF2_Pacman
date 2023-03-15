package com.example.pacman;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    public static Activity mainActivity;
    public static Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.wakawaka);
        mediaPlayer.start();
        mediaPlayer.setLooping(true); //Per repetir l'audio

        mainActivity = this;

        handler = new Handler();
    }
}
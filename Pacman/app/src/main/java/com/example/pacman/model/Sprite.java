package com.example.pacman.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;

import com.example.pacman.DemoSurfaceView;

import java.util.ArrayList;
import java.util.List;

public abstract class Sprite extends GameObject {

    protected PointF mPosicio;
    protected int mSpriteActiu;

    protected List<Bitmap> mBitmapSprites;
    protected List<Integer> mBitmapFrames;
    protected int mFrameActiu = 0;
    protected int mWidth, mHeight;
    protected int mWidthScreen, mHeightScreen;
    protected int mDelay = 0, mDelayRandom = 0;
    protected Point[] mDireccions = new Point[]{
            new Point(1, 0),
            new Point(-1, 0),
            new Point(0, 1),
            new Point(0, -1)
    };
    protected Rect src;
    protected Rect dst;

    public Sprite(DemoSurfaceView view, int width, int height, int witdhScreen, int heightScreen) {
        super(view);

        mWidth = width;
        mHeight = height;
        mWidthScreen = witdhScreen;
        mHeightScreen = heightScreen;

        mBitmapSprites = new ArrayList<>();
        mBitmapFrames = new ArrayList<>();//per saber quants fotogrames te el Bitmap
        mSpriteActiu = 0; //Quan el fantasma entra en mode que el poden menjar canviem el spriteActiu a 1 quan es normal a 0
        for (int idRes : getSprites()) {
            Bitmap m = BitmapFactory.decodeResource(view.getResources(), idRes);
            mBitmapFrames.add(m.getWidth() / mWidth);
            mBitmapSprites.add(m);
        }

    }

    public abstract List<Integer> getSprites();

    @Override
    public void onDraw(Canvas canvas) {
        src = new Rect(mWidth * (mFrameActiu), 0, mWidth * (mFrameActiu + 1), mHeight);
        dst = new Rect((int)mPosicio.x, (int)mPosicio.y,
                (int)mPosicio.x + mWidthScreen, (int)mPosicio.y + mHeightScreen);

        canvas.save();
        //canvas.scale(-1, 1, (int)(mPosicio.x + mWidthScreen * 0.5), (int)(mPosicio.y + mWidthScreen * 0.5));
        /*canvas.rotate(90, (int)(mPosicio.x + mWidthScreen * 0.5), (int)(mPosicio.y + mWidthScreen * 0.5));*/

        canvas.drawBitmap(mBitmapSprites.get(mSpriteActiu), src, dst, new Paint());
        canvas.restore();
    }

    @Override
    public void tick() {

        mDelay = (mDelay + 1) % 5;
        if(mDelay == 0){
            mFrameActiu = (mFrameActiu + 1) % mBitmapFrames.get(mSpriteActiu);
        }
    }

    protected void setSpriteActiu(int idxSprite) {
        if (idxSprite < 0 || idxSprite > mBitmapFrames.size() - 1) {
            throw new RuntimeException("Animal! on vas! No tinc tants sprites");
        }
        this.mSpriteActiu = idxSprite;
    }

    protected int getSpriteActiu(){
        return mSpriteActiu;
    }
}

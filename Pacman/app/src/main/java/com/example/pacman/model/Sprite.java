package com.example.pacman.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

import com.example.pacman.DemoSurfaceView;

import java.util.ArrayList;
import java.util.List;

public abstract class Sprite extends GameObject {

    protected PointF mPosicio;
    private int mSpriteActiu;

    private List<Bitmap> mBitmapSprites;
    private List<Integer> mBitmapFrames;
    private int mFrameActiu = 0;
    private int mWidth, mHeight;
    private int mWidthScreen, mHeightScreen;
    private int mDelay = 0, mDelayRandom = 0;
    protected Point[] mDireccions = new Point[]{
            new Point(1, 0),
            new Point(-1, 0),
            new Point(0, 1),
            new Point(0, -1)
    };

    public Sprite(DemoSurfaceView view, int width, int height, int witdhScreen, int heightScreen) {
        super(view);

        mWidth = width;
        mHeight = height;
        mWidthScreen = witdhScreen;
        mHeightScreen = heightScreen;
        mPosicio = new PointF(40, 40);

        mBitmapSprites = new ArrayList<>();
        mBitmapFrames = new ArrayList<>();//per sabet quants fotogrames te el Bitmap
        mSpriteActiu = 1; //Quan el fantasma entra en mode que el poden menjar canviem el spriteActiu a 1 quan es normal a 0
        for (int idRes : getSprites()) {
            Bitmap m = BitmapFactory.decodeResource(view.getResources(), idRes);
            mBitmapFrames.add(m.getWidth() / mWidth);
            mBitmapSprites.add(m);
        }

    }

    public abstract List<Integer> getSprites();

    @Override
    public void onDraw(Canvas canvas) {
        Rect src = new Rect(mWidth * (mFrameActiu), 0, mWidth * (mFrameActiu + 1), mHeight);
        Rect dst = new Rect((int)mPosicio.x, (int)mPosicio.y,
                (int)mPosicio.x + mWidthScreen, (int)mPosicio.y + mHeightScreen);

        canvas.save();
        canvas.scale(-1, 1, (int)(mPosicio.x + mWidthScreen * 0.5), (int)(mPosicio.y + mWidthScreen * 0.5));
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
}

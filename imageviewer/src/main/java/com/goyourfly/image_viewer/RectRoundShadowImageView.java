package com.goyourfly.image_viewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import net.soulwolf.widget.ratiolayout.widget.RatioImageView;

/**
 * Created by gaoyufei on 2017/3/28.
 */

public class RectRoundShadowImageView extends RatioImageView {
    public static final String TAG = "RectRoundShadowImage";

    private static final int COLORDRAWABLE_DIMENSION = 2;

    //图片的bitmap
    private Bitmap mImageBitmap;
    //阴影尺寸
    private int shadowSize = 20;

    private int cornerRadius = 10;

    //bitmapPaint所用shader
    private BitmapShader bitmapShader;

    //阴影所在范围
    private RectF shadowBound;
    //图片所在范围
    private RectF bitmapBound;

    private Paint bitmapPaint;
    private Paint shadowPaint;
    private Paint placeHolderPaint;

    private Matrix bitmapMatrix;

    private boolean isReady = false;

    public RectRoundShadowImageView(Context context) {
        super(context);
        init();
    }

    public RectRoundShadowImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RectRoundShadowImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    public void setImageResource(@DrawableRes int resId) {
        Log.d(TAG,"setImageResource");
        isReady = false;
        super.setImageResource(resId);
        mImageBitmap = getBitmapFromDrawable(getResources().getDrawable(resId));
        setup();
    }


    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        Log.d(TAG,"setImageDrawable");
        isReady = false;
        super.setImageDrawable(drawable);
        mImageBitmap = getBitmapFromDrawable(drawable);
        setup();
    }


    @Override
    public void setImageBitmap(Bitmap bm) {
        Log.d(TAG,"setImageBitmap");
        isReady = false;
        super.setImageBitmap(bm);
        mImageBitmap = getBitmapFromDrawable(getDrawable());
        setup();
    }


    @Override
    public void setImageURI(@Nullable Uri uri) {
        Log.d(TAG,"setImageURI");
        isReady = false;
        super.setImageURI(uri);
        mImageBitmap = getBitmapFromDrawable(getDrawable());
        setup();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if(!isReady){
            super.onDraw(canvas);
            return;
        }

//        canvas.drawRoundRect(shadowBound,cornerRadius,cornerRadius,shadowPaint);
        canvas.drawRoundRect(bitmapBound,cornerRadius,cornerRadius,bitmapPaint);
    }

    private void init(){
        shadowBound = new RectF();
        bitmapBound = new RectF();


        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        shadowPaint.setColor(getResources().getColor(android.R.color.black));
        shadowPaint.setShadowLayer(shadowSize/2,0,0,getResources().getColor(android.R.color.black));

        placeHolderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        placeHolderPaint.setStyle(Paint.Style.FILL);
        placeHolderPaint.setColor(getResources().getColor(android.R.color.darker_gray));

        bitmapMatrix = new Matrix();

    }

    private void setup(){
        if(mImageBitmap == null){
            Log.w(TAG,"ImageBitmap is null");
            return;
        }


        bitmapBound.set(shadowSize/2,shadowSize/2,getWidth() - shadowSize/2,getHeight() - shadowSize/2);
        shadowBound.set(shadowSize/2,shadowSize/2,getWidth() - shadowSize/2,getHeight() - shadowSize/2);

        bitmapShader = new BitmapShader(mImageBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        bitmapPaint.setAntiAlias(true);
        bitmapPaint.setShader(bitmapShader);

        int width = mImageBitmap.getWidth();
        int height = mImageBitmap.getHeight();

        bitmapMatrix.set(null);

        float scale;
        float dx = 0;
        float dy = 0;

        bitmapMatrix.set(null);

        if (width * bitmapBound.height() > bitmapBound.width() * height) {
            scale = bitmapBound.height() / (float) height;
            dx = (bitmapBound.width() - width * scale) * 0.5f;
        } else {
            scale = bitmapBound.width() / (float) width;
            dy = (bitmapBound.height() - height * scale) * 0.5f;
        }

        bitmapMatrix.setScale(scale,scale);
        bitmapMatrix.postTranslate((int) (dx + 0.5f) + bitmapBound.left, (int) (dy + 0.5f) + bitmapBound.top);

        bitmapShader.setLocalMatrix(bitmapMatrix);


        isReady = true;

        invalidate();
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, Bitmap.Config.ARGB_8888);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

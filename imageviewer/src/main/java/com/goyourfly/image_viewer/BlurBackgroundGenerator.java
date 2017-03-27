package com.goyourfly.image_viewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcel;
import android.widget.ImageView;

import jp.wasabeef.blurry.Blurry;


/**
 * Created by gaoyufei on 17-3-26.
 */

public class BlurBackgroundGenerator implements BackgroundGenerator{
    private static final float scale = 0.5F;
    private Bitmap bitmap;
    private Context context;
    public BlurBackgroundGenerator (Context context, Bitmap bitmap){
        this.context = context;
        this.bitmap = bitmap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    @Override
    public void setBackground(final ImageView view) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setScale(scale,scale,scale,1);

        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth()/2,bitmap.getHeight()/2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        Rect rectF = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        RectF rectT = new RectF(0,0,newBitmap.getWidth(),newBitmap.getHeight());
        canvas.drawBitmap(bitmap,rectF,rectT,paint);

        Blurry.with(context).radius(20).from(newBitmap).into(view);
    }
}

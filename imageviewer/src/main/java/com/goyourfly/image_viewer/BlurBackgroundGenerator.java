package com.goyourfly.image_viewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
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

        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap,0,0,paint);

        Blurry.with(context).radius(50).from(newBitmap).into(view);
    }
}

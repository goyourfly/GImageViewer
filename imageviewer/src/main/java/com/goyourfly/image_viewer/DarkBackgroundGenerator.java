package com.goyourfly.image_viewer;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by gaoyufei on 17-3-26.
 */

public class DarkBackgroundGenerator implements BackgroundGenerator{
    private float alpha;
    public DarkBackgroundGenerator (float alpha){
        this.alpha = alpha;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(alpha);
    }


    @Override
    public void setBackground(ImageView view) {
        ColorDrawable colorDrawable = new ColorDrawable(Color.BLACK);
        colorDrawable.setAlpha((int) (alpha * 255));
        view.setBackground(colorDrawable);
    }
}

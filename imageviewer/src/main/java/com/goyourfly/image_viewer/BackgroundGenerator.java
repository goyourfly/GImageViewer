package com.goyourfly.image_viewer;

import android.os.Parcelable;
import android.widget.ImageView;

/**
 * Created by gaoyufei on 17-3-26.
 */

public interface BackgroundGenerator extends Parcelable{
    public void setBackground(ImageView view);
}

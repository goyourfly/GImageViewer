package com.goyourfly.image_viewer;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by gaoyufei on 17-3-26.
 */

public class GoImage {
    public static ImageViewerFragment.ImageViewerBuilder with(AppCompatActivity activity){
        return ImageViewerFragment.ImageViewerBuilder.with(activity);
    }
}

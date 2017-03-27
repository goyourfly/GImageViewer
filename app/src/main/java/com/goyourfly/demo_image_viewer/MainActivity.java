package com.goyourfly.demo_image_viewer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.goyourfly.image_viewer.GoImage;
import com.goyourfly.image_viewer.ImageViewerFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private View mMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMain = findViewById(R.id.activity_main);

        final ArrayList<Uri> list = new ArrayList<>();
        list.add(Uri.parse("http://pic1.win4000.com/wallpaper/a/57a0131036064.jpg"));
        list.add(Uri.parse("http://pic17.nipic.com/20111122/6759425_152002413138_2.jpg"));
        list.add(Uri.parse("http://pic24.nipic.com/20121003/10754047_140022530392_2.jpg"));
        list.add(Uri.parse("http://pic9.nipic.com/20100827/4982229_103856887335_2.jpg"));
        list.add(Uri.parse("http://attach.bbs.miui.com/forum/201309/27/181659trqmmw5x3t4gcigt.jpg"));

        list.add(Uri.parse("http://pic1.win4000.com/wallpaper/a/57a0131036064.jpg"));
        list.add(Uri.parse("http://pic17.nipic.com/20111122/6759425_152002413138_2.jpg"));
        list.add(Uri.parse("http://pic24.nipic.com/20121003/10754047_140022530392_2.jpg"));
        list.add(Uri.parse("http://pic9.nipic.com/20100827/4982229_103856887335_2.jpg"));
        list.add(Uri.parse("http://attach.bbs.miui.com/forum/201309/27/181659trqmmw5x3t4gcigt.jpg"));

        list.add(Uri.parse("http://pic1.win4000.com/wallpaper/a/57a0131036064.jpg"));
        list.add(Uri.parse("http://pic17.nipic.com/20111122/6759425_152002413138_2.jpg"));
        list.add(Uri.parse("http://pic24.nipic.com/20121003/10754047_140022530392_2.jpg"));
        list.add(Uri.parse("http://pic9.nipic.com/20100827/4982229_103856887335_2.jpg"));
        list.add(Uri.parse("http://attach.bbs.miui.com/forum/201309/27/181659trqmmw5x3t4gcigt.jpg"));

        list.add(Uri.parse("http://pic1.win4000.com/wallpaper/a/57a0131036064.jpg"));
        list.add(Uri.parse("http://pic17.nipic.com/20111122/6759425_152002413138_2.jpg"));
        list.add(Uri.parse("http://pic24.nipic.com/20121003/10754047_140022530392_2.jpg"));
        list.add(Uri.parse("http://pic9.nipic.com/20100827/4982229_103856887335_2.jpg"));
        list.add(Uri.parse("http://attach.bbs.miui.com/forum/201309/27/181659trqmmw5x3t4gcigt.jpg"));
        findViewById(R.id.image)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GoImage
                                .with(MainActivity.this)
                                .imageHeight(getResources().getDimensionPixelSize(R.dimen.image_viewer_height))
                                .imageInterval(getResources().getDimensionPixelSize(R.dimen.image_viewer_interval))
                                .imageAspectRatio(1.44444F)
                                .blurBackground(getBitmapFromView(mMain))
                                .data(list)
                                .show(R.id.container);
                    }
                });
    }


    public static Bitmap getBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        // Draw background
        Drawable bgDrawable = v.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(c);
        else
            c.drawColor(Color.WHITE);
        // Draw view to canvas
        v.draw(c);
        return b;
    }
}

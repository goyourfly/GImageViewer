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
        list.add(Uri.parse("http://d.hiphotos.baidu.com/image/pic/item/5882b2b7d0a20cf482c772bf73094b36acaf997f.jpg"));
        list.add(Uri.parse("http://g.hiphotos.baidu.com/image/pic/item/10dfa9ec8a1363270c254f53948fa0ec09fac782.jpg"));
        list.add(Uri.parse("http://c.hiphotos.baidu.com/image/pic/item/bf096b63f6246b6086073e14eef81a4c510fa241.jpg"));
        list.add(Uri.parse("http://h.hiphotos.baidu.com/image/pic/item/80cb39dbb6fd5266cfe0b174ae18972bd5073682.jpg"));
        list.add(Uri.parse("http://g.hiphotos.baidu.com/image/pic/item/9e3df8dcd100baa1cb7acbdb4210b912c8fc2e7f.jpg"));
        list.add(Uri.parse("http://c.hiphotos.baidu.com/image/pic/item/cc11728b4710b912c2b57a9ec6fdfc039245225d.jpg"));
        list.add(Uri.parse("http://d.hiphotos.baidu.com/image/pic/item/5882b2b7d0a20cf482c772bf73094b36acaf997f.jpg"));
        list.add(Uri.parse("http://g.hiphotos.baidu.com/image/pic/item/10dfa9ec8a1363270c254f53948fa0ec09fac782.jpg"));
        list.add(Uri.parse("http://c.hiphotos.baidu.com/image/pic/item/bf096b63f6246b6086073e14eef81a4c510fa241.jpg"));
        list.add(Uri.parse("http://h.hiphotos.baidu.com/image/pic/item/80cb39dbb6fd5266cfe0b174ae18972bd5073682.jpg"));
        list.add(Uri.parse("http://g.hiphotos.baidu.com/image/pic/item/9e3df8dcd100baa1cb7acbdb4210b912c8fc2e7f.jpg"));
        list.add(Uri.parse("http://c.hiphotos.baidu.com/image/pic/item/cc11728b4710b912c2b57a9ec6fdfc039245225d.jpg"));
        list.add(Uri.parse("http://d.hiphotos.baidu.com/image/pic/item/5882b2b7d0a20cf482c772bf73094b36acaf997f.jpg"));
        list.add(Uri.parse("http://g.hiphotos.baidu.com/image/pic/item/10dfa9ec8a1363270c254f53948fa0ec09fac782.jpg"));
        list.add(Uri.parse("http://c.hiphotos.baidu.com/image/pic/item/bf096b63f6246b6086073e14eef81a4c510fa241.jpg"));
        list.add(Uri.parse("http://h.hiphotos.baidu.com/image/pic/item/80cb39dbb6fd5266cfe0b174ae18972bd5073682.jpg"));
        list.add(Uri.parse("http://g.hiphotos.baidu.com/image/pic/item/9e3df8dcd100baa1cb7acbdb4210b912c8fc2e7f.jpg"));
        list.add(Uri.parse("http://c.hiphotos.baidu.com/image/pic/item/cc11728b4710b912c2b57a9ec6fdfc039245225d.jpg"));

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

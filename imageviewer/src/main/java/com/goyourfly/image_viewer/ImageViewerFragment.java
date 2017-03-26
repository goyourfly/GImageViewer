package com.goyourfly.image_viewer;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.soulwolf.widget.ratiolayout.RatioDatumMode;
import net.soulwolf.widget.ratiolayout.widget.RatioFrameLayout;
import net.soulwolf.widget.ratiolayout.widget.RatioImageView;

import java.util.ArrayList;
import java.util.List;


public class ImageViewerFragment extends Fragment {

    private RecyclerView mRecycler;
    private TextView mTextIndicator;
    private float aspectRatio;
    private int imageInterval;
    private int imageHeight;
    private BackgroundGenerator backgroundGenerator;
    private List<Uri> mData;




    public ImageViewerFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle == null)
            throw new NullPointerException("Bundle must not null");

        aspectRatio = bundle.getFloat("aspectRatio");
        imageHeight = bundle.getInt("imageHeight");
        imageInterval = bundle.getInt("imageInterval");
        backgroundGenerator = bundle.getParcelable("backgroundGenerator");
        mData = bundle.getParcelableArrayList("imgList");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_viewer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycler = (RecyclerView) view.findViewById(R.id.recycler);
        mTextIndicator = (TextView) view.findViewById(R.id.text_indicator);

        mRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        mRecycler.setAdapter(new MyAdapter());

        backgroundGenerator.setBackground((ImageView) view.findViewById(R.id.image));


        view.findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(ImageViewerFragment.this).commit();
            }
        });


        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int index = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                if(index >= 0)
                    mTextIndicator.setText((1 + index) + "/" + mData.size());
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        RatioImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (RatioImageView) itemView.findViewById(R.id.image);
            imageView.setRatio(RatioDatumMode.DATUM_HEIGHT,1,aspectRatio);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) imageView.getLayoutParams();
            params.height = imageHeight;
            params.leftMargin = imageInterval/2;
            params.rightMargin = imageInterval/2;
            imageView.setLayoutParams(params);
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        ColorDrawable colorDrawable;

        MyAdapter(){
            colorDrawable = new ColorDrawable(getResources().getColor(android.R.color.darker_gray));
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,parent,false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            Glide.with(holder.imageView.getContext())
                    .load(mData.get(position))
                    .placeholder(colorDrawable)
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }


    public static class ImageViewerBuilder{
        AppCompatActivity activity;
        // set the final view image height
        int imageHeight;
        float aspectRatio;
        int imageInterval = -1;
        BackgroundGenerator backgroundGenerator;
        ArrayList<Uri> imgList;

        private ImageViewerBuilder(AppCompatActivity activity ){
            this.activity = activity;
        }

        protected static ImageViewerBuilder with(AppCompatActivity activity){
            ImageViewerBuilder builder = new ImageViewerBuilder(activity);
            return builder;
        }

        public ImageViewerBuilder imageHeight(int height){
            this.imageHeight = height;
            return this;
        }

        /**
         * set showing img height
         * @param aspectRatio
         * @return
         */
        public ImageViewerBuilder imageAspectRatio(float aspectRatio){
            this.aspectRatio = aspectRatio;
            return this;
        }


        /**
         *  an blur background
          * @param bitmap a image wait blur
         * @return
         */
        public ImageViewerBuilder blurBackground(Bitmap bitmap){
            if(bitmap == null || bitmap.isRecycled()){
                throw new NullPointerException("Bitmap is null or recycled");
            }
            backgroundGenerator = new BlurBackgroundGenerator(activity,bitmap);
            return this;
        }

        /**
         * an alpha darker background
         * @param alpha 0 ~ 1
         * @return
         */
        public ImageViewerBuilder darkBackground(float alpha){
            backgroundGenerator = new DarkBackgroundGenerator(alpha);
            return this;
        }

        /**
         * Custom background generator
         * @param generator
         * @return
         */
        public ImageViewerBuilder customBackground(BackgroundGenerator generator){
            backgroundGenerator = generator;
            return this;
        }


        /**
         * Set image interval
         * @param interval
         * @return
         */
        public ImageViewerBuilder imageInterval(int interval){
            this.imageInterval = interval;
            return this;
        }

        public ImageViewerBuilder data(ArrayList<Uri> imgList){
            this.imgList = imgList;
            return this;
        }

        public Fragment build(){
            if(imageHeight <= 0){
                imageHeight = 200;
            }

            if(aspectRatio <= 0){
                aspectRatio = 1.77777f;
            }

            if(imageInterval < 0){
                imageInterval = 40;
            }

            if(backgroundGenerator == null){
                backgroundGenerator = new DarkBackgroundGenerator(0.5f);
            }

            Bundle bundle = new Bundle();
            bundle.putFloat("aspectRatio",aspectRatio);
            bundle.putInt("imageHeight",imageHeight);
            bundle.putInt("imageInterval",imageInterval);
            bundle.putParcelableArrayList("imgList",imgList);
            bundle.putParcelable("backgroundGenerator",backgroundGenerator);


            ImageViewerFragment fragment = new ImageViewerFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        public void show(int id){
            activity.getSupportFragmentManager().beginTransaction().replace(id,build()).commit();
        }


    }
}

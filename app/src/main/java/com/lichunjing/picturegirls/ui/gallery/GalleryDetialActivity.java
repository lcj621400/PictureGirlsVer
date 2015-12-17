package com.lichunjing.picturegirls.ui.gallery;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.base.BasePicActivity;

import uk.co.senab.photoview.PhotoViewAttacher;

public class GalleryDetialActivity extends BasePicActivity {

    public static final String GALLERY_URL="gallery_url";
    private String galleryUrl;
    private ImageView mImageView;
    private PhotoViewAttacher mAttacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        if(getIntent()!=null){
            galleryUrl=getIntent().getStringExtra(GALLERY_URL);
        }
    }

    @Override
    protected void initViews() {
        super.initViews();
        mImageView= (ImageView) findViewById(R.id.gallery_detial_imageview);
        if(TextUtils.isEmpty(galleryUrl)) return;
        Glide.with(this).load(galleryUrl).centerCrop().crossFade().into(mImageView);

        mAttacher=new PhotoViewAttacher(mImageView);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_gallery_detial;
    }
}

package com.lichunjing.picturegirls.ui.gallery;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.base.BasePicActivity;

import uk.co.senab.photoview.PhotoView;

public class GalleryDetialActivity extends BasePicActivity {

    public static final String GALLERY_URL="gallery_url";
    private String galleryUrl;
    private PhotoView mImageView;
    private CardView mCardView;
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
        mCardView= (CardView) findViewById(R.id.cardview);
        mImageView= (PhotoView) findViewById(R.id.gallery_detial_imageview);
        if(TextUtils.isEmpty(galleryUrl)) return;
        Glide.with(this).load(galleryUrl).error(R.drawable.load_error).fitCenter().crossFade().into(mImageView);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.gallery_detial_image_in);
        mCardView.startAnimation(animation);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_gallery_detial;
    }
}

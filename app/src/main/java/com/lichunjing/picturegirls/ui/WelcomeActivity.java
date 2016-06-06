package com.lichunjing.picturegirls.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.ui.pictures.mainpicture.MainActivity;

/**
 * 启动欢迎页
 */
public class WelcomeActivity extends Activity {

    private ImageView welcomeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        welcomeImg = (ImageView) findViewById(R.id.welcome_img);
        welcome();
    }


    private void welcome() {
        Glide.with(this).load(R.drawable.welcom_bg)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .animate(new ViewPropertyAnimation.Animator() {
                    @Override
                    public void animate(final View view) {
                        Animation scaleAnimation = new ScaleAnimation(1f, 1.2f, 1f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        scaleAnimation.setDuration(1000 * 2);
                        Animation alphaAnimation = new AlphaAnimation(0.5f, 1.0f);
                        alphaAnimation.setDuration(1000 * 2);
                        AnimationSet set = new AnimationSet(true);
                        set.addAnimation(scaleAnimation);
                        set.addAnimation(alphaAnimation);
                        set.setFillAfter(true);
                        set.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                                finish();
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        view.startAnimation(set);
                    }
                })
                .into(welcomeImg);
    }

}

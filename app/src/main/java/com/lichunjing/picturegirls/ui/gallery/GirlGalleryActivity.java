package com.lichunjing.picturegirls.ui.gallery;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.base.BasePicActivity;
import com.lichunjing.picturegirls.ui.gallery.fragment.JellyViewPagerFragment;
import com.lichunjing.picturegirls.ui.gallery.fragment.ViewPagerBaseFragment;
import com.lichunjing.picturegirls.ui.gallery.fragment.ViewPagerFragment;
import com.lichunjing.picturegirls.widget.jellyViewPager.JellyViewPager;

/**
 * 1、JellyViewPager显示所有的图片
 * 2、普通ViewPager显示图片
 */
public class GirlGalleryActivity extends BasePicActivity {

    private  int id;
    public static final String GALLERY_ID="gallery_id";
    private RelativeLayout backLayout;

    private ViewPagerFragment viewPagerFragment;
    private JellyViewPagerFragment jellyViewPagerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void initStatus() {
        super.initStatus();
        if(getIntent()!=null){
            id=getIntent().getIntExtra(GALLERY_ID,0);
        }
        setFragment(getViewPagerFragment());
    }

    @Override
    protected void initViews() {
        super.initViews();
        backLayout= (RelativeLayout) findViewById(R.id.backLayout);
        int a=(int)(Math.random()*100)+90;
        int r=(int)(Math.random()*255);
        int g=(int)(Math.random()*255);
        int b=(int)(Math.random()*255);
        int color= Color.argb(a,r,g,b);
        backLayout.setBackgroundColor(color);
        findViewById(R.id.viewpager_type).setOnClickListener(this);
        findViewById(R.id.jellyviewpager_type).setOnClickListener(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_gril_gallery;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.viewpager_type:
                setFragment(getViewPagerFragment());
                break;
            case R.id.jellyviewpager_type:
                setFragment(getJellyViewPagerFragment());
                break;
        }
    }
    private Fragment getViewPagerFragment(){
        if(viewPagerFragment==null){
            viewPagerFragment= (ViewPagerFragment) ViewPagerFragment.newInstance(id);
        }
        return viewPagerFragment;
    }

    private Fragment getJellyViewPagerFragment(){
        jellyViewPagerFragment= (JellyViewPagerFragment) JellyViewPagerFragment.newInstance(id);
        return jellyViewPagerFragment;
    }

    private void setFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).commit();
    }
}

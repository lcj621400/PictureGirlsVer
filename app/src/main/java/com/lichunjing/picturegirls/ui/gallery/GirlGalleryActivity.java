package com.lichunjing.picturegirls.ui.gallery;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.base.BasePicActivity;
import com.lichunjing.picturegirls.ui.gallery.fragment.recycleviewfragment.RecycleViewFragment;
import com.lichunjing.picturegirls.ui.gallery.fragment.viewpagerfragment.JellyViewPagerFragment;
import com.lichunjing.picturegirls.ui.gallery.fragment.viewpagerfragment.ViewPagerFragment;

/**
 * 1、JellyViewPager显示所有的图片
 * 2、普通ViewPager显示图片
 */
public class GirlGalleryActivity extends BasePicActivity {

    private  int id;
    public static final String GALLERY_ID="gallery_id";
    private LinearLayout backLayout;

    private ViewPagerFragment viewPagerFragment;
    private JellyViewPagerFragment jellyViewPagerFragment;
    private RecycleViewFragment recycleViewFragment;

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
        setFragment(getRecycleViewFragment());
    }

    @Override
    protected void initViews() {
        super.initViews();
        backLayout= (LinearLayout) findViewById(R.id.backLayout);
        int a=(int)(Math.random()*100)+90;
        int r=(int)(Math.random()*255);
        int g=(int)(Math.random()*255);
        int b=(int)(Math.random()*255);
        int color= Color.argb(a,r,g,b);
        backLayout.setBackgroundColor(getResources().getColor(R.color.background));


        initToolBar("写真", true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        FloatingActionButton floatingActionButton = initFloatActionButton(null);
        floatingActionButton.setVisibility(View.GONE);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_gril_gallery;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gallery_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.gallery:
                setFragment(getRecycleViewFragment());
                return true;
            case R.id.viewpager_gallery:
                setFragment(getViewPagerFragment());
                return true;
            case R.id.jelly_gallery:
                setFragment(getJellyViewPagerFragment());
                return true;
        }
        return super.onOptionsItemSelected(item);
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

    private Fragment getRecycleViewFragment(){
        if(recycleViewFragment==null){
            recycleViewFragment= (RecycleViewFragment) RecycleViewFragment.getInstance(id);
        }
        return recycleViewFragment;
    }

    private void setFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).commit();
    }


}

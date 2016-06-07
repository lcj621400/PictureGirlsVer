package com.lichunjing.picturegirls.ui.pictures;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.baseui.BaseNFragment;
import com.lichunjing.picturegirls.ui.mainview.AppMainActivity;
import com.lichunjing.picturegirls.ui.pictures.mainpicture.fragment.MainFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/26.
 * 图片页面
 */
public class PicturesMainFragment extends BaseNFragment {
    private static final String[] TAB_TITLE = {"性感美女", "韩日美女", "丝袜美腿", "美女照片", "美女写真", "清纯美女", "性感车模"};
    private static final int[] TAB_ID = {1, 2, 3, 4, 5, 6, 7};

    private ViewPager mViewPager;
    private MainViewPagerAdapter mViewPagerAdapter;
    private List<MainFragment> fragments;
    private TabLayout indicator;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_picture;
    }

    @Override
    protected void initView(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.main_viewpager);
        indicator = (TabLayout) view.findViewById(R.id.indicator);
        fragments=new ArrayList<>();
        for(int i=0;i<TAB_ID.length;i++){
            fragments.add(MainFragment.newInstance(TAB_ID[i]));
        }
        mViewPagerAdapter = new MainViewPagerAdapter(getChildFragmentManager(), TAB_TITLE,fragments);
        mViewPager.setAdapter(mViewPagerAdapter);
        indicator.setupWithViewPager(mViewPager);
        final Toolbar toolbar= (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("美女图片");
        toolbar.setNavigationIcon(R.drawable.main_menu_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity!=null){
                    ((AppMainActivity)activity).onCustomMenuClick("图片");
                }else {
                    activity=getActivity();
                    if(activity!=null){
                        ((AppMainActivity)activity).onCustomMenuClick("图片");
                    }
                }
            }
        });
    }

    private class MainViewPagerAdapter extends FragmentPagerAdapter {

        private String[] title;
        private List<MainFragment> fragments;

        public MainViewPagerAdapter(FragmentManager fm, String[] title, List<MainFragment> fragments) {
            super(fm);
            this.title = title;
            this.fragments=fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }
}

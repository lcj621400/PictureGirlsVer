package com.lichunjing.picturegirls.ui.news;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.baseui.BaseNFragment;
import com.lichunjing.picturegirls.ui.news.itemfragment.NewsItemFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/26.
 * 新闻页面
 */
public class NewsMainFragment extends BaseNFragment {

    public static final String[] titles={"民生热点","娱乐热点","财经热点","体育热点","教育热点","社会热点"};
    public static final int[] ids={1,2,3,4,5,6};
    private ViewPager mainNewsViewPager;
    private List<NewsItemFragment> mainNewsFragments;
    private MainNewsAdapter mainNewsAdapter;
    private TabLayout mainNewsIndicator;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_newsmain;
    }

    @Override
    protected void initView(View view){
        mainNewsIndicator= (TabLayout) view.findViewById(R.id.main_news_indicator);
        mainNewsViewPager= (ViewPager) view.findViewById(R.id.main_news_viewpager);
        mainNewsAdapter=new MainNewsAdapter(getChildFragmentManager(),titles,generateFragments());
        mainNewsIndicator.setupWithViewPager(mainNewsViewPager);

    }

    /**
     * 生成首页各个页面的fragment集合
     * @return
     */
    private List<NewsItemFragment> generateFragments(){
        mainNewsFragments=new ArrayList<>();
        mainNewsFragments.add(NewsItemFragment.getInstance(1));
        mainNewsFragments.add(NewsItemFragment.getInstance(2));
        mainNewsFragments.add(NewsItemFragment.getInstance(3));
        mainNewsFragments.add(NewsItemFragment.getInstance(4));
        mainNewsFragments.add(NewsItemFragment.getInstance(5));
        mainNewsFragments.add(NewsItemFragment.getInstance(6));
        return mainNewsFragments;
    }


    /**
     * 首页ViewPager适配器
     */
    class MainNewsAdapter extends FragmentPagerAdapter{

        private String[] titles;
        private List<NewsItemFragment> mainNewsFragments;
        public MainNewsAdapter(FragmentManager fm,String[] titles,List<NewsItemFragment> fragments) {
            super(fm);
            this.titles=titles;
            this.mainNewsFragments=fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mainNewsFragments.get(position);
        }

        @Override
        public int getCount() {
            return mainNewsFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}

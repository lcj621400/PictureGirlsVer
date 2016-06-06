package com.lichunjing.picturegirls.ui.mainview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.baseui.BaseActivity;
import com.lichunjing.picturegirls.ui.news.NewsMainFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/5/26.
 * app主页面
 */
public class AppMainActivity extends BaseActivity{

    private static final String CURRENT_TAB="currentTab";

    private RadioGroup mTables;

    private int currentTab;

    private NewsMainFragment newsfragment;
    private NewsMainFragment picFragment;
    private NewsMainFragment mineFragment;

    private FragmentManager fragmentManager;
    @Override
    public int getLayout() {
        return R.layout.activity_appmain;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager=getSupportFragmentManager();
        if(savedInstanceState!=null){
            List<Fragment> fragments = fragmentManager.getFragments();
            if(fragments!=null) {
                for (Fragment fragment : fragments) {
                    if (fragment instanceof NewsMainFragment) {
                        // 新闻
                        newsfragment = (NewsMainFragment) fragment;
                    } else if (fragment instanceof NewsMainFragment) {
                        // 图片
                        picFragment = (NewsMainFragment) fragment;
                    } else if (fragment instanceof NewsMainFragment) {
                        // 我的
                        mineFragment = (NewsMainFragment) fragment;
                    }
                }
            }
            currentTab=savedInstanceState.getInt(CURRENT_TAB,0);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if(newsfragment!=null){
                fragmentTransaction.add(R.id.main_content,newsfragment);
            }
            if(picFragment!=null){
                fragmentTransaction.add(R.id.main_content,picFragment);
            }
            if(mineFragment!=null){
                fragmentTransaction.add(R.id.main_content,mineFragment);
            }
            hideAllFragment(fragmentTransaction);
            showFragment(fragmentTransaction,currentTab);
        }else {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            hideAllFragment(fragmentTransaction);
            showFragment(fragmentTransaction,0);
        }
    }

    @Override
    protected void initViews() {
        super.initViews();
        mTables= (RadioGroup) findViewById(R.id.tab_main);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mTables.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                hideAllFragment(fragmentTransaction);
                switch (checkedId){
                    case R.id.tab_news:
                        showFragment(fragmentTransaction,0);
                        break;
                    case R.id.tab_pic:
                        showFragment(fragmentTransaction,1);
                        break;
                    case R.id.tab_mine:
                        showFragment(fragmentTransaction,2);
                        break;
                }
            }
        });

    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(newsfragment!=null){
            fragmentTransaction.hide(newsfragment);
        }
        if(picFragment!=null){
            fragmentTransaction.hide(picFragment);
        }
        if(mineFragment!=null){
            fragmentTransaction.hide(mineFragment);
        }
    }

    private void showFragment(FragmentTransaction fragmentTransaction,int type){
        switch (type){
            case 0:
                if(newsfragment==null){
                    newsfragment=new NewsMainFragment();
                    fragmentTransaction.add(R.id.main_content,newsfragment);
                }else {
                    fragmentTransaction.show(newsfragment);
                }
                break;
            case 1:
                if(picFragment==null){
                    picFragment=new NewsMainFragment();
                    fragmentTransaction.add(R.id.main_content,picFragment);
                }else {
                fragmentTransaction.show(picFragment);
            }

                break;
            case 2:
                if(mineFragment==null){
                    mineFragment=new NewsMainFragment();
                    fragmentTransaction.add(R.id.main_content,mineFragment);
                }else {
                    fragmentTransaction.show(mineFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_TAB,currentTab);
    }
}


package com.lichunjing.picturegirls.ui.mainview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.baseui.BaseActivity;
import com.lichunjing.picturegirls.ui.me.MineMainFragment;
import com.lichunjing.picturegirls.ui.news.NewsMainFragment;
import com.lichunjing.picturegirls.ui.pictures.PicturesMainFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/5/26.
 * app主页面
 */
public class AppMainActivity extends BaseActivity{

    private static final String CURRENT_TAB="currentTab";

    private RadioGroup mTables;
    private RadioButton newsTab;
    private RadioButton picTab;
    private RadioButton mineTab;
    private int tabUnselectedColor;
    private int tabSelectedColor;

    private int currentTab;

    private NewsMainFragment newsfragment;
    private PicturesMainFragment picFragment;
    private MineMainFragment mineFragment;

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
                    } else if (fragment instanceof PicturesMainFragment) {
                        // 图片
                        picFragment = (PicturesMainFragment) fragment;
                    } else if (fragment instanceof MineMainFragment) {
                        // 我的
                        mineFragment = (MineMainFragment) fragment;
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

    /**
     * 初始化view
     */
    @Override
    protected void initViews() {
        super.initViews();
        mTables= (RadioGroup) findViewById(R.id.tab_main);
        newsTab= (RadioButton) findViewById(R.id.tab_news);
        picTab= (RadioButton) findViewById(R.id.tab_pic);
        mineTab= (RadioButton) findViewById(R.id.tab_mine);
        tabSelectedColor=getResources().getColor(R.color.tab_seleted_color);
        tabUnselectedColor=getResources().getColor(R.color.tab_unseleted_color);
    }

    /**
     * 设置控件点击事件等
     */
    @Override
    protected void initEvent() {
        super.initEvent();
        mTables.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                hideAllFragment(fragmentTransaction);
                resetColor();
                switch (checkedId){
                    case R.id.tab_news:
                        newsTab.setTextColor(tabSelectedColor);
                        showFragment(fragmentTransaction,0);
                        break;
                    case R.id.tab_pic:
                        picTab.setTextColor(tabSelectedColor);
                        showFragment(fragmentTransaction,1);
                        break;
                    case R.id.tab_mine:
                        mineTab.setTextColor(tabSelectedColor);
                        showFragment(fragmentTransaction,2);
                        break;
                }
            }
        });

    }

    /**
     * 重置radiobutton的字体颜色
     */
    private void resetColor(){
        newsTab.setTextColor(tabUnselectedColor);
        picTab.setTextColor(tabUnselectedColor);
        mineTab.setTextColor(tabUnselectedColor);
    }

    /**
     * 隐藏所有的fragment
     * @param fragmentTransaction
     */
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

    /**
     * 显示制定类型的fragment
     * @param fragmentTransaction
     * @param type
     */
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
                    picFragment=new PicturesMainFragment();
                    fragmentTransaction.add(R.id.main_content,picFragment);
                }else {
                fragmentTransaction.show(picFragment);
            }

                break;
            case 2:
                if(mineFragment==null){
//                    mineFragment=new NewsMainFragment();
                    mineFragment=new MineMainFragment();
                    fragmentTransaction.add(R.id.main_content,mineFragment);
                }else {
                    fragmentTransaction.show(mineFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    /**
     * 应用在后台被杀死时，存储当前显示的fragment的类型
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_TAB,currentTab);
    }

    /**
     * toolbar左边菜单按钮的点击事件
     */
    public void onCustomMenuClick(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}


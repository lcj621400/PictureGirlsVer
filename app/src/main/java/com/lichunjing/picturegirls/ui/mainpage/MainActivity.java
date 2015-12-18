package com.lichunjing.picturegirls.ui.mainpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.base.BasePicActivity;
import com.lichunjing.picturegirls.ui.AboutActivity;
import com.lichunjing.picturegirls.ui.SettingsActivity;
import com.lichunjing.picturegirls.ui.mainpage.fragment.MainFragment;

public class MainActivity extends BasePicActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String[] TAB_TITLE = {"性感美女", "韩日美女", "丝袜美腿", "美女照片", "美女写真", "清纯美女", "性感车模"};
    private static final int[] TAB_ID = {1, 2, 3, 4, 5, 6, 7};

    private ViewPager mViewPager;
    private MainViewPagerAdapter mViewPagerAdapter;
    private TabLayout indicator;

    private long lastBackPressTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initEvent() {
        super.initEvent();


    }

    @Override
    protected void initStatus() {
        super.initStatus();
    }


    @Override
    protected void initViews() {
        super.initViews();
        Toolbar toolbar=initToolBar("美女写真",false,null);
        FloatingActionButton faButton=initFloatActionButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        indicator = (TabLayout) findViewById(R.id.indicator);
        mViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), TAB_ID, TAB_TITLE);
        mViewPager.setAdapter(mViewPagerAdapter);
        indicator.setupWithViewPager(mViewPager);
    }



    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastBackPressTime > 2000) {
                Snackbar.make(mViewPager, "再按一次推出程序", Snackbar.LENGTH_SHORT).show();
                lastBackPressTime = currentTime;
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_push) {
            Toast.makeText(mApplication, item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_recycle) {
            Toast.makeText(mApplication, item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));
        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class MainViewPagerAdapter extends FragmentPagerAdapter {

        private int[] id;
        private String[] title;

        public MainViewPagerAdapter(FragmentManager fm, int[] id, String[] title) {
            super(fm);
            this.id = id;
            this.title = title;
        }

        @Override
        public Fragment getItem(int position) {
            return MainFragment.newInstance(id[position]);
        }

        @Override
        public int getCount() {
            return id.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }


}

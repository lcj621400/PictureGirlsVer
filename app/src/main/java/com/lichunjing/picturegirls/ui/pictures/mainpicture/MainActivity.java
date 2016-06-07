package com.lichunjing.picturegirls.ui.pictures.mainpicture;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.baseui.BaseActivity;
import com.lichunjing.picturegirls.networkevent.NetEvent;
import com.lichunjing.picturegirls.ui.mainview.AppMainActivity;
import com.lichunjing.picturegirls.ui.me.login.LoginActivity;
import com.lichunjing.picturegirls.ui.me.setting.AboutActivity;
import com.lichunjing.picturegirls.ui.me.setting.SettingsActivity;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    private long lastBackPressTime = 0;
    protected NetEvent netEvent;

    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    @Override
    protected void initViews() {
        super.initViews();
        Toolbar toolbar=initToolBar("美女写真",false,null);
        FloatingActionButton faButton=initFloatActionButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        drawer= (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle= new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




    }

    @Override
    protected void initEvent() {
        super.initEvent();
        // 注册网络监听器
        netEvent=new NetEvent(this);
        netEvent.regist();

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
                Snackbar.make(drawer, "再按一次推出程序", Snackbar.LENGTH_SHORT).show();
                lastBackPressTime = currentTime;
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onDestroy() {
        //解除注册监听网络变化的广播接收器
        netEvent.unRegist();
        drawer.removeDrawerListener(toggle);
        super.onDestroy();
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
            drawer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            },250);
        } else if (id == R.id.nav_gallery) {
            drawer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this, AppMainActivity.class));
                }
            },250);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_about) {
            drawer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this, AboutActivity.class));
                }
            },250);

        } else if (id == R.id.nav_setting) {
            drawer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                }
            },250);

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}

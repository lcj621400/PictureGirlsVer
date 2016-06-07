package com.lichunjing.picturegirls.ui.news;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.baseui.BaseActivity;

/**
 * Created by Administrator on 2016/6/6.
 * 新闻详情
 */
public class NewsDetialActivity extends BaseActivity{

    public static final String DETIAL_URL="detialUrl";

    private WebView mWebView;
    private ProgressBar mProgressBar;
    private String url;

    @Override
    protected void initViews() {
        super.initViews();
        initToolBar("", true, getDefaultBackListener());
        initWebView();
        loadData();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_newsdetial;
    }

    private void loadData(){
        url=getIntent().getStringExtra(DETIAL_URL);
        Log.d(TAG,"url:"+url);
        if(TextUtils.isEmpty(url)){
            return;
        }
        mWebView.loadUrl(url);
    }
    private void initWebView(){
        mProgressBar= (ProgressBar) findViewById(R.id.progressbar);
        mWebView= (WebView) findViewById(R.id.new_webview);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
//        settings.setCacheMode();
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress==100){
                    mProgressBar.setVisibility(View.GONE);
                }else {
                    mProgressBar.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setPageTitle(title);
            }
        });
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_BACK&&mWebView.canGoBack()){
                    mWebView.goBack();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mWebView!=null) {
            mWebView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mWebView!=null) {
            mWebView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mWebView!=null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView=null;
        }
    }
}

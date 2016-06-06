package com.lichunjing.picturegirls.ui.news;

import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.baseui.BaseActivity;

/**
 * Created by Administrator on 2016/6/6.
 * 新闻详情
 */
public class NewsDetialActivity extends BaseActivity{

    @Override
    protected void initViews() {
        super.initViews();
        initToolBar("新闻详情",true,getDefaultBackListener());
    }

    @Override
    public int getLayout() {
        return R.layout.activity_newsdetial;
    }
}

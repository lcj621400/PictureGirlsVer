package com.lichunjing.picturegirls.ui.me;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.baseui.BaseNFragment;
import com.lichunjing.picturegirls.ui.mainview.AppMainActivity;

/**
 * Created by Administrator on 2016/5/26.
 * 我的页面
 */
public class MineMainFragment extends BaseNFragment{
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_mine;
    }

    @Override
    protected void initView(View view) {
        Toolbar toolbar= (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setTitle("我的");
        toolbar.setNavigationIcon(R.drawable.main_menu_icon_pink);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity!=null){
                    ((AppMainActivity)activity).onCustomMenuClick("我的");
                }else {
                    activity=getActivity();
                    if(activity!=null){
                        ((AppMainActivity)activity).onCustomMenuClick("我的");
                    }
                }
            }
        });
    }
}

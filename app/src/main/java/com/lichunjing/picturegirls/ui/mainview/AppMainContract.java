package com.lichunjing.picturegirls.ui.mainview;

import com.lichunjing.picturegirls.base.BasePresenter;
import com.lichunjing.picturegirls.base.BaseView;

/**
 * Created by Administrator on 2016/5/27.
 */
public class AppMainContract {

    interface Presenter extends BasePresenter{
        void registNetEvent();
        void unRegistNetEvent();
    }

    interface View extends BaseView<Presenter>{
        void showTable(int type);
        void hideAllTable();
    }
}

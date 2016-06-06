package com.lichunjing.picturegirls.ui.pictures.mainpicture;

import com.lichunjing.picturegirls.base.BasePresenter;
import com.lichunjing.picturegirls.base.BaseView;

/**
 * Created by Administrator on 2016/5/24.
 */
public interface MainPictureContract {

    // presentar
    interface Presenter extends BasePresenter{

        void loadCache();

        void loadNet();

        void refresh();

        void loadMore();

    }
    // view
    interface View extends BaseView<Presenter>{

        void showErrorView();

        void showLoadingView();

        void showContentView();

        void notifyDataChange();
    }

}

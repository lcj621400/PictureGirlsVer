package com.lichunjing.picturegirls.ui.me.login;

import com.lichunjing.picturegirls.base.BasePresenter;
import com.lichunjing.picturegirls.base.BaseView;

/**
 * Created by Administrator on 2016/5/27.
 */
public interface LoginContract{


    interface Presenter extends BasePresenter{
        boolean check(String value);
        void login(String userName,String passWord);
        void toMainPage();
        void hideSoftInput();
    }

    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dissMissLoadingView();
        void showCheckErrorMessage(String message);
        void showLoginSuccessMessage(String message);
    }
}

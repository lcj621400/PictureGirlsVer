package com.lichunjing.picturegirls.ui.me.login;

import android.content.Context;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Administrator on 2016/5/27.
 * 登录界面 Presenter
 */
public class LoginPresenter implements LoginContract.Presenter{

    private LoginActivity view;

    public LoginPresenter(LoginContract.View view){
        this.view= (LoginActivity) view;
    }
    @Override
    public boolean check(String value) {
        if(TextUtils.isEmpty(value)) {
            view.showCheckErrorMessage("请输入用户名和密码");
            return false;
        }
        return true;
    }

    @Override
    public void login(String userName, String passWord) {
        view.showLoginSuccessMessage("登录成功");
        toMainPage();
    }

    @Override
    public void toMainPage() {
        view.showCheckErrorMessage("跳转到主页面");
    }

    @Override
    public void hideSoftInput() {
        InputMethodManager manager= (InputMethodManager) view.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void start() {
        // 自动登录
    }
}

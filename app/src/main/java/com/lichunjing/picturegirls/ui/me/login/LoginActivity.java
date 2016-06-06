package com.lichunjing.picturegirls.ui.me.login;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;

import com.lichunjing.picturegirls.R;
import com.lichunjing.picturegirls.baseui.BaseActivity;

/**
 * Created by Administrator on 2016/5/26.
 * 登录页面
 */
public class LoginActivity extends BaseActivity implements LoginContract.View{


    private LoginPresenter presenter;
    private TextInputLayout userName;
    private TextInputLayout passWord;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(new LoginPresenter(this));
    }

    @Override
    protected void initViews(){
        userName= (TextInputLayout) findViewById(R.id.username);
        passWord = (TextInputLayout) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        login.setOnClickListener(this);
    }

    @Override
    protected void initStatus() {
        super.initStatus();
        initToolBar("登录",true,getDefaultBackListener());
    }

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void showLoadingView() {
        showLoadingDialog("正在登录");
    }

    @Override
    public void dissMissLoadingView() {
        dMissLoadingDialog();
    }

    @Override
    public void showCheckErrorMessage(String message) {
        showToastShort(message);
    }

    @Override
    public void showLoginSuccessMessage(String message) {
        showToastShort(message);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter= (LoginPresenter) presenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                String name=userName.getEditText().getText().toString();
                String word=passWord.getEditText().getText().toString();
                if(presenter.check(name)&&presenter.check(word)){
                    presenter.hideSoftInput();
                    presenter.login(name,word);
                }
                break;
        }
    }
}

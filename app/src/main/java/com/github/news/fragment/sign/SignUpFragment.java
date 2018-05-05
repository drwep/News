package com.github.news.fragment.sign;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.news.R;
import com.github.news.base.BaseMvpDelegate;
import com.github.news.model.User;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author： xuyafan
 * description:
 */
public class SignUpFragment extends BaseMvpDelegate<SignPresenter> implements SignView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edit_sign_up_username)
    TextInputEditText mUsername;
    @BindView(R.id.edit_sign_up_password)
    TextInputEditText mPassword;
    @BindView(R.id.edit_sign_up_re_password)
    TextInputEditText mRePassword;
    @BindView(R.id.btn_sign_up)
    AppCompatButton mBtnSignUp;
    @BindView(R.id.tv_link_sign_in)
    AppCompatTextView mLinkSignIn;


    public static SignUpFragment newInstance() {
        Bundle args = new Bundle();
        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mToolbar.setTitle("注册");
    }


    @OnClick({R.id.btn_sign_up, R.id.tv_link_sign_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_up:
                //注册操作
                if (checkForm()) {
                    final String username = mUsername.getText().toString();
                    final String password = mPassword.getText().toString();

                    mvpPresenter.signUp(username, password);


                }
                break;
            case R.id.tv_link_sign_in:
                start(SignInFragment.newInstance(), SINGLETASK);
                break;
        }
    }

    private boolean checkForm() {
        final String username = mUsername.getText().toString();
        final String password = mPassword.getText().toString();
        final String rePassword = mRePassword.getText().toString();

        boolean isPass = true;

        if (username.isEmpty()) {
            mUsername.setError("请输入用户名");
            isPass = false;
        } else {
            mUsername.setError(null);
        }


        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        if (rePassword.isEmpty() || rePassword.length() < 6 || !(rePassword.equals(password))) {
            mRePassword.setError("密码验证错误");
            isPass = false;
        } else {
            mRePassword.setError(null);
        }

        return isPass;
    }

    @Override
    protected SignPresenter createPresenter() {
        return new SignPresenter(this);
    }

    @Override
    public void signInSuccess(User user) {
        //通知注册成功
        toast("注册成功！");
        //SignUpFragment 出栈,将结果返回给SignInFragment
        Bundle bundle = new Bundle();
        bundle.putString(SignInFragment.ARG_USERNAME, user.getUsername());
        bundle.putString(SignInFragment.ARG_PASSWORD, user.getPassword());
        setFragmentResult(RESULT_OK, bundle);
        pop();
    }

    @Override
    public void signInFail(String msg) {

    }

    @Override
    public void signUpSuccess(User user) {

    }

    @Override
    public void signUpFail(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataFail(String msg) {

    }
}

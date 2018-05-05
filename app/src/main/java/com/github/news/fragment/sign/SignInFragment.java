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
import com.github.news.event.SignInEvent;
import com.github.news.fragment.news.NewsTabFragment;
import com.github.news.model.User;
import com.github.news.util.storage.PreferenceUtil;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * author： xuyafan
 * description:
 */
public class SignInFragment extends BaseMvpDelegate<SignPresenter> implements SignView {
    public static final String ARG_USERNAME = "username";
    public static final String ARG_PASSWORD = "password";
    private static final int REQ_SIGN_UP = 100;
    @BindView(R.id.edit_sign_in_username)
    TextInputEditText mUsername;
    @BindView(R.id.edit_sign_in_password)
    TextInputEditText mPassword;
    @BindView(R.id.btn_sign_in)
    AppCompatButton mBtnSignIn;
    @BindView(R.id.tv_link_sign_up)
    AppCompatTextView mLinkSignUp;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    public static SignInFragment newInstance() {
        Bundle args = new Bundle();
        SignInFragment fragment = new SignInFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mToolbar.setTitle("登录");
    }


    @OnClick({R.id.btn_sign_in, R.id.tv_link_sign_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_in:
                if (checkForm()) {
                    //登录操作
                    final String username = mUsername.getText().toString();
                    final String password = mPassword.getText().toString();
                    mvpPresenter.signIn(username, password);
                }
                break;
            case R.id.tv_link_sign_up:
                startForResult(SignUpFragment.newInstance(), REQ_SIGN_UP);
                break;
        }

    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQ_SIGN_UP && resultCode == RESULT_OK && data != null) {
            mUsername.setText(data.getString(ARG_USERNAME));
            mPassword.setText(data.getString(ARG_PASSWORD));
        }
    }


    private boolean checkForm() {
        final String username = mUsername.getText().toString();
        final String password = mPassword.getText().toString();

        boolean isPass = true;

        if (username.isEmpty()) {
            mUsername.setError("用户名为空");
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

        return isPass;
    }

    @Override
    protected SignPresenter createPresenter() {
        return new SignPresenter(this);
    }

    @Override
    public void signInSuccess(User user) {
        //UserName存入SharedPreference
        PreferenceUtil.put(PreferenceUtil.TAG_USERNAME, user.getUsername());
        PreferenceUtil.put(PreferenceUtil.TAG_PASSWORD, user.getPassword());
        PreferenceUtil.putInt(PreferenceUtil.USER_ID, user.getId());
        PreferenceUtil.putBoolean(PreferenceUtil.IS_LOGIN, true);
        //通知MainActivity 更新username
        EventBusActivityScope.getDefault(_mActivity).post(new SignInEvent(user.getUsername()));
        //SignInFragment 出栈,直到主页面
        popTo(NewsTabFragment.class, false);
    }

    @Override
    public void signInFail(String msg) {
        toast("用户名或密码错误" + msg);
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
        toast(msg);
    }
}

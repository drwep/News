package com.github.news.fragment.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.news.R;
import com.github.news.base.BaseMvpDelegate;
import com.github.news.event.SignInEvent;
import com.github.news.model.User;
import com.github.news.util.storage.PreferenceUtil;

import org.litepal.crud.DataSupport;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * author： xuyafan
 * description:
 */
public class SettingFragment extends BaseMvpDelegate<SettingPresenter> implements SettingView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_username)
    TextView mTvUserName;


    public static SettingFragment newInstance() {
        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_setting;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mToolbar.setTitle("设置");
        initToolbarMenu(mToolbar);
    }


    @OnClick(R.id.tv_username)
    public void onUserNameClicked() {
        new MaterialDialog.Builder(_mActivity)
                .title("设置")
                .content("修改用户名")
                .input("用户名", PreferenceUtil.get(PreferenceUtil.TAG_USERNAME), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        int userId = PreferenceUtil.getInt(PreferenceUtil.USER_ID);
                        String username = input.toString();
                        mvpPresenter.updateUsername(userId, username);
                    }
                }).show();
    }

    @OnClick(R.id.tv_password)
    public void onPasswordClicked() {
        new MaterialDialog.Builder(_mActivity)
                .title("设置")
                .content("请输入旧密码")
                .inputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
                .input("旧密码", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        String password = input.toString();
                        String truePassword = PreferenceUtil.get(PreferenceUtil.TAG_PASSWORD);
                        if (truePassword.equals(password)) {
                            inputNewPassword();
                        } else {
                            toast("密码错误");
                        }
                    }
                }).show();
    }

    private void inputNewPassword() {
        new MaterialDialog.Builder(_mActivity)
                .title("设置")
                .content("请输入新密码")
                .inputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
                .input("新密码", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        String password = input.toString();
                        int userId = PreferenceUtil.getInt(PreferenceUtil.USER_ID);
                        mvpPresenter.updatePassword(userId, password);
                        toast("修改密码成功");
                    }
                }).show();
    }


    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter(this);
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

    @Override
    public void updateUsername(User user) {
        PreferenceUtil.put(PreferenceUtil.TAG_USERNAME, user.getUsername());
        //通知MainActivity 更新username
        EventBusActivityScope.getDefault(_mActivity).post(new SignInEvent(user.getUsername()));
        toast("修改用户名成功");
    }

    @Override
    public void updatePassword(User user) {
        PreferenceUtil.put(PreferenceUtil.TAG_PASSWORD, user.getPassword());
        toast("修改密码成功");
    }
}

package com.github.news.fragment.setting;


import com.github.news.base.BasePresenter;
import com.github.news.model.User;
import com.github.news.net.ApiCallback;
import com.github.news.net.Response;
import com.github.news.net.RetrofitClient;

/**
 * author： xuyafan
 * description:
 */
public class SettingPresenter extends BasePresenter<SettingView> {
    public SettingPresenter(SettingView mvpView) {
        super(mvpView);
    }

    public void updateUsername(int id, String username) {
        mvpView.showLoading();
        addSubscription(RetrofitClient.getApi().updateUsername(id, username), new ApiCallback<Response<User>>(mvpView) {
            @Override
            public void onSuccess(Response<User> response) {
                if (response.getStatus().equals("success")) {
                    mvpView.updateUsername(response.getData());
                } else if (response.getStatus().equals("fail")) {
                    mvpView.getDataFail(response.getErrorCode());
                }

            }

        });
    }

    public void updatePassword(int id, String password) {
        mvpView.showLoading();
        addSubscription(RetrofitClient.getApi().updatePassword(id, password), new ApiCallback<Response<User>>(mvpView) {
            @Override
            public void onSuccess(Response<User> response) {
                if (response.getStatus().equals("success")) {
                    mvpView.updatePassword(response.getData());
                } else if (response.getStatus().equals("fail")) {
                    mvpView.getDataFail(response.getErrorCode());
                }

            }

        });
    }
}



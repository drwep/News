package com.github.news.fragment.sign;


import com.github.news.base.BasePresenter;
import com.github.news.model.User;
import com.github.news.net.ApiCallback;
import com.github.news.net.Response;
import com.github.news.net.RetrofitClient;

/**
 * author： xuyafan
 * description:
 */
public class SignPresenter extends BasePresenter<SignView> {
    public SignPresenter(SignView mvpView) {
        super(mvpView);
    }

    public void signIn(String username, String password) {
        mvpView.showLoading();
        addSubscription(RetrofitClient.getApi().signIn(username, password), new ApiCallback<Response<User>>(mvpView) {
            @Override
            public void onSuccess(Response<User> response) {
                if (response.getStatus().equals("success")) {
                    mvpView.signInSuccess(response.getData());
                } else if (response.getStatus().equals("fail")) {
                    mvpView.signInFail(response.getErrorCode());
                }

            }

        });
    }

    public void signUp(String username, String password) {
        mvpView.showLoading();
        addSubscription(RetrofitClient.getApi().signUp(username, password), new ApiCallback<Response<User>>(mvpView) {
            @Override
            public void onSuccess(Response<User> response) {
                if (response.getStatus().equals("success")) {
                    mvpView.signInSuccess(response.getData());
                } else if (response.getStatus().equals("fail")) {
                    mvpView.signInFail(response.getErrorCode());
                }
            }

        });
    }
}

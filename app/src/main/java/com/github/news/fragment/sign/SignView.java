package com.github.news.fragment.sign;


import com.github.news.base.BaseView;
import com.github.news.model.User;

/**
 * author： xuyafan
 * description:
 */
public interface SignView extends BaseView {
    void signInSuccess(User user);

    void signInFail(String msg);

    void signUpSuccess(User user);

    void signUpFail(String msg);
}

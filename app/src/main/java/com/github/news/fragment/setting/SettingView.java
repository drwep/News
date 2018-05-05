package com.github.news.fragment.setting;

import com.github.news.base.BaseView;
import com.github.news.model.User;

/**
 * author： xuyafan
 * description:
 */
public interface SettingView extends BaseView {
    void updateUsername(User user);

    void updatePassword(User user);
}

package com.github.news.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * author： xuyafan
 * description:
 */
public abstract class BaseEventBusDelegate extends BaseDelegate {
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        EventBusActivityScope.getDefault(_mActivity).register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
    }
}

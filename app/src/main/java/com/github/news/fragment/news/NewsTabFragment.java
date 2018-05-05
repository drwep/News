package com.github.news.fragment.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;


import com.github.news.R;
import com.github.news.base.BaseDelegate;

import butterknife.BindView;

/**
 * author： xuyafan
 * description:
 */
public class NewsTabFragment extends BaseDelegate {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;


    public static NewsTabFragment newInstance() {
        Bundle args = new Bundle();
        NewsTabFragment fragment = new NewsTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_viewpager;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mToolbar.setTitle("新闻");
        initToolbarMenu(mToolbar);

        mTab.addTab(mTab.newTab());


        mViewPager.setAdapter(new NewsTabAdapter(getChildFragmentManager(),
                "公告","社团新闻","校园要闻"));
        mTab.setupWithViewPager(mViewPager);
    }


}

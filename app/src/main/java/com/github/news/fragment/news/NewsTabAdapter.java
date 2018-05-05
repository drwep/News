package com.github.news.fragment.news;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.github.news.fragment.news.edu.EduNewsFragment;


/**
 * author： xuyafan
 * description:
 */
public class NewsTabAdapter extends FragmentPagerAdapter {
    private String[] mTitles;

    public NewsTabAdapter(FragmentManager fm, String... titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return EduNewsFragment.newInstance("公告");
            case 1:
                return EduNewsFragment.newInstance("社团新闻");
            case 2:
                return EduNewsFragment.newInstance("校园要闻");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}

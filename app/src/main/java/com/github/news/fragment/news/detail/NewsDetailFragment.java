package com.github.news.fragment.news.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.news.R;
import com.github.news.base.BaseDelegate;
import com.github.news.model.News;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import butterknife.BindView;

/**
 * author： xuyafan
 * description:
 */
public class NewsDetailFragment extends BaseDelegate {

    private static final String ARG_NEWS = "news";
    private static final RequestOptions RECYCLER_OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();
    @BindView(R.id.iv)
    ImageView mImageView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_detailString)
    TextView mTvDetailString;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    News mNews;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_author)
    TextView mTvAuthor;
    @BindView(R.id.tv_date)
    TextView mTvDate;


    public static NewsDetailFragment newInstance(News news) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_NEWS, news);
        NewsDetailFragment fragment = new NewsDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_news_detail;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mNews = (News) args.getSerializable(ARG_NEWS);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        Glide.with(_mActivity)
                .load(mNews.getImg())
                .apply(RECYCLER_OPTIONS)
                .into(mImageView);

        mToolbar.setTitle(mNews.getTitle());

        mTvDetailString.setText(mNews.getDetailString());
        mTvTitle.setText(mNews.getTitle());
        mTvAuthor.setText(mNews.getAuthor());
        mTvDate.setText(mNews.getDate());

        //为RefreshLayout设置Header
        ClassicsHeader header = new ClassicsHeader(_mActivity);
        header.setPrimaryColor(this.getResources().getColor(R.color.green));
        header.setAccentColor(Color.WHITE);
        mRefreshLayout.setRefreshHeader(header);
    }


}

package com.github.news.fragment.news.edu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.news.R;
import com.github.news.base.BaseMvpDelegate;
import com.github.news.fragment.news.detail.NewsDetailFragment;
import com.github.news.model.News;
import com.github.news.util.log.Log;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * author： xuyafan
 * description:
 */
public class EduNewsFragment extends BaseMvpDelegate<EduNewsPresenter> implements EduNewsView {
    private static final String ARG_TYPE = "type";

    @BindView(R.id.recy)
    RecyclerView mRecy;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;

    @BindColor(R.color.green)
    int green;

    EduNewsAdapter mAdapter;
    List<News> mNewsDataList;

    String type;


    public static EduNewsFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        EduNewsFragment fragment = new EduNewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected EduNewsPresenter createPresenter() {
        return new EduNewsPresenter(this);
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_recy;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsDataList = new ArrayList<>();
        Bundle args = getArguments();
        if (args != null) {
            type = args.getString(ARG_TYPE);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mAdapter = new EduNewsAdapter(mNewsDataList);
        mRecy.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecy.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                assert getParentFragment() != null;
                ((SupportFragment) getParentFragment()).start(NewsDetailFragment.newInstance(mNewsDataList.get(position)));
            }
        });

        ClassicsHeader header = new ClassicsHeader(_mActivity);
        header.setPrimaryColor(green);
        header.setAccentColor(Color.WHITE);
        mSmartRefreshLayout.setRefreshHeader(header);
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getEduNews(type);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mvpPresenter.getEduNews(type);
    }


    @Override
    public void getDataSuccess(List<News> data) {
        mNewsDataList = data;
        mSmartRefreshLayout.finishRefresh();
        mAdapter.setNewData(mNewsDataList);
        if (mNewsDataList.size() == 0) {
            mAdapter.setEmptyView(R.layout.view_empty, (ViewGroup) mRecy.getParent());
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataFail(String msg) {
        Log.e(msg);
        mAdapter.setEmptyView(R.layout.view_error, (ViewGroup) mRecy.getParent());
    }


}

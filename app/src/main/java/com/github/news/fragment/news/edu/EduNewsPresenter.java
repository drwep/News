package com.github.news.fragment.news.edu;


import com.github.news.base.BasePresenter;
import com.github.news.model.News;
import com.github.news.net.ApiCallback;
import com.github.news.net.Response;
import com.github.news.net.RetrofitClient;

import java.util.List;

/**
 * author： xuyafan
 * description:
 */
public class EduNewsPresenter extends BasePresenter<EduNewsView> {
    public EduNewsPresenter(EduNewsView mvpView) {
        super(mvpView);
    }


    /**
     * 通过Retrofit2+RxJava2+OkHttp3从网络API获取新闻
     */
    public void getEduNews(String type) {
        mvpView.showLoading();
        addSubscription(RetrofitClient.getApi().getNewsByType(type), new ApiCallback<Response<List<News>>>(mvpView) {
            @Override
            public void onSuccess(Response<List<News>> response) {
                if (response.getStatus().equals("success")) {
                    mvpView.getDataSuccess(response.getData());
                } else if (response.getStatus().equals("fail")) {
                    mvpView.getDataFail(response.getErrorCode());
                }
            }
        });
    }


}

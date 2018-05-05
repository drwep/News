package com.github.news.fragment.news.edu;


import com.github.news.base.BaseView;
import com.github.news.model.News;

import java.util.List;

/**
 * author： xuyafan
 * description:
 */
public interface EduNewsView extends BaseView {

    void getDataSuccess(List<News> data);

}

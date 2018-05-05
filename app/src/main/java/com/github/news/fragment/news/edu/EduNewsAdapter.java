package com.github.news.fragment.news.edu;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.news.R;
import com.github.news.model.News;


import java.util.List;

/**
 * author： xuyafan
 * description:
 */
public class EduNewsAdapter extends BaseQuickAdapter<News, BaseViewHolder> {

    private static final RequestOptions RECYCLER_OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();

    public EduNewsAdapter(@Nullable List<News> data) {
        super(R.layout.item_news, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, News item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_intro, item.getIntro())
                .setText(R.id.tv_date, item.getDate())
                .setText(R.id.tv_author, item.getAuthor());

        Glide.with(mContext)
                .load(item.getImg())
                .apply(RECYCLER_OPTIONS)
                .into((ImageView) helper.getView(R.id.img));
    }
}

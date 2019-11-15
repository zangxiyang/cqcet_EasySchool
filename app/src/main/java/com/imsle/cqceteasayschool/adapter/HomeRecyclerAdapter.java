package com.imsle.cqceteasayschool.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imsle.cqceteasayschool.R;
import com.imsle.cqceteasayschool.model.News;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Seale on 2019/11/11.
 * Author:臧锡洋
 * 功能描述: 首页fragment中RecyclerView的适配器
 */
public class HomeRecyclerAdapter extends BaseQuickAdapter<News, BaseViewHolder> {

    private List<News> newsList;
    private OnItemClickListener onItemClickListener;

    public HomeRecyclerAdapter(int layoutResId, @Nullable List<News> data) {
        super(layoutResId, data);
        newsList = data;
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, News item) {

        TextView home_recycler_title;
        TextView home_recycler_subTitle;
        TextView home_recycler_date;

        home_recycler_title = helper.getView(R.id.home_recycler_title);
        home_recycler_subTitle = helper.getView(R.id.home_recycler_subTitle);
        home_recycler_date = helper.getView(R.id.home_recycler_date);

        home_recycler_title.setText(newsList.get(helper.getAdapterPosition()-getHeaderLayoutCount()).getTitle());
        home_recycler_subTitle.setText(newsList.get(helper.getAdapterPosition()-getHeaderLayoutCount()).getSubTitle());
        home_recycler_date.setText(newsList.get(helper.getAdapterPosition()-getHeaderLayoutCount()).getDate());


        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.OnItemClick(view, newsList.get(helper.getAdapterPosition()-1), helper.getAdapterPosition());
                }
            }
        });
    }


    public interface OnItemClickListener {
        void OnItemClick(View view, News news, int position);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

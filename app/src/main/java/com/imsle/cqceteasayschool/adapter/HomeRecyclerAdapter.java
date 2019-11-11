package com.imsle.cqceteasayschool.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imsle.cqceteasayschool.R;
import com.imsle.cqceteasayschool.model.News;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Seale on 2019/11/11.
 * Author:臧锡洋
 * 功能描述: 首页fragment中RecyclerView的适配器
 */
public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {

    private List<News> newsList;
    private OnItemClickListener onItemClickListener;

    public HomeRecyclerAdapter(List<News> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.home_recycler_title.setText(news.getTitle());
        holder.home_recycler_subTitle.setText(news.getSubTitle());
        holder.home_recycler_date.setText(news.getDate());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView home_recycler_title;
        TextView home_recycler_subTitle;
        TextView home_recycler_date;

        public ViewHolder (View view)
        {
            super(view);
            home_recycler_title = view.findViewById(R.id.home_recycler_title);
            home_recycler_subTitle = view.findViewById(R.id.home_recycler_subTitle);
            home_recycler_date = view.findViewById(R.id.home_recycler_date);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null){
                        onItemClickListener.OnItemClick(view,newsList.get(getAdapterPosition()),getAdapterPosition());
                    }
                }
            });
        }

    }


    public interface OnItemClickListener{
        public void OnItemClick(View view  , News news , int position);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}

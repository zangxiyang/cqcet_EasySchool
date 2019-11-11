package com.imsle.cqceteasayschool.adapter;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Seale on 2019/11/11.
 * Author:臧锡洋
 * 功能描述: 扩展RecyclerView的功能,使其能够在添加头部和尾部
 */
public class HeaderViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //被包装的Adapter。
    private RecyclerView.Adapter mAdapter;

    //用于存放HeaderView
    private final List<FixedViewInfo> mHeaderViewInfos = new ArrayList<>();

    //用于存放FooterView
    private final List<FixedViewInfo> mFooterViewInfos = new ArrayList<>();

    //用于监听被包装的Adapter的数据变化的监听器。它将被包装的Adapter的数据变化映射成HeaderViewAdapter的变化。
    private RecyclerView.AdapterDataObserver mObserver = new RecyclerView.AdapterDataObserver() {
        //这里是具体的代码实现，因为篇幅的关系，在这里就不放出来了。
    };

    public HeaderViewAdapter(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
        if (mAdapter != null) {
            //注册mAdapter的数据变化监听
            mAdapter.registerAdapterDataObserver(mObserver);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 根据viewType查找对应的HeaderView 或 FooterView。如果没有找到则表示该viewType是普通的列表项。
        View view = findViewForInfos(viewType);
        if (view != null) {
            return new ViewHolder(view);
        } else {
            //交由mAdapter处理。
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // 如果是HeaderView 或者是 FooterView则不绑定数据。
        // 因为HeaderView和FooterView是由外部传进来的，它们不由列表去更新。
        if (isHeader(position) || isFooter(position)) {
            return;
        }

        //将列表实际的position调整成mAdapter对应的position。
        //交由mAdapter处理。
        int adjPosition = position - getHeadersCount();
        mAdapter.onBindViewHolder(holder, adjPosition);
    }

    @Override
    public int getItemCount() {
        return mHeaderViewInfos.size() + mFooterViewInfos.size()
                + (mAdapter == null ? 0 : mAdapter.getItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        //如果当前item是HeaderView，则返回HeaderView对应的itemViewType。
        if (isHeader(position)) {
            return mHeaderViewInfos.get(position).itemViewType;
        }

        //如果当前item是HeaderView，则返回HeaderView对应的itemViewType。
        if (isFooter(position)) {
            return mFooterViewInfos.get(position - mHeaderViewInfos.size() - mAdapter.getItemCount()).itemViewType;
        }

        //将列表实际的position调整成mAdapter对应的position。
        //交由mAdapter处理。
        int adjPosition = position - getHeadersCount();
        return mAdapter.getItemViewType(adjPosition);
    }

    /**
     * 判断当前位置是否是头部View。
     *
     * @param position 这里的position是整个列表(包含HeaderView和FooterView)的position。
     * @return
     */
    public boolean isHeader(int position) {
        return position < getHeadersCount();
    }

    /**
     * 判断当前位置是否是尾部View。
     *
     * @param position 这里的position是整个列表(包含HeaderView和FooterView)的position。
     * @return
     */
    public boolean isFooter(int position) {
        return getItemCount() - position <= getFootersCount();
    }

    /**
     * 获取HeaderView的个数
     *
     * @return
     */
    public int getHeadersCount() {
        return mHeaderViewInfos.size();
    }

    /**
     * 获取FooterView的个数
     *
     * @return
     */
    public int getFootersCount() {
        return mFooterViewInfos.size();
    }

    /**
     * 添加HeaderView
     *
     * @param view
     */
    public void addHeaderView(View view) {
        addHeaderView(view, generateUniqueViewType());
    }

    private void addHeaderView(View view, int viewType) {
        //包装HeaderView数据并添加到列表
        FixedViewInfo info = new FixedViewInfo();
        info.view = view;
        info.itemViewType = viewType;
        mHeaderViewInfos.add(info);
        notifyDataSetChanged();
    }

    /**
     * 添加FooterView
     *
     * @param view
     */
    public void addFooterView(View view) {
        addFooterView(view, generateUniqueViewType());
    }

    private void addFooterView(View view, int viewType) {
        // 包装FooterView数据并添加到列表
        FixedViewInfo info = new FixedViewInfo();
        info.view = view;
        info.itemViewType = viewType;
        mFooterViewInfos.add(info);
        notifyDataSetChanged();
    }

    /**
     * 生成一个唯一的数，用于标识HeaderView或FooterView的type类型，并且保证类型不会重复。
     *
     * @return
     */
    private int generateUniqueViewType() {
        int count = getItemCount();
        while (true) {
            //生成一个随机数。
            int viewType = (int) (Math.random() * Integer.MAX_VALUE) + 1;

            //判断该viewType是否已使用。
            boolean isExist = false;
            for (int i = 0; i < count; i++) {
                if (viewType == getItemViewType(i)) {
                    isExist = true;
                    break;
                }
            }

            //判断该viewType还没被使用，则返回。否则进行下一次循环，重新生成随机数。
            if (!isExist) {
                return viewType;
            }
        }
    }

    /**
     * 根据viewType查找对应的HeaderView 或 FooterView。没有找到则返回null。
     *
     * @param viewType 查找的viewType
     * @return
     */
    private View findViewForInfos(int viewType) {
        for (FixedViewInfo info : mHeaderViewInfos) {
            if (info.itemViewType == viewType) {
                return info.view;
            }
        }

        for (FixedViewInfo info : mFooterViewInfos) {
            if (info.itemViewType == viewType) {
                return info.view;
            }
        }

        return null;
    }

    /**
     * 用于包装HeaderView和FooterView的数据类
     */
    private class FixedViewInfo {
        //保存HeaderView或FooterView
        View view;

        //保存HeaderView或FooterView对应的viewType。
        int itemViewType;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}

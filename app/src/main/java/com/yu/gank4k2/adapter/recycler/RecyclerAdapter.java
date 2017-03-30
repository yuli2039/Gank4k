package com.yu.gank4k2.adapter.recycler;

import android.content.Context;
import android.view.LayoutInflater;

import com.yu.gank4k2.adapter.common.ItemViewDelegate;
import com.yu.gank4k2.adapter.common.ViewHolder;

import java.util.List;

/**
 * Created by yu on 2017/3/30.
 */

public abstract class RecyclerAdapter<T> extends MultiTypeRecyclerAdapter<T> {
    protected int mLayoutId;
    protected LayoutInflater mInflater;

    public RecyclerAdapter(final Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForThisViewType(T item, int position) {
                return true;
            }

            @Override
            public void bindData(ViewHolder holder, T t, int position) {
                RecyclerAdapter.this.bindData(holder, t, position);
            }
        });
    }

    protected abstract void bindData(ViewHolder holder, T t, int position);


}

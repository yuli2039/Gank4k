package com.yu.gank4k2.adapter.recycler.wrapper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import com.yu.gank4k2.adapter.common.loadmore.ILoadMore;
import com.yu.gank4k2.adapter.recycler.ViewHolder4r;
import com.yu.gank4k2.adapter.recycler.utils.SpanSizeUtils;

import static com.yu.gank4k2.adapter.common.loadmore.ILoadMore.Status.COMPLETE;
import static com.yu.gank4k2.adapter.common.loadmore.ILoadMore.Status.LOADING;
import static com.yu.gank4k2.adapter.common.loadmore.ILoadMore.Status.NOMORE;

/**
 * recyclerview加载更多的包装类
 */
public class LoadMoreWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 1;

    private RecyclerView.Adapter mInnerAdapter;
    private ILoadMore mLoadMoreView;
    private OnLoadMoreListener mOnLoadMoreListener;

    private boolean isLoading;
    private boolean isNoMore;
    private boolean hasScrolled;

    public LoadMoreWrapper(RecyclerView.Adapter adapter, ILoadMore loadMoreView) {
        mInnerAdapter = adapter;
        mLoadMoreView = loadMoreView;
    }

    private boolean isShowLoadMore(int position) {
        return mLoadMoreView != null && (position >= getRealItemCount());
    }

    public RecyclerView.Adapter getInnerAdapter() {
        return mInnerAdapter;
    }

    public int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }

    @Override
    public int getItemCount() {
        return getRealItemCount() + (mLoadMoreView != null ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowLoadMore(position)) {
            return ITEM_TYPE_LOAD_MORE;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            return ViewHolder4r.createViewHolder(parent.getContext(), mLoadMoreView.getView());
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isShowLoadMore(position)) {
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        SpanSizeUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new SpanSizeUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                if (isShowLoadMore(position)) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });

        if (null != mLoadMoreView)
            initLoadMore(recyclerView);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);

        if (isShowLoadMore(holder.getLayoutPosition())) {
            SpanSizeUtils.setFullSpan(holder);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mInnerAdapter.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
        return mInnerAdapter.onFailedToRecycleView(holder);
    }

    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        mInnerAdapter.unregisterAdapterDataObserver(observer);
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        mInnerAdapter.registerAdapterDataObserver(observer);
    }

    private void initLoadMore(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int state) {
                super.onScrollStateChanged(recyclerView, state);

                if (mOnLoadMoreListener != null
                        && state == RecyclerView.SCROLL_STATE_IDLE
                        && !isLoading) {

                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    int lastVisibleItemPosition;

                    if (layoutManager instanceof GridLayoutManager) {
                        lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                        lastVisibleItemPosition = findMax(into);
                    } else {
                        lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    }

                    if (layoutManager.getChildCount() > 0
                            && hasScrolled
                            && lastVisibleItemPosition >= layoutManager.getItemCount() - 1
//                            && layoutManager.getItemCount() > layoutManager.getChildCount()
                            && !isNoMore) {

                        isLoading = true;
                        mLoadMoreView.setState(LOADING);
                        mOnLoadMoreListener.onLoadMore();
                    }
                } else if (state == RecyclerView.SCROLL_STATE_DRAGGING) {
                    hasScrolled = false;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                hasScrolled = true;
            }
        });
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max)
                max = value;
        }
        return max;
    }

    /**
     * 重置状态
     */
    public void reset() {
        isLoading = false;
        isNoMore = false;
        mLoadMoreView.setState(COMPLETE);
    }

    /**
     * 加载数据完成后，重置状态
     */
    public void loadMoreComplete() {
        reset();
//        mInnerAdapter.notifyDataSetChanged();
    }

    /**
     * 设置footer没有更多，不能再次触发加载监听
     */
    public void setNoMore() {
        isLoading = false;
        isNoMore = true;
        mLoadMoreView.setState(NOMORE);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public LoadMoreWrapper setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        if (loadMoreListener != null) {
            mOnLoadMoreListener = loadMoreListener;
        }
        return this;
    }

}

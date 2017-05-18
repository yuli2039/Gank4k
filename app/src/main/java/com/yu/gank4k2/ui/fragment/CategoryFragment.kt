package com.yu.gank4k2.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.yu.gank4k2.App
import com.yu.gank4k2.R
import com.yu.gank4k2.adapter.common.ItemViewDelegate
import com.yu.gank4k2.adapter.common.ViewHolder
import com.yu.gank4k2.adapter.common.loadmore.DefaultLoadMoreFooter
import com.yu.gank4k2.adapter.recycler.MultiTypeRecyclerAdapter
import com.yu.gank4k2.adapter.recycler.wrapper.LoadMoreWrapper
import com.yu.gank4k2.base.BaseMvpFragment
import com.yu.gank4k2.util.CategoryType
import kotlinx.android.synthetic.main.fragment_common_list.*
import me.yu.drxx.entity.GankEntity
import me.yu.drxx.mvp.CategoryContract
import me.yu.drxx.mvp.presenter.CategoryPresenter
import java.util.*

/**
 * Created by yu on 2017/3/7.
 */
class CategoryFragment : BaseMvpFragment<CategoryPresenter>(), CategoryContract.View {

    private var adapter: MultiTypeRecyclerAdapter<GankEntity>? = null
    private var loadMoreWrapper: LoadMoreWrapper? = null
    private var dataList: ArrayList<GankEntity> = ArrayList()

    private val type: String by lazy { arguments.getString(KEY) }

    companion object {
        const val KEY = "type"
        const val PAGE_SIZE = 10

        fun newInstance(type: String): CategoryFragment {
            val fragment = CategoryFragment()
            val bundle = Bundle()
            bundle.putString(KEY, type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override val layoutId: Int = R.layout.fragment_common_list

    override fun afterInitView() {
        refreshLayout.setOnRefreshListener { mPresenter.refresh(type, PAGE_SIZE) }

        adapter = CommonAdapter(activity, dataList, type)

        recyclerView.layoutManager = LinearLayoutManager(activity)

        loadMoreWrapper = LoadMoreWrapper(adapter, DefaultLoadMoreFooter(activity))
        loadMoreWrapper?.setOnLoadMoreListener { mPresenter.loadMore(type) }
        recyclerView.adapter = loadMoreWrapper

        initDataLazy()
    }

    override fun injectComponent() {
        App.apiComponent?.inject(this)
        mPresenter.attachView(this)
    }

    fun initDataLazy() {
        refreshLayout.isRefreshing = true
        mPresenter.refresh(type, PAGE_SIZE)
    }

    override fun onRefresh(data: List<GankEntity>) {
        dataList.clear()
        if (data.isNotEmpty()) {
            dataList.addAll(data)
        }
        adapter?.notifyDataSetChanged()
    }

    override fun onLoadMore(data: List<GankEntity>) {
        dataList.addAll(data)
        loadMoreWrapper?.loadMoreComplete()
        adapter?.notifyDataSetChanged()
    }

    override fun onNoMore() {
        loadMoreWrapper?.setNoMore()
    }

    override fun hideLoading() {
        refreshLayout?.isRefreshing = false
        loadMoreWrapper?.loadMoreComplete()
    }
}

class CommonAdapter(context: Context?, datas: List<GankEntity>?, val type: String)
    : MultiTypeRecyclerAdapter<GankEntity>(context, datas) {
    init {
        addItemViewDelegate(object : ItemViewDelegate<GankEntity> {
            override fun getItemViewLayoutId() = R.layout.item_android_ios

            override fun isForThisViewType(item: GankEntity?, position: Int)
                    = (type == CategoryType.ANDROID || type == CategoryType.IOS)

            override fun bindData(holder: ViewHolder?, entity: GankEntity?, position: Int) {
                holder?.setText(R.id.tvDesc, entity?.desc)
                        ?.setText(R.id.tvAuthor, entity?.who)
                        ?.setText(R.id.tvDate, entity?.publishedAt)
            }
        }).addItemViewDelegate(object : ItemViewDelegate<GankEntity> {
            override fun getItemViewLayoutId(): Int = R.layout.item_girls

            override fun isForThisViewType(item: GankEntity?, position: Int)
                    = (type == CategoryType.GIRLS)

            override fun bindData(holder: ViewHolder?, entity: GankEntity?, position: Int) {
                Glide.with(context).load(entity?.url).centerCrop().into(holder?.getView(R.id.ivImage))
            }
        })
    }
}

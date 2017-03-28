package com.yu.gank4k2.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.yu.gank4k2.App
import com.yu.gank4k2.R
import com.yu.gank4k2.base.BaseMvpFragment
import com.yu.gank4k2.base.adapter.common.ViewHolder
import com.yu.gank4k2.base.adapter.common.loadmore.DefaultLoadMoreFooter
import com.yu.gank4k2.base.adapter.recycler.RecyclerAdapter
import com.yu.gank4k2.base.adapter.recycler.wrapper.LoadMoreWrapper
import com.yu.gank4k2.di.moudle.CategoryListModule
import kotlinx.android.synthetic.main.fragment_common_list.*
import me.yu.drxx.di.component.DaggerCategoryListComponent
import me.yu.drxx.entity.GankEntity
import me.yu.drxx.mvp.ListContract
import me.yu.drxx.mvp.presenter.ListPresenter

/**
 * Created by yu on 2017/3/7.
 */
class CategoryListFragment : BaseMvpFragment<ListPresenter>(), ListContract.View {

    private var adapter: RecyclerAdapter<GankEntity>? = null
    private var loadMoreWrapper: LoadMoreWrapper? = null
    private var dataList: ArrayList<GankEntity> = ArrayList()

    private val type: String by lazy { arguments.getString(KEY) }

    companion object {
        const val TYPE_ANDROID = "Android"
        const val TYPE_IOS = "iOS"
        const val TYPE_GIRLS = "福利"
        const val TYPE_COLLECTION = "Collection"
        const val KEY = "type"
        const val PAGE_SIZE = 10

        fun newInstance(type: String): CategoryListFragment {
            val fragment = CategoryListFragment()
            val bundle = Bundle()
            bundle.putString(KEY, type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override val layoutId: Int = R.layout.fragment_common_list

    override fun afterInitView() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        refreshLayout.setOnRefreshListener { mPresenter.refresh(type, PAGE_SIZE) }

        adapter = AndroidAdapter(activity, dataList)
        loadMoreWrapper = LoadMoreWrapper(adapter, DefaultLoadMoreFooter(activity))
        loadMoreWrapper!!.setOnLoadMoreListener { mPresenter.loadMore(type) }
        recyclerView.adapter = loadMoreWrapper

        refreshLayout.isRefreshing = true
        mPresenter.refresh(type, PAGE_SIZE)
    }

    override fun injectComponent() {
        DaggerCategoryListComponent.builder()
                .apiComponent(App.apiComponent)
                .categoryListModule(CategoryListModule(this))
                .build()
                .inject(this)
    }

    override fun onRefresh(data: List<GankEntity>?) {
        dataList.clear()
        if (data != null && data.isNotEmpty()) {
            dataList.addAll(data)
        }
        adapter!!.notifyDataSetChanged()
    }

    override fun onLoadMore(data: List<GankEntity>?) {
        if (data != null)
            dataList.addAll(data)
        loadMoreWrapper?.loadMoreComplete()
        adapter!!.notifyDataSetChanged()
    }

    override fun onNoMore() {
        loadMoreWrapper?.setNoMore()
    }

    override fun hideLoading() {
        refreshLayout.isRefreshing = false
        loadMoreWrapper?.loadMoreComplete()
    }
}

class AndroidAdapter(context: Context?, datas: List<GankEntity>?)
    : RecyclerAdapter<GankEntity>(context, R.layout.item_android_ios, datas) {

    override fun bindData(holder: ViewHolder?, t: GankEntity?, position: Int) {
        holder?.setText(R.id.tvDesc, t?.desc)
    }
}

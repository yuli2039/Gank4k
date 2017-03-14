package com.yu.gank4k2.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.yu.gank4k2.App
import com.yu.gank4k2.R
import com.yu.gank4k2.base.BaseFragment
import com.yu.gank4k2.base.adapter.common.ViewHolder
import com.yu.gank4k2.base.adapter.common.loadmore.DefaultLoadMoreFooter
import com.yu.gank4k2.base.adapter.recycler.RecyclerAdapter
import com.yu.gank4k2.base.adapter.recycler.wrapper.LoadMoreWrapper
import com.yu.gank4k2.di.moudle.CategoryListModule
import kotlinx.android.synthetic.main.fragment_common_list.*
import me.yu.drxx.di.component.DaggerAndroidComponent
import me.yu.drxx.entity.GankEntity
import me.yu.drxx.mvp.ListContract
import me.yu.drxx.mvp.presenter.ListPresenter

/**
 * Created by yu on 2017/3/7.
 */
class AndroidFragment : BaseFragment<ListPresenter>(), ListContract.View {

    private var adapter: RecyclerAdapter<GankEntity>? = null
    private var loadMoreWrapper: LoadMoreWrapper? = null
    private var dataList: ArrayList<GankEntity> = ArrayList()

    private val type: String by lazy { arguments.getString(KEY) }

    companion object {
        const val TYPE_VALUE = "android"
        const val KEY = "type"
        const val PAGE_SIZE = 10

        public fun newInstance(type: String): AndroidFragment {
            val fragment = AndroidFragment()
            val bundle = Bundle()
            bundle.putString(KEY, TYPE_VALUE)
            fragment.arguments = bundle
            return fragment
        }
    }

    override val layoutId: Int = R.layout.fragment_common_list

    override fun afterInitView() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        refreshLayout.setOnRefreshListener { mPresenter.refresh(type, PAGE_SIZE) }

        mPresenter.refresh(type, PAGE_SIZE)
    }

    override fun injectComponent() {
        DaggerAndroidComponent.builder()
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

        if (adapter == null) {
            adapter = AndroidAdapter(activity, dataList)
            loadMoreWrapper = LoadMoreWrapper(adapter, DefaultLoadMoreFooter(activity))
            loadMoreWrapper!!.setOnLoadMoreListener { mPresenter.loadMore(type) }
            recyclerView.adapter = loadMoreWrapper
        }

        adapter!!.notifyDataSetChanged()
    }

    override fun onLoadMore(data: List<GankEntity>?) {
        if (data != null)
            dataList.addAll(data)
        loadMoreWrapper?.loadMoreComplete()
    }

    override fun onNoMore() {
        loadMoreWrapper?.setNoMore()
    }

    class AndroidAdapter(context: Context?, datas: List<GankEntity>?)
        : RecyclerAdapter<GankEntity>(context, R.layout.item_android_ios, datas) {

        override fun bindData(holder: ViewHolder?, t: GankEntity?, position: Int) {
            holder?.setText(R.id.tvDesc, t?.desc)
        }
    }
}

package com.yu.gank4k2.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.yu.gank4k2.App
import com.yu.gank4k2.R
import com.yu.gank4k2.base.BaseMvpFragment
import com.yu.gank4k2.base.adapter.common.ViewHolder
import com.yu.gank4k2.base.adapter.common.loadmore.DefaultLoadMoreFooter
import com.yu.gank4k2.base.adapter.recycler.RecyclerAdapter
import com.yu.gank4k2.base.adapter.recycler.wrapper.LoadMoreWrapper
import com.yu.gank4k2.util.CategoryType
import kotlinx.android.synthetic.main.fragment_common_list.*
import me.yu.drxx.entity.GankEntity
import me.yu.drxx.mvp.ListContract
import me.yu.drxx.mvp.presenter.ListPresenter
import java.util.*

/**
 * Created by yu on 2017/3/7.
 */
class CategoryListFragment : BaseMvpFragment<ListPresenter>(), ListContract.View {

    private var adapter: RecyclerAdapter<GankEntity>? = null
    private var loadMoreWrapper: LoadMoreWrapper? = null
    private var dataList: ArrayList<GankEntity> = ArrayList()

    private val type: String by lazy {
        val categoryType = arguments.getSerializable(KEY) as CategoryType
        categoryType.getTypeString()
    }

    companion object {
        const val KEY = "type"
        const val PAGE_SIZE = 10

        fun newInstance(type: CategoryType): CategoryListFragment {
            val fragment = CategoryListFragment()
            val bundle = Bundle()
            bundle.putSerializable(KEY, type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override val layoutId: Int = R.layout.fragment_common_list

    override fun afterInitView() {
        refreshLayout.setOnRefreshListener { mPresenter.refresh(type, PAGE_SIZE) }

        if (type == CategoryType.GIRLS.getTypeString()) {
            adapter = GirlsAdapter(activity, dataList, this)
        } else {
            adapter = AndroidAndIosAdapter(activity, dataList)
        }
        recyclerView.layoutManager = LinearLayoutManager(activity)

        loadMoreWrapper = LoadMoreWrapper(adapter, DefaultLoadMoreFooter(activity))
        loadMoreWrapper?.setOnLoadMoreListener { mPresenter.loadMore(type) }
        recyclerView.adapter = loadMoreWrapper

        refreshLayout.isRefreshing = true
        mPresenter.refresh(type, PAGE_SIZE)
    }

    override fun injectComponent() {
        App.apiComponent?.inject(this)
        mPresenter.setView(this)
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
        refreshLayout.isRefreshing = false
        loadMoreWrapper?.loadMoreComplete()
    }
}

class AndroidAndIosAdapter(context: Context?, datas: List<GankEntity>?)
    : RecyclerAdapter<GankEntity>(context, R.layout.item_android_ios, datas) {

    override fun bindData(holder: ViewHolder?, entity: GankEntity?, position: Int) {
        holder?.setText(R.id.tvDesc, entity?.desc)
                ?.setText(R.id.tvAuthor, entity?.who)
                ?.setText(R.id.tvDate, entity?.publishedAt)
    }
}

class GirlsAdapter(context: Context?, datas: List<GankEntity>?, val fragment: Fragment)
    : RecyclerAdapter<GankEntity>(context, R.layout.item_girls, datas) {

    override fun bindData(holder: ViewHolder?, entity: GankEntity?, position: Int) {
        Glide.with(fragment).load(entity?.url).centerCrop().into(holder?.getView(R.id.ivImage))
    }
}

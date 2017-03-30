package com.yu.gank4k2.ui.fragment

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.yu.gank4k2.App
import com.yu.gank4k2.R
import com.yu.gank4k2.adapter.common.ViewHolder
import com.yu.gank4k2.adapter.recycler.MultiTypeRecyclerAdapter
import com.yu.gank4k2.adapter.recycler.RecyclerAdapter
import com.yu.gank4k2.adapter.recycler.wrapper.HeaderAndFooterWrapper
import com.yu.gank4k2.base.BaseMvpFragment
import com.yu.gank4k2.mvp.presenter.RandomPresenter
import com.yu.gank4k2.util.CategoryType
import kotlinx.android.synthetic.main.fragment_common_list.*
import kotlinx.android.synthetic.main.layout_random_banner.*
import me.yu.drxx.entity.GankEntity
import me.yu.drxx.mvp.RandomContract
import java.util.*

/**
 * Created by yu on 2017/3/7.
 */
class RandomFragment : BaseMvpFragment<RandomPresenter>(), RandomContract.View {

    private var adapter: MultiTypeRecyclerAdapter<GankEntity>? = null
    private var hafWrapper: HeaderAndFooterWrapper? = null
    private var dataList: ArrayList<GankEntity> = ArrayList()

    override fun injectComponent() {
        App.apiComponent?.inject(this)
        mPresenter.setView(this)
    }

    override val layoutId: Int
        get() = R.layout.fragment_common_list

    override fun afterInitView() {
        initRecyclerView()

        refreshLayout.setOnRefreshListener { mPresenter.refresh() }
        refreshLayout.isRefreshing = true
        mPresenter.refresh()
    }

    private fun initRecyclerView() {
        adapter = RandomAdapter(activity as Context, dataList)
        hafWrapper = HeaderAndFooterWrapper(adapter)
        val bannerView = LayoutInflater.from(activity).inflate(R.layout.layout_random_banner, null)
        bannerView.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, 360)
        hafWrapper?.addHeaderView(bannerView)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = hafWrapper
    }

    override fun onRefresh(data: Map<String, List<GankEntity>>) {
        viewPager.adapter = BannerAdapter(activity, data.get(CategoryType.GIRLS) as List<GankEntity>)

        dataList.clear()
        dataList.addAll(data.get(CategoryType.ANDROID) as List<GankEntity>)
        dataList.addAll(data.get(CategoryType.IOS) as List<GankEntity>)
        adapter?.notifyDataSetChanged()
    }

    override fun hideLoading() {
        refreshLayout.isRefreshing = false
    }
}

class BannerAdapter(private val context: Context, private val data: List<GankEntity>) : PagerAdapter() {

    override fun isViewFromObject(view: View?, obj: Any?) = view == obj

    override fun getCount(): Int = data.size

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val imageView = ImageView(context)
        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        imageView.layoutParams = layoutParams
        Glide.with(context).load(data.get(position).url).centerCrop().into(imageView)
        container?.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup?, position: Int, obj: Any?) {
        container?.removeView(obj as View?)
    }
}

class RandomAdapter(context: Context?, datas: List<GankEntity>?)
    : RecyclerAdapter<GankEntity>(context, R.layout.item_android_ios, datas) {

    override fun bindData(holder: ViewHolder?, entity: GankEntity?, position: Int) {
        holder?.setText(R.id.tvDesc, entity?.desc)
                ?.setText(R.id.tvAuthor, entity?.who)
                ?.setText(R.id.tvDate, entity?.publishedAt)
    }
}

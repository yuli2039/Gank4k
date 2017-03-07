package me.yu.drxx.ui

import com.yu.gank4k2.base.BaseActivity
import me.yu.drxx.mvp.presenter.EmptyPresenter

/**
 * 没有数据逻辑的简单页面，只关乎UI的一些页面，直接继承这个activity
 * @author yu
 * *         Create on 2016/12/27.
 */
abstract class BaseEmptyActivity : BaseActivity<EmptyPresenter>() {

    override fun injectComponent() {

    }

}

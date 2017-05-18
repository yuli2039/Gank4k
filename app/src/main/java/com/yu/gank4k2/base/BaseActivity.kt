package com.yu.gank4k2.base

import android.os.Bundle
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity


/**
 * @author yu
 */
abstract class BaseActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        injectComponent()
        afterInitView()
    }

    protected abstract val layoutId: Int

    protected abstract fun afterInitView()

    open fun injectComponent() {

    }

}

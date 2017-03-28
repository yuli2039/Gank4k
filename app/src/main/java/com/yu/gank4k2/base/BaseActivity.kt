package com.yu.gank4k2.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity


/**
 * @author yu
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        injectComponent()
        afterInitView()
    }

    protected abstract val layoutId: Int

    protected abstract fun afterInitView()

    protected abstract fun injectComponent()

}

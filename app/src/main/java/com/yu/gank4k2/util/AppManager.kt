package com.yu.gank4k2.util

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.lang.IllegalStateException
import java.util.*

/**
 * 需要在application里初始化，提供一些api用于app状态判断和activity栈管理等
 * @author yu
 * *         Create on 16/5/7.
 */
class AppManager private constructor() {

    private var isInitialized = false
    private var mActiveCount = 0// 当前活动的activity数
    private var mCurrentActivity: Activity? = null

    companion object {
        fun getInstance(): AppManager = InstanceHolder.INSTANCE
    }

    private object InstanceHolder {
        val INSTANCE = AppManager()
    }

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                ActivityStack.add(activity)
            }

            override fun onActivityStarted(activity: Activity?) {
                mActiveCount++
            }

            override fun onActivityResumed(activity: Activity?) {
                mCurrentActivity = activity
            }

            override fun onActivityPaused(activity: Activity?) {
                mCurrentActivity = null
            }

            override fun onActivityStopped(activity: Activity?) {
                mActiveCount--
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}

            override fun onActivityDestroyed(activity: Activity?) {
                ActivityStack.remove(activity)
            }
        })
        isInitialized = true
    }

    /**
     * @return 当前应用是否在前台
     */
    val isForground: Boolean
        get() {
            checkInit()
            return mActiveCount > 0
        }

    val currentActicity: Activity?
        get() {
            checkInit()
            return if (mCurrentActivity == null) ActivityStack.getTopActivity() else mCurrentActivity as Activity
        }

    fun isExists(clazz: Class<out Activity>): Boolean? {
        checkInit()
        return ActivityStack.isExists(clazz)
    }

    fun finishExcept(clazz: Class<out Activity>) {
        checkInit()
        ActivityStack.finishExcept(clazz)
    }

    fun exitApp() {
        checkInit()
        ActivityStack.exitApp()
    }

    private fun checkInit() {
        if (!isInitialized)
            throw IllegalStateException("AppManager must be initialized in application!")
    }

    /**
     * 模拟activity栈，对activity进行管理
     */
    private object ActivityStack {

        private val STACK = LinkedList<Activity>()

        // 入栈
        fun add(aty: Activity?) {
            if (aty != null) {
                synchronized(ActivityStack::class.java) {
                    STACK.addLast(aty)
                }
            }
        }

        // 出栈
        fun remove(aty: Activity?) {
            if (aty != null) {
                synchronized(ActivityStack::class.java) {
                    if (STACK.contains(aty))
                        STACK.remove(aty)
                }
            }
        }

        fun getTopActivity(): Activity? {
            return try {
                STACK.last
            } catch (e: NoSuchElementException) {
                null
            }
        }

        fun isExists(clazz: Class<out Activity>): Boolean {
            for (aty in STACK) {
                if (aty.javaClass.simpleName == clazz.simpleName)
                    return true
            }
            return false
        }

        fun exitApp() {
            synchronized(ActivityStack::class.java) {
                val copy = LinkedList(STACK)
                for (aty in copy) {
                    aty.finish()
                }
                copy.clear()

                android.os.Process.killProcess(android.os.Process.myPid())
            }
        }

        fun finishExcept(clazz: Class<out Activity>) {
            synchronized(ActivityStack::class.java) {
                val copy = LinkedList(STACK)
                for (aty in copy) {
                    if (aty.javaClass != clazz) aty.finish()
                }
                copy.clear()
            }
        }
    }

}

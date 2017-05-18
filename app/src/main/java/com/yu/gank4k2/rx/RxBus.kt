package com.yu.gank4k2.rx

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by yu on 2017/5/3.
 */
class RxBus private constructor() {

    private val mBus: FlowableProcessor<Any>
    private val mStickyEventMap: MutableMap<Class<*>, Any>

    init {
        mBus = PublishProcessor.create<Any>().toSerialized()
        mStickyEventMap = HashMap<Class<*>, Any>()
    }

    fun post(obj: Any) {
        mBus.onNext(obj)
    }

    fun <T> toFlowable(event: Class<T>): Flowable<T> {
        return mBus.ofType(event).subscribeOn(Schedulers.io())
    }

    /**
     * 发送一个新Sticky事件
     */
    fun postSticky(event: Any) {
        synchronized(mStickyEventMap) {
            mStickyEventMap.put(event.javaClass, event)
        }
        post(event)
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    fun <T> toFlowableSticky(eventType: Class<T>): Flowable<T> {
        synchronized(mStickyEventMap) {
            val observable = mBus.ofType(eventType)
            val event = mStickyEventMap[eventType]
            if (event != null) {
                return Flowable.merge(observable, Flowable.create({ e -> e.onNext(eventType.cast(event)) }, BackpressureStrategy.LATEST))
            } else {
                return observable
            }
        }
    }

    /**
     * 根据eventType获取Sticky事件
     */
    fun <T> getStickyEvent(eventType: Class<T>): T {
        synchronized(mStickyEventMap) {
            return eventType.cast(mStickyEventMap[eventType])
        }
    }

    /**
     * 移除指定eventType的Sticky事件
     */
    fun <T> removeStickyEvent(eventType: Class<T>): T {
        synchronized(mStickyEventMap) {
            return eventType.cast(mStickyEventMap.remove(eventType))
        }
    }

    /**
     * 移除所有的Sticky事件
     */
    fun removeAllStickyEvents() {
        synchronized(mStickyEventMap) {
            mStickyEventMap.clear()
        }
    }

    private object InstanceHolder {
        val INSTANCE = RxBus()
    }

    companion object {
        val default: RxBus
            get() = InstanceHolder.INSTANCE
    }
}

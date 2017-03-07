package me.yu.drxx.mvp

import com.yu.gank4k2.base.BaseModel
import com.yu.gank4k2.base.BaseView

/**
 * Created by yu on 2016/10/25.
 */
interface EmptyContract {
    interface View : BaseView

    interface Presenter

    interface Model : BaseModel
}

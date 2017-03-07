package me.yu.drxx.mvp.presenter


import com.yu.gank4k2.base.BasePresenter
import me.yu.drxx.mvp.EmptyContract
import me.yu.drxx.mvp.repository.EmptyModel
import javax.inject.Inject

/**
 * Created by yu on 2016/10/25.
 */
class EmptyPresenter
@Inject constructor(view: EmptyContract.View, model: EmptyModel)
    : BasePresenter<EmptyContract.View, EmptyModel>(view, model), EmptyContract.Presenter
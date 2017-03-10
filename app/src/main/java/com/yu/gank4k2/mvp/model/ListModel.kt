package me.yu.drxx.mvp.repository

import me.yu.drxx.entity.GankEntity
import me.yu.drxx.mvp.ListContract
import javax.inject.Inject

/**
 * Created by yu on 2016/12/27.
 */
class ListModel
@Inject constructor() : ListContract.Model {
    override fun loadData(type: String, pageNum: Int): List<GankEntity>? {
        return null
    }
}
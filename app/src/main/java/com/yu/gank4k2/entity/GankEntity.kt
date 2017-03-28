package me.yu.drxx.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by yu on 2016/12/9.
 */
data class GankEntity(
        @SerializedName("_id")
        var id: String?,
        var createdAt: String?,
        var desc: String?,
        var images: List<String>?,
        var publishedAt: String?,
        var source: String?,
        var type: String?,
        var url: String?,
        var used: Boolean?,
        var who: String?
        )



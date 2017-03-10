package com.yu.gank4k2.entity

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

/**
 * Realm的实体示例
 * RealmEntity类和变量都必须以open修饰
 * Created by yu on 2017/3/10.
 */
open class RealmEntity(

        @PrimaryKey open var id: Long = 0,

        @Required // 必填项
        open var name: String = "",

        open var age: Int = 0,

        @Ignore open var tempReference: Int = 0

) : RealmObject() {

}
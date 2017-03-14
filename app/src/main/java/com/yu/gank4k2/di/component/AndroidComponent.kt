package me.yu.drxx.di.component


import com.yu.gank4k2.di.ActivityScope
import com.yu.gank4k2.di.moudle.CategoryListModule
import com.yu.gank4k2.ui.fragment.AndroidFragment
import dagger.Component

/**
 *
 */
@ActivityScope
@Component(dependencies = arrayOf(ApiComponent::class), modules = arrayOf(CategoryListModule::class))
interface AndroidComponent {
    fun inject(aty: AndroidFragment)
}

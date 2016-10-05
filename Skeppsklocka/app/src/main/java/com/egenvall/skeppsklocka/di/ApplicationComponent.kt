package com.egenvall.skeppsklocka.di

import com.egenvall.skeppsklocka.App
import com.egenvall.skeppsklocka.ui.ChildController
import com.egenvall.skeppsklocka.ui.MainActivity
import com.egenvall.skeppsklocka.ui.MainController
import com.egenvall.skeppsklocka.ui.PushedController
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, NetworkModule::class))
interface ApplicationComponent {
    fun inject(app : App)
    fun inject(mainActivity: MainActivity)
    fun inject(childController: ChildController)
}
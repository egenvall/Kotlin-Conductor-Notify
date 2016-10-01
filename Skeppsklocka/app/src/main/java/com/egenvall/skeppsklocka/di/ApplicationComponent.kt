package com.egenvall.skeppsklocka.di

import com.egenvall.skeppsklocka.App
import com.egenvall.skeppsklocka.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun inject(app : App)
    fun inject(mainActivity: MainActivity)
}
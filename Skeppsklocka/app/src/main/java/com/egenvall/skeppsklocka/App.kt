package com.egenvall.skeppsklocka
import android.app.Application
import com.egenvall.skeppsklocka.di.ApplicationComponent
import com.egenvall.skeppsklocka.di.ApplicationModule
import com.egenvall.skeppsklocka.di.DaggerApplicationComponent


class App : Application() {
    companion object {
        //platformStatic allow access it from java code
        @JvmStatic lateinit var graph: ApplicationComponent
    }
    override fun onCreate() {
        super.onCreate()
        graph = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
        graph.inject(this)
    }
}
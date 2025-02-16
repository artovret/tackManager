package com.titixoid.taskmanager

import android.app.Application
import com.titixoid.taskmanager.ui.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class TaskManagerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TaskManagerApp)
            modules(appModule)
        }
    }
}
package com.titixoid.taskmanager

import android.app.Application
import com.google.firebase.FirebaseApp
import com.titixoid.taskmanager.ui.di.appModule
import com.titixoid.taskmanager.ui.di.dataStoreModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class TaskManagerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@TaskManagerApp)
            modules(appModule, dataStoreModule)
        }
    }
}
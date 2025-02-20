package com.titixoid.taskmanager.ui.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.titixoid.data.repository.AuthTokenDataStore
import com.titixoid.data.repository.FirebaseTaskRepository
import com.titixoid.data.repository.FirebaseUserRepository
import com.titixoid.domain.repository.TaskRepository
import com.titixoid.domain.repository.UserRepository
import com.titixoid.domain.usecases.CheckAuthStatusUseCase
import com.titixoid.domain.usecases.GetAllUsersUseCase
import com.titixoid.domain.usecases.GetTasksForWorkerUseCase
import com.titixoid.domain.usecases.SignInUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private val Context.authDataStore by preferencesDataStore(name = "auth_preferences")

val dataStoreModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single<UserRepository> { FirebaseUserRepository(get(), get(), get()) }
    single<TaskRepository> { FirebaseTaskRepository(get()) }
    single { androidContext().authDataStore }
    single { AuthTokenDataStore(get()) }


    factory { SignInUseCase(get()) }
    factory { CheckAuthStatusUseCase(get()) }
    factory { GetAllUsersUseCase(get()) }
    factory { GetTasksForWorkerUseCase(get()) }

}
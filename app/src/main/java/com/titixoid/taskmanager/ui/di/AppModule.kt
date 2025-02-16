package com.titixoid.taskmanager.ui.di

import com.google.firebase.auth.FirebaseAuth
import com.titixoid.data.repository.FirebaseAuthRepository
import com.titixoid.domain.repository.AuthRepository
import com.titixoid.domain.usecases.SignInUseCase
import com.titixoid.taskmanager.ui.admin.workers.AdminWorkerListViewModel
import com.titixoid.taskmanager.ui.login.SignInViewModel
import com.titixoid.taskmanager.ui.worker.tasks.WorkerTaskListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single<AuthRepository> { FirebaseAuthRepository(get()) }
    factory { SignInUseCase(get()) }


    viewModel { SignInViewModel(get()) }
    viewModel { WorkerTaskListViewModel() }
    viewModel { AdminWorkerListViewModel() }
}
package com.titixoid.taskmanager.ui.di

import com.titixoid.taskmanager.ui.admin.task_create.CreateTaskViewModel
import com.titixoid.taskmanager.ui.admin.tasks.AdminTaskListViewModel
import com.titixoid.taskmanager.ui.admin.workers.AdminWorkerListViewModel
import com.titixoid.taskmanager.ui.login.SignInViewModel
import com.titixoid.taskmanager.ui.start.StartScreenViewModel
import com.titixoid.taskmanager.ui.worker.tasks.WorkerTaskListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { SignInViewModel(get()) }
    viewModel { StartScreenViewModel(get()) }
    viewModel { WorkerTaskListViewModel() }
    viewModel { AdminWorkerListViewModel(get(), get()) }
    viewModel { (workerId: String) ->
        AdminTaskListViewModel(
            workerId = workerId,
            get()
        )
    }
    viewModel { (workerId: String) ->
        CreateTaskViewModel(
            workerId = workerId,
            get()
        )
    }
}
package com.titixoid.data.models

import com.titixoid.domain.models.Task


data class TaskDao(
    var title: String = "",
    var description: String = "",
    var status: String = "",
    var completed: Boolean = false,
    var workerId: String = ""
)

fun TaskDao.toDomainTask(id: String): Task {
    return Task(
        id = id,
        title = this.title,
        description = this.description,
        status = this.status,
        completed = this.completed,
        workerId = this.workerId
    )
}
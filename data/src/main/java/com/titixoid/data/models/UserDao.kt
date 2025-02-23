package com.titixoid.data.models

import com.titixoid.domain.models.User

data class UserDao(
    var email: String = "",
    var login: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var password: String = "",
    var role: String = "",
    var taskCount: Int = 0,

)

fun UserDao.toDomainUser(id: String): User {
    return User(
        id = id,
        firstName = this.firstName,
        lastName = this.lastName,
        role = this.role,
        taskCount = this.taskCount,
    )
}
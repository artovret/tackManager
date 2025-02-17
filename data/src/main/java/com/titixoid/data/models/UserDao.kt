package com.titixoid.data.models

import com.titixoid.domain.models.User

data class UserDao(
    var email: String = "",
    var login: String = "",
    var password: String = "",
    var role: String = ""
)

fun UserDao.toDomainUser(): User {
    return User(
        role = this.role
    )
}
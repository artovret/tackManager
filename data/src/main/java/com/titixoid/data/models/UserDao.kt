package com.titixoid.data.models

import com.titixoid.domain.models.User

data class UserDao(
    var email: String = "",
    var login: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var password: String = "",
    var role: String = ""
)

fun UserDao.toDomainUser(): User {
    return User(
        firstName = this.firstName,
        lastName = this.lastName,
        role = this.role,
    )
}
package com.titixoid.taskmanager.ui.common

enum class TaskFilterEnum(val id: String, val displayName: String) {
    NONE("", "Все"),
    URGENT("urgent", "Срочные"),
    PLANNED("planned", "Плановые"),
    OPTIONAL("optional", "Прочее")
}

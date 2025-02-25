package com.titixoid.taskmanager.ui.widgets

enum class TaskFilter(val id: String, val displayName: String) {
    NONE("", "Все"),
    IN_PROGRESS("in_progress", "В работе"),
    COMPLETED("completed", "Завершённые"),
    PENDING("pending", "Ожидающие")
}
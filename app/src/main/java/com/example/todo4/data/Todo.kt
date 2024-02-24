package com.example.todo4.data

import java.util.Date

data class Todo(
    val id: Int,
    var title: String,
    var detail: String = ""
) {
    val dueDate: Date? = null
}
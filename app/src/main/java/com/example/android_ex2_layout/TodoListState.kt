package com.example.android_ex2_layout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class TodoItem(
    val id: Int,
    val title: String,
    val isCompleted: Boolean
)

class TodoListState {
    val tasks = mutableStateListOf(
        TodoItem(1, "学习 Column 和 Row", isCompleted = true),
        TodoItem(2, "学习状态管理", isCompleted = false),
        TodoItem(3, "完成 Compose 实验", isCompleted = false)
    )

    var inputText by mutableStateOf("")
        private set

    private var nextId = 4

    val completedCount: Int
        get() = tasks.count { it.isCompleted }

    fun updateInput(text: String) {
        inputText = text
    }

    fun addTask() {
        val title = inputText.trim()
        if (title.isEmpty()) return

        tasks.add(
            TodoItem(
                id = nextId++,
                title = title,
                isCompleted = false
            )
        )
        inputText = ""
    }

    fun setCompleted(id: Int, completed: Boolean) {
        val index = tasks.indexOfFirst { it.id == id }
        if (index == -1) return

        tasks[index] = tasks[index].copy(isCompleted = completed)
    }

    fun deleteTask(id: Int) {
        tasks.removeAll { it.id == id }
    }
}

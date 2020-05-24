package com.itis.mytask.repository

import com.itis.mytask.model.TaskDto

object RepositoryEmulator : ArrayList<TaskDto>() {
    init {
        add(TaskDto("To buy", "Milk and eggs"))
        add(TaskDto("Task", "Watch film Titanic"))
        add(TaskDto("Task", "Call John"))
    }
}
package com.example.prodiggy_ad_02.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prodiggy_ad_02.data.repositores.TaskRepository

class TaskViewModelFactory(private val taskRepository: TaskRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST") if (modelClass.isAssignableFrom(TaskViewModels::class.java)) return TaskViewModels(
            taskRepository = taskRepository
        ) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
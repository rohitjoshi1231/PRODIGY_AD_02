package com.example.prodiggy_ad_02.data.viewmodels

import androidx.lifecycle.ViewModel
import com.example.prodiggy_ad_02.data.models.Task
import com.example.prodiggy_ad_02.data.repositores.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskViewModels(private val taskRepository: TaskRepository) : ViewModel() {
    val allTasks = taskRepository.getAllTasks()
    fun getTasksByUserId(userId: Int): Flow<List<Task>> =
        taskRepository.getTasksByUserId(userId)

    suspend fun deleteAllTasks() {
        taskRepository.deleteAllTasks()
    }

    suspend fun getTask(id: Int) {
        taskRepository.getTask(id)
    }

    suspend fun insert(task: Task) {
        taskRepository.insert(task)
    }

    suspend fun update(task: Task) {
        taskRepository.update(task)
    }

    suspend fun delete(task: Task) {
        taskRepository.delete(task)
    }
}



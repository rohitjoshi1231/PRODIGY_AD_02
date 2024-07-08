package com.example.prodiggy_ad_02.data.repositores

import com.example.prodiggy_ad_02.data.dao.TaskDao
import com.example.prodiggy_ad_02.data.models.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

    fun getTasksByUserId(userId: Int): Flow<List<Task>> = taskDao.getTasksByUserId(userId)

    suspend fun getTask(id: Int) {
        taskDao.getTaskById(id)
    }

    suspend fun insert(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun update(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun delete(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun deleteAllTasks() {
        taskDao.deleteAllTasks()
        taskDao.resetPrimaryKey()
    }
}
package com.example.prodiggy_ad_02.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var userId: Int = 0,
    var title: String? = null
) {
    constructor() : this(0, 0, "")
}


package com.vondi.dbcards.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val time: Int,
    val tags: List<String>,
    val amount: Int,
)


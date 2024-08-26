package com.vondi.dbcards.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.vondi.dbcards.data.models.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    // Относительно новая аннотация, можно использовать как для update, так и для insert
    @Upsert
    suspend fun upsertItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)

    @Query("SELECT * FROM item")
    fun getItems(): Flow<List<Item>>

}
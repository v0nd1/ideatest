package com.vondi.dbcards.data.network

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vondi.dbcards.data.dao.ItemDao
import com.vondi.dbcards.data.models.Item
import com.vondi.dbcards.data.util.Converter

@Database(
    entities = [Item::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class ProductsDb : RoomDatabase() {
    abstract val dao : ItemDao

    companion object {
        @Volatile
        private var INSTANCE: ProductsDb? = null

        fun getDatabase(context: Context): ProductsDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductsDb::class.java,
                    "product_db.db"
                )
                    .createFromAsset("data.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
package com.greener.data.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.runBlocking
import java.io.InputStream
import java.util.concurrent.Executors

@Database(
    entities = [PlantAccessoryEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class PlantAccessoryDatabase : RoomDatabase() {
    abstract fun plantAccessoryDao(): PlantAccessoryDao

    companion object {
        fun getInstance(context: Context): PlantAccessoryDatabase =
            Room.databaseBuilder(context, PlantAccessoryDatabase::class.java, "PlantAccessory.db")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        Executors.newSingleThreadExecutor().execute {
                            runBlocking {
                                val manager = context.resources.assets
                                val inputStream: InputStream = manager.open("PlantAccessoryDB.tsv")

                                inputStream.bufferedReader().readLines().forEach {
                                    var token = it.split("\t")
                                    getInstance(context).plantAccessoryDao().addPlantAccessory(
                                        PlantAccessoryEntity(
                                            id = token[0].toInt(),
                                            itemType = token[1],
                                            itemName = token[2],
                                            limitLevel = token[3].toInt(),
                                        ),
                                    )
                                }
                            }
                        }
                    }
                })
                .build()
    }
}

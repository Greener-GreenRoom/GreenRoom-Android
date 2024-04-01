package com.greener.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.runBlocking
import java.io.InputStream
import java.util.concurrent.Executors

@Database(
    entities = [PlantShapeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PlantShapeDatabase : RoomDatabase() {
    abstract fun plantShapeDao(): PlantShapeDao

    companion object {
        fun getInstance(context: Context): PlantShapeDatabase =
            Room.databaseBuilder(context, PlantShapeDatabase::class.java, "PlantShape.db")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        Executors.newSingleThreadExecutor().execute {
                            runBlocking {
                                val manager = context.resources.assets
                                val inputStream: InputStream = manager.open("PlantShapeDB.tsv")

                                inputStream.bufferedReader().readLines().forEach {
                                    val token = it.split("\t")
                                    getInstance(context).plantShapeDao().addPlantShape(
                                        PlantShapeEntity(
                                            id = token[0].toInt(),
                                            plantType = token[1],
                                            plantName = token[2]
                                        )
                                    )
                                }
                            }
                        }
                    }
                })
                .build()
    }
}

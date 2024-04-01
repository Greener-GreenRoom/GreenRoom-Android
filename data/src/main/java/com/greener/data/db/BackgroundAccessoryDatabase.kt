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
    entities = [BackgroundAccessoryEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class BackgroundAccessoryDatabase : RoomDatabase() {
    abstract fun backgroundAccessoryDao(): BackgroundAccessoryDao

    companion object {
        fun getInstance(context: Context): BackgroundAccessoryDatabase =
            Room.databaseBuilder(context, BackgroundAccessoryDatabase::class.java, "BackgroundAccessory.db")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        Executors.newSingleThreadExecutor().execute {
                            runBlocking {
                                val manager = context.resources.assets
                                val inputStream: InputStream = manager.open("BackgroundAccessoryDB.tsv")

                                inputStream.bufferedReader().readLines().forEach {
                                    val token = it.split("\t")
                                    getInstance(context).backgroundAccessoryDao().addBackgroundAccessory(
                                        BackgroundAccessoryEntity(
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

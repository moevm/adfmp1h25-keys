package ru.etu.duplikeytor.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.etu.duplikeytor.domain.dao.KeyDao
import ru.etu.duplikeytor.domain.models.Key

@Database(entities = [Key::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun keyDao(): KeyDao
}
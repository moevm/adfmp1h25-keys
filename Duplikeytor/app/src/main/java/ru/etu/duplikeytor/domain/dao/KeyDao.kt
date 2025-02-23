package ru.etu.duplikeytor.domain.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ru.etu.duplikeytor.domain.models.Key

@Dao
interface KeyDao {
    @Query("SELECT * FROM keys;")
    suspend fun getAll(): List<Key>

    @Query("SELECT * FROM keys WHERE key_id = :id;")
    suspend fun getById(id: Long): Key

    @Upsert
    suspend fun insert(key: Key)

    @Query("DELETE FROM keys WHERE key_id = :id;")
    suspend fun delete(id: Long)
}
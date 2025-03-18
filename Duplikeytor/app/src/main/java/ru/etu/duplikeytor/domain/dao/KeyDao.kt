package ru.etu.duplikeytor.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.etu.duplikeytor.domain.models.Key

@Dao
interface KeyDao {
    @Query("SELECT * FROM keys;")
    suspend fun getAll(): List<Key>

    @Query("SELECT * FROM keys WHERE key_id = :id;")
    suspend fun getById(id: Long): Key?

    @Insert
    suspend fun insert(key: Key): Long

    @Update
    suspend fun update(key: Key)

    suspend fun upsert(key: Key): Long {
        val existingKey = getById(key.id)
        return if (existingKey == null) {
            insert(key)
        } else {
            update(key)
            return key.id
        }
    }

    @Query("DELETE FROM keys WHERE key_id = :id;")
    suspend fun delete(id: Long)
}
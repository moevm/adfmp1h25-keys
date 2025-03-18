package ru.etu.duplikeytor.domain.repository

import ru.etu.duplikeytor.domain.dao.KeyDao
import ru.etu.duplikeytor.domain.models.Key
import javax.inject.Inject

class KeyRepository @Inject constructor(private val keyDao: KeyDao) {
    suspend fun getKeys(): List<Key> {
        return keyDao.getAll()
    }

    suspend fun getKey(id: Long): Key? {
        return keyDao.getById(id)
    }

    suspend fun upsert(key: Key): Long {
        return keyDao.upsert(key)
    }

    suspend fun deleteKey(id: Long) {
        keyDao.delete(id)
    }
}
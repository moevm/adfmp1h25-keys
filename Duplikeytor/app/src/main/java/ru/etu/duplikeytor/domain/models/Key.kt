package ru.etu.duplikeytor.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.etu.duplikeytor.domain.converters.Converters
import java.util.Date

@Entity(tableName = "keys")
@TypeConverters(Converters::class)
data class Key(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="key_id")
    var id: Long = 0,

    var name: String,

    @ColumnInfo(name="photo_uri")
    var photoUri: String? = null,

    var type: String,

    var pins: List<Int>,

    @ColumnInfo(name="created_at")
    var createdAt: Long = System.currentTimeMillis(),
)
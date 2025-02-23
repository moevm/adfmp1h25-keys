package ru.etu.duplikeytor.domain.converters

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromPinsToByteArray(pins: List<Int>?): String? {
        return gson.toJson(pins)
    }

    @TypeConverter
    fun fromJsonToPins(data: String?): List<Int> {
        return gson.fromJson(data, Array<Int>::class.java).toList()
    }
}
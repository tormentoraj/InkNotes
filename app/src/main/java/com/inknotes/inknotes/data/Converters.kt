package com.inknotes.inknotes.data

import androidx.room.TypeConverter
import com.inknotes.inknotes.data.model.Stroke
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Date

class Converters {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromStrokeList(value: String?): List<Stroke>? {
        return value?.let {
            try {
                json.decodeFromString<List<Stroke>>(it)
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    @TypeConverter
    fun strokeListToString(strokes: List<Stroke>?): String? {
        return strokes?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun fromIntList(value: String?): List<Int>? {
        return value?.let {
            try {
                json.decodeFromString<List<Int>>(it)
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    @TypeConverter
    fun intListToString(list: List<Int>?): String? {
        return list?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun fromFloatList(value: String?): List<Float>? {
        return value?.let {
            try {
                json.decodeFromString<List<Float>>(it)
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    @TypeConverter
    fun floatListToString(list: List<Float>?): String? {
        return list?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun fromLongList(value: String?): List<Long>? {
        return value?.let {
            try {
                json.decodeFromString<List<Long>>(it)
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    @TypeConverter
    fun longListToString(list: List<Long>?): String? {
        return list?.let { json.encodeToString(it) }
    }
}
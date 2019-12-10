package com.example.anihub.ui

import android.util.Log
import androidx.room.TypeConverter
import com.example.anihub.ui.anime.AnimeModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun toPageInfo(value: String): AnimeModel.PageInfo {
        val type = object: TypeToken<AnimeModel.PageInfo>() {}.type
        Log.d("Chris", value)
        return Gson().fromJson<AnimeModel.PageInfo>(value, type)
    }

    @TypeConverter
    fun fromPageInfo(value: AnimeModel.PageInfo): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toMedia(value: String): AnimeModel.Media {
        val type = object: TypeToken<AnimeModel.Media>() {}.type
        return Gson().fromJson<AnimeModel.Media>(value, type)
    }

    @TypeConverter
    fun fromMedia(value: AnimeModel.Media): String {
        return Gson().toJson(value)
    }
}
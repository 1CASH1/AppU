package com.hugo.moviesofmine.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MovieEntity")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var title: String,
    var original_language: String,
    var popularity: String,
    var vote_average: String,
    var vote_count: String,
    var image: String)


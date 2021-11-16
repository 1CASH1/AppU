package com.hugo.moviesofmine.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieEntity")
    fun getAll(): MutableList<MovieEntity>

    @Insert
    suspend fun insert(movieEntity:MovieEntity): Long

    @Query("DELETE FROM MovieEntity")
    suspend fun delete()


}
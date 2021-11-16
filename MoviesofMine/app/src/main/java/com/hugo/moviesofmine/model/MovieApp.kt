package com.hugo.moviesofmine.model

import android.app.Application
import androidx.room.Room

class MovieApp: Application() {
    companion object{
        lateinit var database: MovieDB
    }


    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this,
            MovieDB::class.java,
            "movie")
            .build()
    }
}
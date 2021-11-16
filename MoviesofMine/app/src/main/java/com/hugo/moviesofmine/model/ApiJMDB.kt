package com.hugo.moviesofmine.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiJMDB {
    @GET
    suspend fun getMovies(@Url url:String): Response<JsonTMDB>
}
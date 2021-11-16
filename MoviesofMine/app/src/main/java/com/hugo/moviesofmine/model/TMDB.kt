package com.hugo.moviesofmine.model

import android.content.Context
import android.util.Log
import com.hugo.moviesofmine.adapter.Movie
import com.hugo.moviesofmine.auxiliar.Internet
import com.hugo.moviesofmine.interfaces.IContratoMovies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.random.Random

class TMDB: IContratoMovies.Model {
    @Singleton
    val mlMovies = mutableListOf<Movie>()
    override fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    override fun downloadMovies(context: Context?, sl: (MutableList<Movie>) -> Unit, eL: (String) -> Unit) {

        CoroutineScope(Dispatchers.IO).launch {
            Log.e("TAG","=====================================")
            @Singleton
            val internet = Internet(context)
            Log.e("TAG","++++++++${internet.validate()}++++++++")

            if (internet.validate() == true) {
                Log.e("TAG","**********************")
                val call = getRetrofit().create(ApiJMDB::class.java)
                    .getMovies("movie/popular?api_key=edcd960b3ba5322cc0702c0c02f8ccba")
                val puppies = call.body()
                MovieApp.database.MovieDao().delete()
                for (item in puppies!!.results) {
                    MovieApp.database.MovieDao().insert(MovieEntity(
                        Random.nextLong(),
                        item.title,
                        item.original_language,
                        item.popularity,
                        item.vote_average.toString(),
                        item.vote_count.toString(),
                        "https://image.tmdb.org/t/p/w500/${item.backdrop_path}"
                    ))
                }
                Log.e("TAG","**********************")
            }
            for (item in MovieApp.database.MovieDao().getAll()){
                Log.e("TAG","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
                mlMovies.add(Movie(item.title,item.original_language,item.popularity,item.vote_average,item.vote_count, "https://image.tmdb.org/t/p/w500/${item.image}"))
                sl(mlMovies)
                Log.e("TAG","¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡")
            }
            Log.e("TAG","=====================================")
        }
    }


}
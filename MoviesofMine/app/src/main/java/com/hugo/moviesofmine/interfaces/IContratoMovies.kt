package com.hugo.moviesofmine.interfaces

import android.content.Context
import com.hugo.moviesofmine.adapter.Movie
import retrofit2.Retrofit

interface IContratoMovies {
    interface View {
        //fun getRetrofit(): Retrofit
        fun showMovies(moviesML: MutableList<Movie>)
        fun showError(msg: String)
    }

    interface Presenter {
        fun getMovies()
    }

    interface Model {
        fun getRetrofit(): Retrofit
        fun downloadMovies(
            context: Context?,
            ml: (MutableList<Movie>) -> Unit,
            eL: (String) -> Unit
        )
    }
}
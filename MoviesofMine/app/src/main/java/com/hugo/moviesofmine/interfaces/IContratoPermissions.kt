package com.hugo.moviesofmine.interfaces

import com.hugo.moviesofmine.adapter.Movie

interface IContratoPermissions {

    interface ViewPermisos {
        //fun getRetrofit(): Retrofit
        fun showMovies(moviesML: MutableList<Movie>)
        fun showError(msg: String)
    }

    interface Presenter {
        fun getMovies()
    }
}
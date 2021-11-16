package com.hugo.moviesofmine.presenter

import android.content.Context
import com.hugo.moviesofmine.interfaces.IContratoMovies

class MoviesPresenter(private val view: IContratoMovies.View,
                      private val model: IContratoMovies.Model, val context: Context?): IContratoMovies.Presenter {
    override fun getMovies() {
        model.downloadMovies(
            context,
            {ml-> view.showMovies(ml)},
            {eL -> view.showError(eL)}
        )
    }
}
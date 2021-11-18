package com.hugo.moviesofmine.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hugo.moviesofmine.R
import com.hugo.moviesofmine.adapter.AdapterMovies
import com.hugo.moviesofmine.adapter.Movie
import com.hugo.moviesofmine.databinding.FragmentMoviesBinding
import com.hugo.moviesofmine.interfaces.IContratoMovies
import com.hugo.moviesofmine.model.TMDB
import com.hugo.moviesofmine.presenter.MoviesPresenter


class MoviesFragment : Fragment(R.layout.fragment_movies), IContratoMovies.View {
    private lateinit var binding: FragmentMoviesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoviesBinding.bind(view)

        var mp = MoviesPresenter(this, TMDB(), this.context)
        mp.getMovies()
    }

    override fun showMovies(moviesML: MutableList<Movie>) {
        try {
            if (moviesML.size>0) {
                binding.lvMovies.adapter = AdapterMovies(this.context, R.layout.item_movies, moviesML)
            }

        } catch (e: Exception) {
            Log.e("TAG", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
            Log.e("TAG", e.toString())
            Log.e("TAG", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
        }
    }

    override fun showError(msg: String) {
        Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()
    }

    companion object{
        @JvmStatic
        fun newInstance() = MoviesFragment()
    }




}
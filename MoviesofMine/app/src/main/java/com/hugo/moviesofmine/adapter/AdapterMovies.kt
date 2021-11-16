package com.hugo.moviesofmine.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.hugo.moviesofmine.R
import com.squareup.picasso.Picasso

class AdapterMovies(var ctx: Context?, var recurses: Int, var items: List<Movie>): ArrayAdapter<Movie>(ctx!!,recurses,items){
    override fun getCount(): Int {
        return items.count()
    }

    override fun getItem(position: Int): Movie? {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val ly: LayoutInflater = LayoutInflater.from(ctx)
        val view: View = ly.inflate(recurses, null)

        val ivFoto: ImageView = view.findViewById(R.id.ivFoto)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvLanguage: TextView = view.findViewById(R.id.tvLanguage)
        val tvPopularity: TextView = view.findViewById(R.id.tvPopularity)
        val tvAverage: TextView = view.findViewById(R.id.tvAverage)
        val tvCount: TextView = view.findViewById(R.id.tvCount)

        var im: Movie = items[position]

        Picasso.get()
            .load(im.image)
            .into(ivFoto);
        tvTitle.text = im.title
        tvLanguage.text = im.language
        tvPopularity.text = im.popularity
        tvAverage.text = im.average
        tvCount.text = im.count

        return view
    }
}
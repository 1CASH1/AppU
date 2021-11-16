package com.hugo.moviesofmine.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.hugo.moviesofmine.R

class AdapterGaleria(val context: Context?, val urls: List<Imagenes>) : BaseAdapter() {

    private lateinit var layoutInflater: LayoutInflater

    init {
        layoutInflater = (context?.getSystemService(LAYOUT_INFLATER_SERVICE)) as LayoutInflater
    }


    override fun getCount(): Int {
        //return img.size
        return urls.size
    }

    override fun getItem(position: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View =  LayoutInflater.from(parent!!.context).inflate(R.layout.item_image, parent, false)
        var ivGvFotos :ImageView= view.findViewById(R.id.ivGvFotos)
        //var tvGvFotos :TextView= view.findViewById(R.id.tvGvFotos)
        //ivGvFotos.setImageResource(img[position])
        if(urls[position].uri != null) {
            ivGvFotos.setImageURI(urls[position].uri)
        }else if (urls[position].bitmap != null){
            ivGvFotos.setImageBitmap(urls[position].bitmap)

        }
        /*tvGvFotos.text = clipData.getItemAt(position).text*/
        return view
    }

}
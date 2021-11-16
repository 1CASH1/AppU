package com.hugo.moviesofmine.view

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationBarView
import com.hugo.moviesofmine.R
import com.hugo.moviesofmine.databinding.ActivityHomeBinding
import com.hugo.moviesofmine.view.Fragment.GalleryFragment
import com.hugo.moviesofmine.view.Fragment.MapFragment
import com.hugo.moviesofmine.view.Fragment.MoviesFragment
import com.hugo.moviesofmine.view.onboarding.ViewPargerAdapter

class HomeActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private lateinit var binding: ActivityHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigation()

    }

    private fun navigation() {
        val fragmentList = arrayListOf(
            MoviesFragment(),
            MapFragment(),
            GalleryFragment()
        )
        binding.vp2Pager.adapter = ViewPargerAdapter(fragmentList,this)//ViewPargerAdapter(fragmentList,supportFragmentManager)
        binding.bnvHome.setOnItemSelectedListener(this)
        binding.vp2Pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                println(state)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                println(position)
            }


            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(position == 2)
                {
                    //requestPermissions()
                }
                binding.bnvHome.menu.getItem(position).isChecked = true
            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.moviesFragment->{
                binding.vp2Pager.currentItem = 0
                return true
            }
            R.id.mapFragment->{
                binding.vp2Pager.currentItem = 1
                return true
            }
            R.id.galleryFragment->{
                //requestPermissions()
                binding.vp2Pager.currentItem = 2
                return true
            }
        }
        return false
    }


    private fun requestPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){ //Primero se debe averiguar el nivel de API
            when{
                //Se comprueba si los permisos ya están habilitados, de ser así se accesa a la galería
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)  == PackageManager.PERMISSION_GRANTED -> {
                    selectPhotoGallery()
                }
                //En caso contrario se solicitará aceptar los permisos, pero también se comprobará si son aceptados o no
                else ->{
                    requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }else{
            selectPhotoGallery() //Método que seleccionará la imagen de nuestra galería
        }
    }
    //Esta variable solicitará los permisos mediante la librería de Activity importada
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isOk ->
        if (isOk){  //En caso de que fueron aceptados se procede con el método para accesar a la galería
            selectPhotoGallery()
        }else{   //En caso de que fueron aceptados se procede a mostrar un mensaje con un toast
            warningMessage()
        }
    }

    private val starForActivityGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            val location = result.data?.data
            //binding.imageView.setImageURI(location)

        }
    }


    //Método que ingresa a la galería de nuestro dispositivo
    private fun selectPhotoGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        starForActivityGallery.launch(intent)
    }

    private fun warningMessage(){
        Toast.makeText(this,"Es necesario aceptar los permisos", Toast.LENGTH_SHORT).show()
    }
}
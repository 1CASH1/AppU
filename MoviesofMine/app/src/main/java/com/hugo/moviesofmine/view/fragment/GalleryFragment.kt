package com.hugo.moviesofmine.view.fragment

import android.R.attr
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.hugo.moviesofmine.R
import com.hugo.moviesofmine.adapter.AdapterGaleria
import com.hugo.moviesofmine.adapter.Imagenes
import com.hugo.moviesofmine.databinding.FragmentGalleryBinding
import java.io.ByteArrayOutputStream
import java.util.*


class GalleryFragment : Fragment(R.layout.fragment_gallery) {
    private var progress: ProgressDialog? = null
    private lateinit var binding: FragmentGalleryBinding
    private lateinit var adapterGaleria: AdapterGaleria
    private lateinit var urls: MutableList<Imagenes>
    //Imagenes
    var PICK_IMAGE_MULTIPLE = 1
    private val cameraRequest = 1888
    private var storageReference: StorageReference? = null


    private val database = Firebase.database
    private val myRef = database.getReference("user")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGalleryBinding.bind(view)
        urls = mutableListOf()
        adapterGaleria = AdapterGaleria(this.context,urls)
        binding.gvGaleria.adapter = adapterGaleria

        storageReference = FirebaseStorage.getInstance().reference

        binding.btGaleria.setOnClickListener{
            requestPermissionsGaleria()
        }


        binding.btCamera.setOnClickListener{
            requestPermissionsCamara()
        }

        binding.btSubir.setOnClickListener{

            urls
            for (item in urls){
                if(item.uri!= null) {

                    uploadImage(item)
                }
            }
        }

    }


    private fun uploadImage(item: Imagenes): Boolean{
        val FileUri = item.uri
        val Folder: StorageReference =
            FirebaseStorage.getInstance().getReference().child("User")
        val file_name: StorageReference = Folder.child("file" + FileUri!!.lastPathSegment)
        file_name.putFile(FileUri).addOnSuccessListener { taskSnapshot ->
            file_name.getDownloadUrl().addOnSuccessListener { uri ->
                val hashMap =
                    HashMap<String, String>()
                hashMap["link"] = java.lang.String.valueOf(uri)
                myRef.setValue(hashMap)
                Log.d("Mensaje", "Se subió correctamente")
                urls.remove(item)
                adapterGaleria.notifyDataSetChanged()
            }
        }
        return false
    }


    companion object{
        @JvmStatic
        fun newInstance() = GalleryFragment()
    }

    private fun requestPermissionsGaleria(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){ //Primero se debe averiguar el nivel de API
            when{
                //Se comprueba si los permisos ya están habilitados, de ser así se accesa a la galería
                ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)  == PackageManager.PERMISSION_GRANTED -> {
                    selectPhotoGallery()
                }
                //En caso contrario se solicitará aceptar los permisos, pero también se comprobará si son aceptados o no
                else ->{
                    requestPermissionLauncherREAD_EXTERNAL_STORAGE.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }else{
            selectPhotoGallery() //Método que seleccionará la imagen de nuestra galería
        }
    }
    //Esta variable solicitará los permisos mediante la librería de Activity importada
    private val requestPermissionLauncherREAD_EXTERNAL_STORAGE = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isOk ->
        if (isOk){  //En caso de que fueron aceptados se procede con el método para accesar a la galería
            selectPhotoGallery()
        }else{   //En caso de que fueron aceptados se procede a mostrar un mensaje con un toast
            warningMessage()
        }
    }

    //Esta variable solicitará los permisos mediante la librería de Activity importada
    private val requestPermissionLauncherCAMERA = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isOk ->
        if (isOk){  //En caso de que fueron aceptados se procede con el método para accesar a la galería
            selectPhotoCamara()
        }else{   //En caso de que fueron aceptados se procede a mostrar un mensaje con un toast
            warningMessage()
        }
    }

    private val starForActivityGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            val location = result.data?.data
            //binding.ivFoto.setImageURI(location)

        }
    }


    //Método que ingresa a la galería de nuestro dispositivo
    private fun selectPhotoGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
    }



    private fun requestPermissionsCamara(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){ //Primero se debe averiguar el nivel de API
            when{
                //Se comprueba si los permisos ya están habilitados, de ser así se accesa a la galería
                ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.CAMERA)  == PackageManager.PERMISSION_GRANTED -> {
                    selectPhotoCamara()
                }
                //En caso contrario se solicitará aceptar los permisos, pero también se comprobará si son aceptados o no
                else ->{
                    requestPermissionLauncherCAMERA.launch(android.Manifest.permission.CAMERA)
                }
            }
        }else{
            selectPhotoCamara() //Método que seleccionará la imagen de nuestra galería
        }
    }



    //Método que ingresa a la galería de nuestro dispositivo
    private fun selectPhotoCamara() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, cameraRequest)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            progress = ProgressDialog(this.context)
            progress?.setTitle("Carga de Galeria")
            progress?.setMessage("Espere")
            progress?.max = 10
            progress?.show()
            // When an Image is picked
            if (requestCode === PICK_IMAGE_MULTIPLE && resultCode === RESULT_OK && null != attr.data) {
                // Get the Image from data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                //imagesEncodedList = ArrayList<String>()
                if (data!!.getClipData() != null) {
                    val count = data!!.clipData!!.itemCount
                    if (count>0) {
                        for (i in 0 until count) {
                            urls.add(Imagenes(data.clipData!!.getItemAt(i).uri,null))
                        }
                    }
                } else{
                    val imageUri = data?.data
                    urls.add(Imagenes(imageUri, null))
                }

                adapterGaleria.notifyDataSetChanged()

            } else if (requestCode == cameraRequest) {
                val photo: Bitmap = data?.extras?.get("data") as Bitmap

                urls.add(Imagenes(getImageUri(photo),null))
                adapterGaleria.notifyDataSetChanged()
            }else {
                Toast.makeText(
                    this.context, "You haven't picked Image",
                    Toast.LENGTH_LONG
                ).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this.context, "Something went wrong", Toast.LENGTH_LONG)
                .show()
        }
        Handler(Looper.getMainLooper()).postDelayed({
            progress?.dismiss();
        }, 1000)

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getImageUri(bitmap: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val rnds = (1000..100000).random()
        val path = MediaStore.Images.Media.insertImage(
            this.context?.contentResolver,
            bitmap,
            rnds.toString(),
            null
        )
        return Uri.parse(path)
    }
    private fun warningMessage(){
        Toast.makeText(context,"Es necesario aceptar los permisos",Toast.LENGTH_SHORT).show()
    }
}
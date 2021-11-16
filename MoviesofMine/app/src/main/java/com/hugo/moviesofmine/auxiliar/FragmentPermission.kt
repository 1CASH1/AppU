package com.hugo.moviesofmine.auxiliar

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import com.hugo.moviesofmine.interfaces.IFramentPremission

class FragmentPermission {
    fun startPermissionRequest(fA: FragmentActivity, fpi: IFramentPremission, manifiest: String){
        val requestPermissionLauncher =
            fA.registerForActivityResult(ActivityResultContracts.RequestPermission(), fpi::onGranted)
        requestPermissionLauncher.launch(manifiest)

    }
}
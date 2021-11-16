package com.hugo.moviesofmine.view.onboarding

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPargerAdapter(
    val list: ArrayList<Fragment>,
    activity: AppCompatActivity
    //fragment: FragmentManager,
    //lyfecycle: Lifecycle
): FragmentStateAdapter(activity ) {


    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}
package com.icare.icare.screens.user_gym

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val NUM_TABS = 2

class UserNutritionStatePagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return UserGymMyActivityFragment()
            1 -> return UserGymReservationFragment()
        }
        return UserGymMyActivityFragment()
    }
}
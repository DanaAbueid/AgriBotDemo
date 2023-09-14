package com.icare.icare.screens

import androidx.fragment.app.Fragment
import com.icare.icare.MainActivity

abstract class BaseFragment : Fragment() {
    abstract fun isLoggedin(): Boolean


    override fun onResume() {
        super.onResume()
        activity?.let {
            if (it is MainActivity) {
                it.isLoggedIn(isLoggedin())
            }
        }
    }

    fun toggleSideMenu(state: Boolean) {
        activity?.let { activityNotNull ->
            if (activityNotNull is MainActivity)
                activityNotNull.toggleSideMenu(state)
        }
    }
}
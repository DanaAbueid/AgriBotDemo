package com.icare.icare.screens.user_gym

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.icare.icare.R
import com.icare.icare.databinding.FragmentUserGymBinding
import com.icare.icare.screens.BaseFragment

open class UserGymFragment : BaseFragment() {
    private var binding: FragmentUserGymBinding? = null
    override fun isLoggedin(): Boolean = true

    private val tabsArray by lazy {
        arrayOf(
            getString(R.string.my_activity),
           getString(R.string.gym_reservation)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserGymBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let { bindingNotNull ->
            bindingNotNull.viewToolbar.ivMenu.visibility = View.VISIBLE
            bindingNotNull.viewToolbar.ivMenu.setOnClickListener {
                toggleSideMenu(true)
            }

            val adapter =
                activity?.supportFragmentManager?.let {
                    UserNutritionStatePagerAdapter(
                        it,
                        lifecycle
                    )
                }
            bindingNotNull.pager.adapter = adapter

            TabLayoutMediator(bindingNotNull.tabLayout, bindingNotNull.pager) { tab, position ->
                tab.text = tabsArray[position]
            }.attach()
        }

    }


}
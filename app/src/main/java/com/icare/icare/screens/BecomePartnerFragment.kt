package com.icare.icare.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.icare.icare.databinding.FragmentBecomeAPartnerBinding

class BecomePartnerFragment : BaseFragment() {
    private var binding: FragmentBecomeAPartnerBinding? = null
    override fun isLoggedin(): Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBecomeAPartnerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {
            it.viewGym.setOnClickListener {
                findNavController().navigate(
                    BecomePartnerFragmentDirections.actionBecomePartnerFragmentToSignupUpGymFragment()
                )
            }
            it.viewNutrients.setOnClickListener {
                findNavController().navigate(
                    BecomePartnerFragmentDirections.actionBecomePartnerFragmentToSignupUpNutrientsFragment()
                )
            }
        }

    }
}
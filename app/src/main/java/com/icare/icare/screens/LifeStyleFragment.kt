package com.icare.icare.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.icare.icare.databinding.FragmentLifeStyleBinding

class LifeStyleFragment : BaseFragment() {
    override fun isLoggedin(): Boolean = false
    private var binding: FragmentLifeStyleBinding? = null
    private val rbHealth = listOf<String>(
        "Eating Health",
        "Not Eating Health",
    )
    private val rbPhysicallyActive = listOf<String>(
        "Physically Active",
        "Not PhysicallyActive",
    )

    private val rbSmoker = listOf<String>(
        "Smoker",
        "Non Smoker",
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLifeStyleBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { bindingNotNull ->
            bindingNotNull.buttonNext.setOnClickListener {
                findNavController().navigate(LifeStyleFragmentDirections.actionLifeStyleFragmentToChronicDiseaseFragment())
            }

            rbHealth.forEach {
                val radioButton = RadioButton(context)
                radioButton.setText(it)
                radioButton.layoutDirection = View.LAYOUT_DIRECTION_RTL
                radioButton.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                bindingNotNull.rgEatHealth.addView(radioButton)
            }
            rbPhysicallyActive.forEach {
                val radioButton = RadioButton(context)
                radioButton.setText(it)
                radioButton.layoutDirection = View.LAYOUT_DIRECTION_RTL
                radioButton.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                bindingNotNull.rgPhysicallyActive.addView(radioButton)
            }
            rbSmoker.forEach {
                val radioButton = RadioButton(context)
                radioButton.setText(it)
                radioButton.layoutDirection = View.LAYOUT_DIRECTION_RTL
                radioButton.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                bindingNotNull.rgSmoker.addView(radioButton)
            }

        }

    }
}
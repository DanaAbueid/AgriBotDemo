package com.icare.icare.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.icare.icare.R
import com.icare.icare.databinding.FragmentChronicDiseaseBinding

class ChronicDiseaseFragment : BaseFragment() {
    private var binding: FragmentChronicDiseaseBinding? = null
    override fun isLoggedin(): Boolean = false
    private val cbDisease = listOf<String>(
        "Asthma", "Arthritis", "Cancer", "Custic Fibrosis", "Diabetes", "Heart Disease", "Obesity"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChronicDiseaseBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let { bindingNotNull ->
            bindingNotNull.buttonSignUpp.setOnClickListener {
                findNavController().popBackStack(R.id.loginFragment, false)
            }
            bindingNotNull.llDiseases.removeAllViews()
            cbDisease.forEach {
                val checkBox = CheckBox(context)
                checkBox.setText(it)
                checkBox.layoutDirection = View.LAYOUT_DIRECTION_RTL
                checkBox.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                bindingNotNull.llDiseases.addView(checkBox)
            }
            fillAllergies()
        }

    }

    private fun fillAllergies() {
        context?.let { it1 ->
            binding?.let { it2 ->
                val choices = listOf<String>("Yes", "None")
                val adapter = ArrayAdapter<String>(
                    it1,
                    android.R.layout.simple_spinner_item,
                    choices
                )
                it2.mactvAllergies.setAdapter(adapter)
                it2.mactvAllergies.setOnItemClickListener { adapterView, view, i, l ->

                }
            }
        }
    }
}
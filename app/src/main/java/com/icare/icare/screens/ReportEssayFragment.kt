
package com.icare.icare.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.icare.icare.databinding.FragmentReportEssayBinding

class ReportEssayFragment : BaseFragment() {
    private var binding: FragmentReportEssayBinding? = null
    override fun isLoggedin(): Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReportEssayBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    private fun validateFields(): Boolean {
        var isValid = true

        val title = binding?.reportTitleNutri?.editText?.text.toString().trim()
        val spinner = binding?.reportReasonNutri?.isSelected.toString().trim()
        val essay = binding?.reportTextNutri?.editText?.text.toString().trim()

        if (title.isEmpty()) {
            binding?.reportTitleNutri?.error = "Please Fill the Title"
            isValid = false
        }

        if (essay.isEmpty()) {
            binding?.reportTextNutri?.error = "Please fill the essay"
            isValid = false
        }



        return isValid
    }
}
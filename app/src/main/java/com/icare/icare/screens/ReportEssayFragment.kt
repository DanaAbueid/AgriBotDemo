
package com.icare.icare.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.icare.icare.Request.ReportRequestBody
import com.icare.icare.ViewModel.AuthViewModel
import com.icare.icare.backend.ApiService
import com.icare.icare.backend.ProgressApi
import com.icare.icare.backend.ReportApi
import com.icare.icare.backend.ReportApiService
import com.icare.icare.backend.RetrofitInstance
import com.icare.icare.databinding.FragmentReportEssayBinding

class ReportEssayFragment : BaseFragment() {
    private var binding: FragmentReportEssayBinding? = null
    private val authViewModel: AuthViewModel by viewModels()

    private val ReportApi = RetrofitInstance.CreateReportApi()
    val userId = authViewModel.userId

    // Replace with your authentication service

    override fun isLoggedin(): Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReportEssayBinding.inflate(inflater, container, false)
        return binding?.root
        val accessToken = authViewModel.accessToken

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val accessToken = authViewModel.accessToken
        val apiService = ApiService(accessToken, "BASE_URL")
        val ReportsApi = apiService.retrofit.create(ReportApi::class.java)

        binding?.let { bindingNotNull ->

            // Add click listener for the submit button
            bindingNotNull.btnSendReport.setOnClickListener {
                if (validateFields()) {
                    submitReport()
                }
            }
        }
    }

    private fun validateFields(): Boolean {
        var isValid = true

        val title = binding?.reportTitleNutri?.editText?.text.toString().trim()
        val spinner = binding?.reportReasonNutri?.selectedItem.toString().trim()
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

    private fun submitReport() {
        val title = binding?.reportTitleNutri?.editText?.text.toString().trim()
        val spinner = binding?.reportReasonNutri?.selectedItem.toString().trim()
        val essay = binding?.reportTextNutri?.editText?.text.toString().trim()

        val reportRequestBody = ReportRequestBody(
            userId = userId,
            reportTitle = title,
            reportReason = spinner,
            adminName = "admin",
            adminReplyText = essay,
            reportStatus = true
        )

        ReportApi.saveReport(reportRequestBody).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {

                    Toast.makeText(requireContext(), "Report submitted successfully", Toast.LENGTH_SHORT).show()
                } else {

                    Toast.makeText(requireContext(), "Failed to submit report", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {

                Toast.makeText(requireContext(), "Network error. Please try again later", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Replace this with your method to extract User ID from the token
    private fun getUserIdFromToken(): Long {
        // Implement the logic to decode the token and extract the User ID here
        // Return the extracted User ID
        return 1L // Placeholder value, replace with the actual User ID
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

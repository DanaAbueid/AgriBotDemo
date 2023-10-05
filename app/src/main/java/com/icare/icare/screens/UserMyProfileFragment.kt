
package com.icare.icare.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.icare.icare.R
import com.icare.icare.ViewModel.AuthViewModel
import com.icare.icare.backend.RetrofitInstance
import com.icare.icare.backend.UserInfoApi
import com.icare.icare.databinding.FragmentUserMyProfileBinding
import kotlinx.coroutines.launch

class UserMyProfileFragment : BaseFragment() {

    private var binding: FragmentUserMyProfileBinding? = null
    private lateinit var userInfoApi: UserInfoApi // Step 1: Create an instance
    private val authViewModel: AuthViewModel by viewModels()


    override fun isLoggedin() = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserMyProfileBinding.inflate(inflater, container, false)
        return binding?.root
        val accessToken = authViewModel.accessToken

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let { bindingNotNull ->
            bindingNotNull.viewToolbar.ivMenu.visibility = View.VISIBLE
            bindingNotNull.viewToolbar.ivMenu.setOnClickListener {
                toggleSideMenu(true)
            }
        }
        binding?.let { bindingNotNull ->
            bindingNotNull.tvMyProfile.setOnClickListener {
                findNavController().navigate(UserMyProfileFragmentDirections.actionSignupToPayment())
            }
        }

        // Step 2: Initialize the UserInfoApi instance
        userInfoApi = RetrofitInstance.retrofit.create(UserInfoApi::class.java)

        // Call API functions and update UI elements
        updateUserProfile()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun updateUserProfile() {
        // Assume you have the user ID from somewhere (e.g., user authentication)
        val userId = 123L // Replace with the actual user ID

        // Use coroutines to make API calls
        lifecycleScope.launch {
            try {
                // Make API calls to retrieve user data
                val firstName = userInfoApi.getUserFirstname(userId)
                val lastName = userInfoApi.getUserLastName(userId)
                val location = userInfoApi.getUserLocation(userId)
                val email = userInfoApi.getUserId(userId)
                val phoneNumber = userInfoApi.getPhoneNumber(userId)
                val accountStatus = userInfoApi.getAccountStatus(userId)

                // Update UI elements with retrieved data
                binding?.tlFirstNameGet?.editText?.setText(firstName)
                binding?.tvLastNameGet?.editText?.setText(lastName)
                binding?.gerLocation?.editText?.setText(location)
                binding?.phoneGet?.editText?.setText(phoneNumber)
             //   binding?.emailGet?.editText?.text=email


            } catch (e: Exception) {
                val setFname = "Not Found"
                val setLname = "Not Found"
                binding?.tlFirstNameGet?.editText?.setText(setFname)
                binding?.tvLastNameGet?.editText?.setText(setLname)
                binding?.gerLocation?.editText?.setText(setFname)
                binding?.phoneGet?.editText?.setText(setFname)
                binding?.phoneGet?.editText?.setText(setFname)

                e.printStackTrace()
                // You can show an error message to the user if needed
            }
        }
    }
}

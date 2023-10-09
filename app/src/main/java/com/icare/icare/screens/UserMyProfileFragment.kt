
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
import com.icare.icare.backend.ApiService
import com.icare.icare.backend.ProgressApi
import com.icare.icare.backend.RetrofitInstance
import com.icare.icare.backend.UserInfoApi
import com.icare.icare.databinding.FragmentUserMyProfileBinding
import kotlinx.coroutines.launch

class UserMyProfileFragment : BaseFragment() {

    private var binding: FragmentUserMyProfileBinding? = null
  //  private lateinit var userInfoApi: UserInfoApi // Step 1: Create an instance
    private val authViewModel: AuthViewModel by viewModels()
    val userId = 4853
    private lateinit var userInfoApi: UserInfoApi // Declare it at the class level




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

        // Inside onViewCreated method of ProgressFragment
        val accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYW5hMjAwMUBnbWFpbC5jb20iLCJpYXQiOjE2OTY4Mjc2NDQsImV4cCI6MTY5NjkxNDA0NH0.l-fGB3QBvA6Fb8_6q0UnbM_I5Rs2bqocExaGGYHY1_I"
        val apiService = ApiService(accessToken, "BASE_URL")
        userInfoApi = apiService.retrofit.create(UserInfoApi::class.java) // Initialize it here

        val userId = authViewModel.userId // Get the user ID from your ViewModel

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

        updateUserProfile()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun updateUserProfile() {
        // Assume you have the user ID from somewhere (e.g., user authentication)

        // Use coroutines to make API calls
        lifecycleScope.launch {
            try {
                // Make API calls to retrieve user data
                val firstName = userInfoApi.getUserFirstname(userId.toLong())
                val lastName = userInfoApi.getUserLastName(userId.toLong())
                val location = userInfoApi.getUserLocation(userId.toLong())
                val email = userInfoApi.getUserId(userId.toLong())
                val phoneNumber = userInfoApi.getPhoneNumber(userId.toLong())
                val accountStatus = userInfoApi.getAccountStatus(userId.toLong())

                // Update UI elements with retrieved data
                binding?.tlFirstNameGet?.editText?.setText(firstName)
                binding?.tvLastNameGet?.editText?.setText(lastName)
                binding?.gerLocation?.editText?.setText(location)
                binding?.phoneGet?.editText?.setText(phoneNumber)
                val emailEditText = binding?.emailGet?.editText
                val emailgeter = email.toString() // Replace with your desired text
                emailEditText?.setText(emailgeter)
                emailEditText?.isFocusable = false
                emailEditText?.isClickable = false
                emailEditText?.background = null


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

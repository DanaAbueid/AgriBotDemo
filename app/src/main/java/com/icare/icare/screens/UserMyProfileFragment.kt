
package com.icare.icare.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.icare.icare.MainActivity
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
  private lateinit var authViewModel: AuthViewModel
    private var userId: Long = -1 // Initialize it with a default value

var authService: UserInfoApi? = null




    override fun isLoggedin() = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserMyProfileBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inside onViewCreated method of ProgressFragment
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
         authService = RetrofitInstance.createinfoService()

        val sharedPrefs = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        userId = sharedPrefs.getLong("userId", -1)

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
                val firstName = authService?.getUserFirstname(userId)
                val lastName = authService?.getUserLastName(userId)
                val location = authService?.getUserLocation(userId)
                val email = authService?.getUserId(userId)
                val phoneNumber = authService?.getPhoneNumber(userId)

                val accountStatus = authService?.getAccountStatus(userId)

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


package com.icare.icare.screens

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.gson.GsonBuilder
import com.icare.icare.MainActivity
import com.icare.icare.R
import com.icare.icare.ViewModel.AuthViewModel
import com.icare.icare.backend.ApiService
import com.icare.icare.backend.ProgressApi
import com.icare.icare.backend.RetrofitInstance
import com.icare.icare.backend.UserInfoApi
import com.icare.icare.databinding.FragmentUserMyProfileBinding
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserMyProfileFragment : BaseFragment() {

    private var binding: FragmentUserMyProfileBinding? = null
  //  private lateinit var userInfoApi: UserInfoApi // Step 1: Create an instance
  private lateinit var authViewModel: AuthViewModel
    var userId: Long? =null





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

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://34.224.41.160:8080")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

         val authService = retrofit.create(UserInfoApi::class.java)

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        authViewModel.initSharedPreferences(requireContext())
        userId = authViewModel.user_id

        val sharedPrefs = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        Log.d("UserIdDebug", "UserId received from API: $userId")


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


////////////////////////////////////
        authService.getUserId(userId!!).enqueue(object :
            Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val CropHealthy = response.body()
                    val textToSet = "$CropHealthy"
                    binding?.emailGet?.editText?.setText(textToSet)
                } else {
                    val textToSet = "error"
                    binding?.emailGet?.editText?.setText(textToSet)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                val textToSet = "network error"
                binding?.emailGet?.editText?.setText(textToSet)            }
        })
////////////////////////////////////
        authService.getUserFirstname(userId!!).enqueue(object :
            Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val CropHealthy = response.body()
                    val textToSet = "$CropHealthy"
                    binding?.tlFirstNameGet?.editText?.setText(textToSet)
                } else {
                    val textToSet = "error"
                    binding?.tlFirstNameGet?.editText?.setText(textToSet)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                val textToSet = "network error"
                binding?.tlFirstNameGet?.editText?.setText(textToSet)            }
        })
////////////////////////////////////

        authService.getUserLastName(userId!!).enqueue(object :
            Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val CropHealthy = response.body()
                    val textToSet = "$CropHealthy"
                    binding?.tvLastNameGet?.editText?.setText(textToSet)
                } else {
                    val textToSet = "error"
                    binding?.tvLastNameGet?.editText?.setText(textToSet)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                val textToSet = "network error"
                binding?.tvLastNameGet?.editText?.setText(textToSet)            }
        })
////////////////////////////////////
        authService.getUserLocation(userId!!).enqueue(object :
            Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val CropHealthy = response.body()
                    val textToSet = "$CropHealthy"
                    Log.d("UserIdDebug", "UserId received from API: $CropHealthy")

                    binding?.gerLocation?.editText?.setText(textToSet)
                } else {
                    val textToSet = "error"
                    binding?.gerLocation?.editText?.setText(textToSet)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                val textToSet = "network error"

                binding?.gerLocation?.editText?.setText(textToSet)            }
        })
////////////////////////////////////

        authService.getPhoneNumber(userId!!).enqueue(object :
            Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val CropHealthy = response.body()
                    val textToSet = "$CropHealthy"
                    binding?.phoneGet?.editText?.setText(textToSet)
                } else {
                    val textToSet = "error"
                    binding?.phoneGet?.editText?.setText(textToSet)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                val textToSet = "network error"
                binding?.phoneGet?.editText?.setText(textToSet)            }
        })
////////////////////////////////////


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
/*
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
        }*/
    }


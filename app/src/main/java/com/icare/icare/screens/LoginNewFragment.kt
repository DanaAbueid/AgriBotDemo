
package com.icare.icare.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.icare.icare.Request.AuthenticationRequest
import com.icare.icare.models.AuthenticationResponse
import com.icare.icare.backend.RetrofitInstance
import com.icare.icare.databinding.FragmentLoginNewBinding
import retrofit2.Call
import com.icare.icare.ViewModel.AuthViewModel
import retrofit2.Callback
import retrofit2.Response
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class LoginNewFragment : BaseFragment() {

    private val authViewModel: AuthViewModel by viewModels()
    private var binding: FragmentLoginNewBinding? = null

    override fun isLoggedin(): Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginNewBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { bindingNotNull ->

            // Add click listener for login button
            bindingNotNull.tvLoginBtn2.setOnClickListener {
                // Get user input from EditText fields
                val email = bindingNotNull.editTextEmail.text.toString()
                val password = bindingNotNull.editTextPsw.text.toString()

                // Check if email and password are not empty (add additional validation as needed)
                if (email.isNotEmpty() && password.isNotEmpty()) {


                    // ...
                    val authService = RetrofitInstance.createAuthService()
                    val authenticationRequest = AuthenticationRequest(email, password)

                    authService.login(authenticationRequest).enqueue(object : Callback<AuthenticationResponse> {
                        override fun onResponse(
                            call: Call<AuthenticationResponse>,
                            response: Response<AuthenticationResponse>
                        ) {
                            if (response.isSuccessful) {
                                val authenticationResponse = response.body()
                                if (authenticationResponse != null) {
                                 //   authViewModel.accessToken = authenticationResponse.accessToken
                                    findNavController().navigate(UserSignUpFragmentDirections.actionSignupToCode())
                                } else {
                                    val errorMessage = "Response body is null. Signup failed."
                                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                val errorMessage = "Signup failed. Please try again."
                                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                            }

                        }


                        override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                            val errorMessage = "Network error. Please check your internet connection."
                            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    })}
// ...


                }


            }


        }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

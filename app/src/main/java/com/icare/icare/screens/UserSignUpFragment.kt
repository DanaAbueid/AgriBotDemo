package com.icare.icare.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.icare.icare.R
import com.icare.icare.ViewModel.AuthViewModel
import com.icare.icare.backend.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.icare.icare.databinding.FragmentUserSignUpBinding
import com.icare.icare.models.Robot
import com.icare.icare.enumclass.Role
import com.icare.icare.models.AuthenticationResponse
import com.icare.icare.models.RegisterRequest
import java.time.LocalDateTime

class UserSignUpFragment : BaseFragment() {

    private var binding: FragmentUserSignUpBinding? = null
    private val authViewModel: AuthViewModel by viewModels()

    private val LOCATION_PERMISSION_REQUEST_CODE = 1001
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun isLoggedin(): Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserSignUpBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { bindingNotNull ->
            bindingNotNull.buttonLogin2.setOnClickListener {
                if (validateFields()) {
                    signUp()
                    findNavController().navigate(UserSignUpFragmentDirections.actionSignupToCode())
                }
            }
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (areLocationPermissionsGranted()) {
            // Location permissions are already granted, proceed with location retrieval.
            getUserLocation()
        } else {
            // Request location permissions if they are not granted.
            requestLocationPermissions()
        }

        binding?.let { bindingNotNull ->
            bindingNotNull.tilFarmLocation.setOnClickListener {
                // Fill the text field with the user's location when clicked.
                getUserLocation()
            }
        }
    }

    private fun areLocationPermissionsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    private fun getUserLocation() {
        Log.d("LocationTest", "getUserLocation() called.")
        if (areLocationPermissionsGranted()) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(), // Use requireContext() here instead of "this"
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireContext(), // Use requireContext() here instead of "this"
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        // Here, you can access the user's location using the `location` object.
                        val latitude = location.latitude
                        val longitude = location.longitude

                        val locationText = "Latitude: $latitude, Longitude: $longitude"
                        binding?.tilFarmLocation?.editText?.setText(locationText)
                    } else {
                        // Location is not available. Handle the case when the user's location is not accessible.
                        // For example, show a toast or an error message.
                    }
                }
                .addOnFailureListener { exception ->
                    // Failed to get user's location. Handle the exception appropriately.
                    // For example, show a toast or an error message.
                }
        } else {


        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                // Location permissions granted. Proceed with location retrieval.
                getUserLocation()
            } else {
                showLocationRequiredToast()
            }
        }
    }

    private fun showLocationRequiredToast() {
        Toast.makeText(
            requireContext(),
            "Location permission is required to proceed.",
            Toast.LENGTH_SHORT
        ).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    private fun validateFields(): Boolean {
        var isValid = true

        val email = binding?.editTextEmail?.text.toString().trim()
        val username = binding?.editTextLastName?.text.toString().trim()
        val password = binding?.editTextPassword?.text.toString().trim()
        val phoneNumber = binding?.editTextPhoneNumber?.text.toString().trim()
        val name = binding?.editTextName?.text.toString().trim()
        val robot = binding?.editTextUsername2?.text.toString().trim()


        if (email.isEmpty()) {
            binding?.editTextEmail?.error = "Email is required"
            isValid = false
        }

        if (username.isEmpty()) {
            binding?.editTextLastName?.error = "Last name is required"
            isValid = false
        }

        if (password.isEmpty()) {
            binding?.editTextPassword?.error = "Password is required"
            isValid = false
        }

        if (phoneNumber.isEmpty()) {
            binding?.editTextPhoneNumber?.error = "Phone number is required"
            isValid = false
        }

        if (name.isEmpty()) {
            binding?.editTextName?.error = "Name is required"
            isValid = false
        }

        if (robot.isEmpty()) {
            binding?.editTextUsername2?.error = "Robot is required is required"
            isValid = false
        }

        return isValid
    }
    private fun signUp() {
        val email = binding?.editTextEmail?.text.toString()
        val lastname = binding?.editTextLastName?.text.toString()
        val password = binding?.editTextPassword?.text.toString()
        val phoneNumber = binding?.editTextPhoneNumber?.text.toString()
        val firstName = binding?.editTextName?.text.toString()
        val location = binding?.tilFarmCard?.text.toString()
        val serialNumber = binding?.editTextUsername2?.text.toString()
        val robot = Robot(serialNumber)


        val authService = RetrofitInstance.createAuthService()
//the
        val registerRequest = RegisterRequest(
            firstname = firstName,
            lastname = lastname,
            robot = robot,
            email = email,
            password = password,
            phoneNumber = phoneNumber,
            location = location,
            accountStatus = true,
            role = Role.USER,
            otp = ""
        )

        authService.register(registerRequest).enqueue(object : Callback<AuthenticationResponse> {
            override fun onResponse(call: Call<AuthenticationResponse>, response: Response<AuthenticationResponse>) {
                if (response.isSuccessful) {
                    // Signup successful, handle the response here
                    val authenticationResponse = response.body()
                    authViewModel.accessToken = authenticationResponse?.accessToken
                    findNavController().navigate(UserSignUpFragmentDirections.actionSignupToCode())
                } else {
                    val errorMessage = "Signup failed. Please try again."
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                // Handle network failure here
                val errorMessage = "Network error. Please check your internet connection."
                // Show an error message to the user, e.g., using a Toast
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }


}


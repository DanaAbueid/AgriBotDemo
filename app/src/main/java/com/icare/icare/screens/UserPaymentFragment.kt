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
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.icare.icare.databinding.FragmentUserPaymentBinding

class UserPaymentFragment : BaseFragment() {

    private var binding: FragmentUserPaymentBinding? = null
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun isLoggedin(): Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserPaymentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let { bindingNotNull ->
            bindingNotNull.tvButtonNextDone.setOnClickListener {
                findNavController().navigate(UserSignUpFragmentDirections.actionSignupToSubPlan())
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
                        binding?.tilCardNumber?.editText?.setText(locationText)
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
}

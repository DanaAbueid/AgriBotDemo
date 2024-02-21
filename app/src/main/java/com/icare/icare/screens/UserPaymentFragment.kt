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
                findNavController().navigate(UserPaymentFragmentDirections.actionPaymentToConfirm())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

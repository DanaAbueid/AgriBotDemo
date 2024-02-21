
package com.icare.icare.screens


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.set
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.icare.icare.R
import com.icare.icare.Request.AuthenticationRequest
import com.icare.icare.ViewModel.AuthViewModel
import com.icare.icare.backend.RetrofitInstance
import com.icare.icare.databinding.FragmentUpgradeSubscriptionBinding
import com.icare.icare.models.AuthenticationResponse
import kotlinx.android.synthetic.main.fragment_upgrade_subscription.tv_end_get
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.Timestamp


class UpgradeSubscriptionFragment : BaseFragment() {

    private lateinit var authViewModel: AuthViewModel

    private var binding: FragmentUpgradeSubscriptionBinding? = null

    override fun isLoggedin(): Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpgradeSubscriptionBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        val sharedPrefs = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userId = sharedPrefs.getLong("userId", -1)


        val authService = RetrofitInstance.UserSubscriptionApi()

        authService.getSubscriptionEndDate(userId).enqueue(object : Callback<Timestamp?> {
            override fun onResponse(
                call: Call<Timestamp?>,
                response: Response<Timestamp?>
            ) {
                if (response.isSuccessful) {
                    val authenticationResponse = response.body()
                    if (authenticationResponse != null) {
                        binding?.tvEndGet?.editText?.setText(authenticationResponse.toString())
              } else {
                        val errorMessage = "Response body is null. Signup failed."
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorMessage = "Signup failed. Please try again."
                    binding?.tvEndGet?.editText?.setText(userId.toString())

                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }

            }


            override fun onFailure(call: Call<Timestamp?>, t: Throwable) {
                val errorMessage = "Network error. Please check your internet connection."
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        })








        binding?.viewToolbar?.ivMenu?.visibility = View.VISIBLE
        binding?.viewToolbar?.ivMenu?.setOnClickListener {
            toggleSideMenu(true)
        }

        binding?.let { bindingNotNull ->
            bindingNotNull.button.setOnClickListener {
                findNavController().navigate(UpgradeSubscriptionFragmentDirections.actionMySubToSubList())
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}



package com.icare.icare.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.icare.icare.MainActivity
import com.icare.icare.R
import com.icare.icare.ViewModel.AuthViewModel
import com.icare.icare.backend.ApiService
import com.icare.icare.backend.ProgressApi
import com.icare.icare.backend.RetrofitInstance
import com.icare.icare.backend.UserInfoApi
import com.icare.icare.databinding.FragmentMainDashboardBinding

import com.icare.icare.models.Notifications

import kotlinx.android.synthetic.main.fragment_main_dashboard.iv_condition
import kotlinx.android.synthetic.main.fragment_main_dashboard.tv_condition_label
import kotlinx.android.synthetic.main.fragment_main_dashboard.tv_condition_text
import kotlinx.android.synthetic.main.fragment_main_dashboard.tv_image_condition
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainDashboardFragment : BaseFragment() {
    private var binding: FragmentMainDashboardBinding? = null
    private lateinit var authViewModel: AuthViewModel
    private var progressId: Long? = null
    var userId: Long? =null
    var authService: ProgressApi? = null


    private lateinit var btncrops: Button
    private lateinit var btnsoil: Button
    private lateinit var btntask: Button
    override fun isLoggedin(): Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainDashboardBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewToolbar?.ivMenu?.visibility = View.VISIBLE
        binding?.viewToolbar?.ivMenu?.setOnClickListener {
            toggleSideMenu(true)
        }

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        authViewModel.initSharedPreferences(requireContext())
        userId = authViewModel.user_id

        Log.d("UserIdDebug", "UserId received from API: $userId")


        val accessToken = authViewModel.accessToken
        val apiService = ApiService(accessToken, "http://18.234.66.152:8080/")
        val progressApi = apiService.retrofit.create(ProgressApi::class.java)
        authService = RetrofitInstance.createprogService()


        if (userId != null) {
            authService!!.getProgressIdCurrentTime(userId!!).enqueue(object : Callback<Long> {
                override fun onResponse(call: Call<Long>, response: Response<Long>) {
                    if (response.isSuccessful) {
                        progressId = response.body()
                        Log.d("UserIdDebug", "UserId received from API: $progressId")
                    } else {
                        val errorMessage = "Retrieving the progress failed. Please try again later."
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()                    }
                }
                override fun onFailure(call: Call<Long>, t: Throwable) {
                    val errorMessage = "Retrieving the progress failed. Please try again."
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()                   }
            })
        } else {
            val errorMessage = "ERROR Please try again later."
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()         }



        btncrops = view.findViewById(R.id.btn_crops)
        btnsoil = view.findViewById(R.id.btn_soil)
        btntask = view.findViewById(R.id.btn_task)

        btncrops.setOnClickListener { onButtonClicked(btncrops) }
        btnsoil.setOnClickListener { onButtonClicked(btnsoil) }
        btntask.setOnClickListener { onButtonClicked(btntask) }

        val UserNotificationsAdapter = activity?.let {
            binding?.rvUserNotifications?.let { it1 ->
                UserNotificationsAdapter(
                    it1, it
                )
            }
        }
        val NotificationsList = listOf<Notifications>(
            Notifications("The Robot task is now finished check the available analysis for this task!",R.drawable.correct),
            Notifications("Humidity is low watering the soil started ",R.drawable.cloud_error_24_regular__1_),
            Notifications("Soil Health is Good!", R.drawable.soil_temperature_field__1_),

        )
        val dividerItemDecoration = DividerItemDecoration(
            binding?.rvUserNotifications?.context,
            RecyclerView.VERTICAL
        )
        context?.let {
            ContextCompat.getDrawable(it, R.drawable.divider)
                ?.let { dividerItemDecoration.setDrawable(it) }
        }
        UserNotificationsAdapter?.addItemDecoration(dividerItemDecoration)
        UserNotificationsAdapter?.addAll(NotificationsList)?.refresh()


        binding?.let { bindingNotNull ->
            bindingNotNull.cvLogout.setOnClickListener {
                findNavController().navigate(MainDashboardFragmentDirections.actionSignupToPayment())
            }}


    }
    private fun onButtonClicked(clickedButton: Button) {
        // Reset the background color (tint) for all buttons
        resetButtonBackgroundColors()

        // Set a different background color (tint) for the clicked button
        clickedButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green3))

        // Handle the button's click action here
        when (clickedButton.id) {
            R.id.btn_crops -> {

                // Create a Retrofit instance for your ProgressController
                val progressApi = RetrofitInstance.createProgressApi()

                // Fetch data from the API for each TextView and update them
                progressApi.getCropHealthy(progressId!!).enqueue(object :
                    Callback<Int?> {
                    override fun onResponse(call: Call<Int?>, response: Response<Int?>) {
                        if (response.isSuccessful) {
                            val CropHealthy = response.body() // Get the temperature from the response
                            // Update the TextView with the new value
                            binding?.tvConditionText?.text = "$CropHealthy "
                        } else {
                            binding?.tvConditionText?.text = "ERROR"
                        }
                    }

                    override fun onFailure(call: Call<Int?>, t: Throwable) {
                        binding?.tvConditionText?.text = "16"
                    }
                })

                iv_condition.setImageResource(R.drawable.plant_bold) // Replace with your drawable resource
                tv_condition_label.text = "Healthy Crops"
                val newDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.plant_line)
                tv_image_condition.text="33"
                tv_image_condition.setCompoundDrawablesWithIntrinsicBounds(newDrawable, null, null, null)

            }
            R.id.btn_soil -> {
                val progressApi = RetrofitInstance.createProgressApi()

                // Fetch data from the API for each TextView and update them
                authService?.getSoilHealth(progressId!!)?.enqueue(object :
                    Callback<Boolean> {
                    override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                        if (response.isSuccessful) {
                            val CropHealthy = response.body()

                            if (CropHealthy == true) {
                                binding?.tvConditionText?.text = "Good "
                            }else {
                                binding?.tvConditionText?.text = "Poor " }

                        } else {
                            binding?.tvConditionText?.text = "Good!"
                        }
                    }

                    override fun onFailure(call: Call<Boolean>, t: Throwable) {
                        binding?.tvConditionText?.text = "Good!"
                    }
                })
                //////////////////////////////////////

                iv_condition.setImageResource(R.drawable.soil_temperature_field__1_) // Replace with your drawable resource
                tv_condition_label.text = "Soil Health"


                authService?.getSoilHumidity(progressId!!)?.enqueue(object : Callback<Double> {
                    override fun onResponse(call: Call<Double>, response: Response<Double>) {
                        if (response.isSuccessful) {
                            val Humidity = response.body() // Get the temperature from the response
                            // Update the TextView with the new value
                            binding?.tvImageCondition?.text = "$Humidity %"
                        } else {
                            binding?.tvImageCondition?.text = "33%"
                        }
                    }

                    override fun onFailure(call: Call<Double>, t: Throwable) {
                        binding?.tvImageCondition?.text = "61%"
                    }
                })

                val newDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.humidity__1_)
                tv_image_condition.setCompoundDrawablesWithIntrinsicBounds(newDrawable, null, null, null)
            }
            R.id.btn_task -> {
                iv_condition.setImageResource(R.drawable.progress__1_) // Replace with your drawable resource
                tv_condition_label.text = "Task Period"
                tv_condition_text.text="Running"
                tv_image_condition.text="33%"
                val newDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.refresh__1_)
                tv_image_condition.setCompoundDrawablesWithIntrinsicBounds(newDrawable, null, null, null)
            }
        }
    }
    private fun resetButtonBackgroundColors() {
        // Reset the background color (tint) for all buttons to the original color
        btncrops.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green1))
        btnsoil.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green1))
        btntask.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green1))
    }


}
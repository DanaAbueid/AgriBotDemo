
package com.icare.icare.screens

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import androidx.navigation.fragment.findNavController
import com.icare.icare.R
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import com.icare.icare.backend.RetrofitInstance
import com.icare.icare.databinding.FragmentProgressBinding

class ProgressFragment : BaseFragment() {

    private val animationDuration = 3000L

    private var binding: FragmentProgressBinding? = null

    override fun isLoggedin() = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Replace with your actual user ID
        val userId = 10000000000

        // Create a Retrofit instance for your ProgressController
        val progressApi = RetrofitInstance.createProgressApi()

        // Fetch data from the API for each TextView and update them
        progressApi.getSoilTemperature(userId).enqueue(object : Callback<Double> {
            override fun onResponse(call: Call<Double>, response: Response<Double>) {
                if (response.isSuccessful) {
                    val temperature = response.body() // Get the temperature from the response
                    // Update the TextView with the new value
                    binding?.tvSensorST?.text = "$temperature °"
                } else {
                    binding?.tvSensorST?.text = "ERROR"
                }
            }

            override fun onFailure(call: Call<Double>, t: Throwable) {
                binding?.tvSensorST?.text = "NETWORK ERROR"
            }
        })

        progressApi.getSoilHumidity(userId).enqueue(object : Callback<Double> {
            override fun onResponse(call: Call<Double>, response: Response<Double>) {
                if (response.isSuccessful) {
                    val Humidity = response.body() // Get the temperature from the response
                    // Update the TextView with the new value
                    binding?.tvSensorSH?.text = "$Humidity %"
                } else {
                    binding?.tvSensorSH?.text = "ERROR"
                }
            }

            override fun onFailure(call: Call<Double>, t: Throwable) {
                binding?.tvSensorSH?.text = "0"
            }
        })

        progressApi.getBatteryLevel(userId).enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful) {
                    val BatteryLevel = response.body() // Get the temperature from the response
                    // Update the TextView with the new value
                    binding?.tvBattary?.text = "$BatteryLevel %"
                } else {
                    binding?.tvBattary?.text = "ERROR"
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                binding?.tvBattary?.text = "0"
            }
        })

        progressApi.getBatteryLevel(userId).enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful) {
                    val BatteryLevel = response.body() // Get the temperature from the response
                    // Update the TextView with the new value
                    binding?.tvBattaryValue?.text = "$BatteryLevel %"
                } else {
                    binding?.tvBattaryValue?.text = "ERROR"
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                binding?.tvBattaryValue?.text = "0"
            }
        })

        progressApi.getSoilHealth(userId).enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful) {
                    val SoilHealth = response.body() // Get the temperature from the response
                    // Update the TextView with the new value
                    binding?.tvSensorWC?.text = "$SoilHealth "
                } else {
                    binding?.tvSensorWC?.text = "ERROR"
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                binding?.tvSensorWC?.text = "0"
            }
        })
        progressApi.getWeedCounter(userId).enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful) {
                    val WeedCounter = response.body() // Get the temperature from the response
                    // Update the TextView with the new value
                    binding?.tvSensorWeedC?.text = "$WeedCounter"
                } else {
                    binding?.tvSensorWeedC?.text = "ERROR"
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                binding?.tvSensorWeedC?.text = "0"
            }
        })

        progressApi.getWeedCounter(userId).enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful) {
                    val WeedCounterprogress = response.body()
                    val progressValue1 = WeedCounterprogress

                    binding?.tvLabelSoilH4?.text = "$WeedCounterprogress %"
                } else {
                    binding?.tvLabelSoilH4?.text = "ERROR"
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                binding?.tvLabelSoilH4?.text = "0"
            }
        })




        // Similar calls for other TextViews using their respective API endpoints...

        val progressValue1 = 77 // Set progress value for ProgressBar 1
        val progressValue2 = 57 // Set progress value for ProgressBar 2
        val progressValue3 = 12 // Set progress value for ProgressBar 3
        val progressValue4 = 78 // Set progress value for ProgressBar 4

        binding?.progressBar9?.progress = 0
        binding?.progressBar10?.progress = 0
        binding?.progressBar11?.progress = 0
        binding?.progressTaskPeriod?.progress = 0

        // Delay the animation until the view is properly measured and laid out
        binding?.root?.viewTreeObserver?.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // Animate progress bars after defining animationDuration
                animateProgressBar(binding?.progressBar9!!, progressValue1, animationDuration)
                animateProgressBar(binding?.progressBar10!!, progressValue2, animationDuration)
                animateProgressBar(binding?.progressBar11!!, progressValue3, animationDuration)
                animateProgressBar(binding?.progressTaskPeriod!!, progressValue4, animationDuration)

                // Remove the listener to prevent multiple calls
                binding?.root?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
            }
        })

        binding?.tvLabelSoilH4?.text = "$progressValue1%"
        binding?.tvLabelSoilC4?.text = "$progressValue2%"
        binding?.tvProgressCounterLabel?.text = "$progressValue3%"

        binding?.viewToolbar?.ivMenu?.visibility = View.VISIBLE
        binding?.viewToolbar?.ivMenu?.setOnClickListener {
            toggleSideMenu(true)
        }
    }

    private fun animateProgressBar(progressBar: ProgressBar, targetProgress: Int, duration: Long) {
        val animator = ObjectAnimator.ofInt(progressBar, "progress", targetProgress)
        animator.duration = duration
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Int
            progressBar.progress = progress
        }
        animator.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}


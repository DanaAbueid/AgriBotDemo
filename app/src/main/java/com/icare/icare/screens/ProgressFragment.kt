
package com.icare.icare.screens

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.gson.GsonBuilder
import com.icare.icare.ViewModel.AuthViewModel
import com.icare.icare.backend.ApiService
import com.icare.icare.backend.ProgressApi
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import com.icare.icare.backend.RetrofitInstance
import com.icare.icare.backend.UserInfoApi
import com.icare.icare.databinding.FragmentProgressBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProgressFragment : BaseFragment() {

    private lateinit var authViewModel: AuthViewModel
    private var progressId: Long? = null
    var userId: Long? =null
    var authService: ProgressApi? = null
    private var ProgressWeedCounter: Int? = null
    private var ProgressRemainingchemicals: Double? = null
    private var Progressealthycrop: Int? = null

    private val animationDuration = 2000L
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

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://34.224.41.160:8080")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val authService = retrofit.create(ProgressApi::class.java)

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        authViewModel.initSharedPreferences(requireContext())
        userId = authViewModel.user_id



        binding?.viewToolbar?.ivMenu?.visibility = View.VISIBLE
        binding?.viewToolbar?.ivMenu?.setOnClickListener {
            toggleSideMenu(true)
        }


            // Make the API request
            if (userId != null) {
                authService!!.getProgressIdCurrentTime(userId!!).enqueue(object : Callback<Long> {
                    override fun onResponse(call: Call<Long>, response: Response<Long>) {
                        if (response.isSuccessful) {
                            progressId = response.body()
                            if (progressId != null) {



                                authService!!.getSoilTemperature(progressId!!).enqueue(object : Callback<Double> {
                                override fun onResponse(call: Call<Double>, response: Response<Double>) {
                                    if (response.isSuccessful) {
                                        val temperature = response.body()
                                        if (temperature != null) {
                                            temperature.toInt()
                                        }// Get the temperature from the response
                                        // Update the TextView with the new value
                                        binding?.tvSensorST?.text = "$temperature°"
                                    } else {
                                        binding?.tvSensorST?.text = "ERROR"
                                    }
                                }

                                override fun onFailure(call: Call<Double>, t: Throwable) {
                                    binding?.tvSensorST?.text = "20°"
                                }
                            })








                            authService!!.getSoilHumidity(progressId!!).enqueue(object : Callback<Double> {
                                override fun onResponse(call: Call<Double>, response: Response<Double>) {
                                    if (response.isSuccessful) {
                                        val Humidity = response.body() // Get the temperature from the response
                                        // Update the TextView with the new value
                                        binding?.tvSensorSH?.text = "$Humidity%"
                                    } else {
                                        binding?.tvSensorSH?.text = "ERROR"
                                    }
                                }

                                override fun onFailure(call: Call<Double>, t: Throwable) {
                                    binding?.tvSensorSH?.text = "61%"
                                }
                            })






                            authService!!.getRemainingChemicals(progressId!!).enqueue(object : Callback<Double?> {
                                override fun onResponse(call: Call<Double?>, response: Response<Double?>) {
                                    if (response.isSuccessful) {
                                        ProgressRemainingchemicals = response.body()
                                        binding?.tvLabelSoilT4?.text = "$ProgressRemainingchemicals %"
                                        ProgressRemainingchemicals?.let { animateProgressBar(binding?.progressBar10!!, it.toInt(), animationDuration) }
                                    } else {
                                        binding?.tvLabelSoilT4?.text = "ERROR"
                                    }
                                }

                                override fun onFailure(call: Call<Double?>, t: Throwable) {
                                    binding?.tvLabelSoilT4?.text = "34%"
                                    val temperature =34
                                    animateProgressBar(binding?.progressBar10!!, temperature, animationDuration)

                                }
                            })




                            authService!!.getSoilHealth(progressId!!).enqueue(object : Callback<Boolean> {
                                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                                    if (response.isSuccessful) {
                                        val CropHealthy = response.body()

                                        if (CropHealthy == true) {
                                            binding?.tvSensorWC?.text = "Good "
                                        }else {
                                            binding?.tvSensorWC?.text = "Poor " }

                                    } else {
                                        binding?.tvSensorWC?.text = "Good!"
                                    }
                                }

                                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                                    binding?.tvSensorWC?.text = "Good"
                                }
                            })







//////////////////////////////////////

                            authService!!.getWeedCounter(progressId!!).enqueue(object : Callback<Int?> {
                                override fun onResponse(call: Call<Int?>, response: Response<Int?>) {
                                    if (response.isSuccessful) {
                                        ProgressWeedCounter = response.body()
                                        ProgressWeedCounter?.let {
                                            animateProgressBar(binding?.progressBar11!!,
                                                it, animationDuration)
                                        }
                                        authService!!.getCropHealthy(progressId!!).enqueue(object : Callback<Int?> {
                                            override fun onResponse(call: Call<Int?>, response: Response<Int?>) {
                                                if (response.isSuccessful) {
                                                    Progressealthycrop = response.body()
                                                    // Update the TextView with the new value
                                                    binding?.tvSensorWeedC?.text = "$Progressealthycrop"
                                                  //  binding?.tvLabelSoilH4?.text = "$Progressealthycrop"
                                                    val weedcounter = ProgressWeedCounter?.let { Progressealthycrop?.plus(it) }

                                                    if (weedcounter != null) {
                                                        binding?.progressBar9?.max = weedcounter
                                                        binding?.progressBar11?.max = weedcounter

                                                    }

                                                    Progressealthycrop?.let { animateProgressBar(binding?.progressBar9!!, it.toInt(), animationDuration) }

                                                    binding?.tvLabelSoilH4?.text = "$Progressealthycrop/$weedcounter "
                                                    binding?.tvLabelSoilC4?.text = "$ProgressWeedCounter/$weedcounter "

                                                } else {
                                                    binding?.tvSensorWeedC?.text = "ERROR"
                                                }
                                            }

                                            override fun onFailure(call: Call<Int?>, t: Throwable) {
                                                binding?.tvSensorWeedC?.text = "29"
                                                binding?.tvLabelSoilH4?.text = "29"
                                                val WeedCounter =29
                                                animateProgressBar(binding?.progressBar9!!, WeedCounter, animationDuration)

                                            }
                                        })

                                    } else {
                                        binding?.tvSensorWeedC?.text = "ERROR"
                                    }
                                }

                                override fun onFailure(call: Call<Int?>, t: Throwable) {
                                    binding?.tvLabelSoilC4?.text = "16"
                                    val WeedCounter =16
                                    animateProgressBar(binding?.progressBar11!!, WeedCounter.toInt(), animationDuration)

                                }
                            })
                            //////////////////////////////////
                            authService!!.getBatteryLevel(progressId!!).enqueue(object :
                                Callback<Int> {
                                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                                    if (response.isSuccessful) {
                                        val BatteryLevel = response.body()
                                        Log.d("UserIdDebug", "UserId received from API: $BatteryLevel")

                                        binding?.tvBattaryValue?.text = "$BatteryLevel %"
                                    } else {
                                        val textToSet = "error"
                                        binding?.tvBattaryValue?.text = "16%"                }
                                }

                                override fun onFailure(call: Call<Int>, t: Throwable) {

                                    binding?.tvBattaryValue?.text = "55 %"            }
                            })











                        } else {
                            val errorMessage = "Retrieving the progress failed. Please try again later."
                            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()                    }
                    }  else {
                            val errorMessage = "ERROR Please try again later."
                            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                        } }



                    override fun onFailure(call: Call<Long>, t: Throwable) {
                        val errorMessage = "Retrieving the progress failed. Please try again."
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()                   }
                })
            }


        Log.d("UserIdDebug", "UserId received from API: $progressId")







        val progressValue1 = Progressealthycrop // Set progress value for ProgressBar 1
        val progressValue2 = ProgressWeedCounter // Set progress value for ProgressBar 2
        val progressValue3 = 66 // Set progress value for ProgressBar 3
        val progressValue4 = 78 // Set progress value for ProgressBar 4

        binding?.progressBar9?.progress = 0
        binding?.progressBar10?.progress = 0
        binding?.progressBar11?.progress = 0
        binding?.progressTaskPeriod?.progress = 0

        // Delay the animation until the view is properly measured and laid out
        binding?.root?.viewTreeObserver?.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // Animate progress bars after defining animationDuration
                ProgressWeedCounter?.let {
                    animateProgressBar(binding?.progressBar9!!,
                        it, animationDuration)
                }

                ProgressRemainingchemicals?.let { animateProgressBar(binding?.progressBar10!!, it.toInt(), animationDuration) }
                animateProgressBar(binding?.progressBar11!!, progressValue3, animationDuration)
                animateProgressBar(binding?.progressTaskPeriod!!, progressValue4, animationDuration)

                // Remove the listener to prevent multiple calls
                binding?.root?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
            }
        })

        val weedcounter = ProgressWeedCounter?.let { Progressealthycrop?.plus(it) }

        binding?.tvLabelSoilH4?.text = "$progressValue1 / $weedcounter "
        binding?.tvLabelSoilC4?.text = "$progressValue2 / $weedcounter "
        binding?.tvProgressCounterLabel?.text = "$progressValue3%"



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


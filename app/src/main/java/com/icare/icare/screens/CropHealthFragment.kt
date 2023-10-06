package com.icare.icare.screens

import android.animation.ValueAnimator

import androidx.appcompat.app.AppCompatActivity

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.icare.icare.R
import com.icare.icare.databinding.FragmentCropHealthBinding
import android.graphics.Color
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.db.williamchart.slidertooltip.SliderTooltip
import kotlinx.android.synthetic.main.fragment_crop_health.*
import com.db.williamchart.ExperimentalFeature
import com.icare.icare.ViewModel.AuthViewModel
import com.icare.icare.backend.ApiService
import com.icare.icare.backend.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CropHealthFragment : BaseFragment() {

    private val authViewModel: AuthViewModel by viewModels()
    private var progressId: Long? = null
    val userId = authViewModel.userId
    val accessToken = authViewModel.accessToken


    private var binding: FragmentCropHealthBinding? = null
        private lateinit var btnToday: Button
        private lateinit var btnMonth: Button
        private lateinit var btnYear: Button
        private val animationDuration = 3000L


    override fun isLoggedin() = true

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentCropHealthBinding.inflate(inflater, container, false)
            return binding?.root

        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)


            val progressApi = RetrofitInstance.createProgressApi()
            val summaryApi = RetrofitInstance.createSummaryApi()

            val apiService = ApiService(accessToken, "BASE_URL")

            val userId = authViewModel.userId // Get the user ID from your ViewModel

            if (userId != null) {
                // Create a Retrofit instance for your ProgressApi
                val progressApi = RetrofitInstance.createProgressApi()

                // Make the API request
                progressApi.getProgressIdCurrentTime(userId).enqueue(object : Callback<Long> {
                    override fun onResponse(call: Call<Long>, response: Response<Long>) {
                        if (response.isSuccessful) {
                            progressId = response.body() // Assign the value to the class-level property
                        } else {
                            // Handle the error response...
                        }
                    }
                    override fun onFailure(call: Call<Long>, t: Throwable) {
                        // Handle the failure...
                    }
                })
            } else {
                // Handle the case where user ID is not available
            }



            val progressValue1 = 77 // Set progress value for ProgressBar 1
            val progressValue2 = 50 // Set progress value for ProgressBar 2
            val progressValue3 = 25 // Set progress value for ProgressBar 3

            binding?.progressBar6?.progress = progressValue1
            binding?.progressBar7?.progress = progressValue2
            binding?.progressBar8?.progress = progressValue3

            binding?.tvLabelSoilH3?.text = "$progressValue1%"
            binding?.tvLabelSoilT3?.text = "$progressValue2%"
            binding?.tvLabelSoilC3?.text = "$progressValue3%"




            progressApi.getCropHealthy(progressId).enqueue(object : Callback<Int> {
                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    if (response.isSuccessful) {
                        val Humidity = response.body() // Get the temperature from the response
                        // Update the TextView with the new value
                        binding?.tvHealthCValue2?.text = "$Humidity %"



                    } else {
                        binding?.tvHealthCValue2?.text = "ERROR"
                    }
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {
                    binding?.tvHealthCValue2?.text = "16"

                }
            })

            progressApi.getCropEarlyBlight(progressId).enqueue(object : Callback<Int> {
                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    if (response.isSuccessful) {
                        val Humidity = response.body() // Get the temperature from the response
                        // Update the TextView with the new value
                        binding?.tvEarlyCValue?.text = "$Humidity %"



                    } else {
                        binding?.tvEarlyCValue?.text = "ERROR"
                    }
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {
                    binding?.tvEarlyCValue?.text = "9"

                }
            })

            progressApi.getCropLight(progressId).enqueue(object : Callback<Int> {
                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    if (response.isSuccessful) {
                        val Humidity = response.body() // Get the temperature from the response
                        // Update the TextView with the new value
                        binding?.tvLateCValue2?.text = "$Humidity %"



                    } else {
                        binding?.tvLateCValue2?.text = "ERROR"
                    }
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {
                    binding?.tvLateCValue2?.text = "7"

                }
            })

            // Initialize buttons
            btnToday = view.findViewById(R.id.bt_today)
            btnMonth = view.findViewById(R.id.btn_month)
            btnYear = view.findViewById(R.id.btn_year)

            // Set click listeners for the buttons

            btnToday.setOnClickListener { onTodayButtonClicked(apiService, progressId) }
            btnMonth.setOnClickListener { onMonthButtonClicked(apiService, progressId) }
            btnYear.setOnClickListener { onYearButtonClicked(apiService, progressId) }


                binding?.viewToolbar?.ivMenu?.visibility = View.VISIBLE
            binding?.viewToolbar?.ivMenu?.setOnClickListener {
                toggleSideMenu(true)
            }

            // Animate progress bars after defining animationDuration
            animateProgressBar(binding?.progressBar6!!, progressValue1, animationDuration)
            animateProgressBar(binding?.progressBar7!!, progressValue2, animationDuration)
            animateProgressBar(binding?.progressBar8!!, progressValue3, animationDuration)
        }

        override fun onDestroyView() {
            super.onDestroyView()
            binding = null
        }
    private fun onTodayButtonClicked(apiService: ApiService, progressId: Long?) {

        // Create a Retrofit instance for your ProgressController
        val progressApi = RetrofitInstance.createProgressApi()
        val summaryApi = RetrofitInstance.createSummaryApi()

        summaryApi.getAverageTodayCropHealthy(userId).enqueue(object : Callback<Double> {
            override fun onResponse(call: Call<Double>, response: Response<Double>) {
                if (response.isSuccessful) {
                    val temperature = response.body()?.toFloat() ?: 0F
                    binding?.tvLabelSoilH3?.text = "$temperature "
                    animateProgressBar(binding?.progressBar6!!, temperature.toInt(), animationDuration)
                } else {
                    binding?.tvLabelSoilH3?.text = "ERROR"
                }
            }

            override fun onFailure(call: Call<Double>, t: Throwable) {
                binding?.tvLabelSoilH3?.text = "23"
                val temperature =23
                animateProgressBar(binding?.progressBar6!!, temperature, animationDuration)

            }
        })


        summaryApi.getAverageTodayLightCrop(userId).enqueue(object : Callback<Double> {
            override fun onResponse(call: Call<Double>, response: Response<Double>) {
                if (response.isSuccessful) {
                    val temperature = response.body()?.toFloat() ?: 0F
                    binding?.tvLabelSoilT3?.text = "$temperature "
                    animateProgressBar(binding?.progressBar7!!, temperature.toInt(), animationDuration)
                } else {
                    binding?.tvLabelSoilT3?.text = "ERROR"
                }
            }

            override fun onFailure(call: Call<Double>, t: Throwable) {
                binding?.tvLabelSoilT3?.text = "34"
                val temperature =34
                animateProgressBar(binding?.progressBar7!!, temperature, animationDuration)

            }
        })

        summaryApi.getAverageTodayCropEarlyBlight(userId).enqueue(object : Callback<Double> {
            override fun onResponse(call: Call<Double>, response: Response<Double>) {
                if (response.isSuccessful) {
                    val temperature = response.body()?.toFloat() ?: 0F
                    binding?.tvLabelSoilC3?.text = "$temperature "
                    animateProgressBar(binding?.progressBar8!!, temperature.toInt(), animationDuration)
                } else {
                    binding?.tvLabelSoilC3?.text = "ERROR"
                }
            }

            override fun onFailure(call: Call<Double>, t: Throwable) {
                binding?.tvLabelSoilC3?.text = "34"
                val temperature =34
                animateProgressBar(binding?.progressBar8!!, temperature, animationDuration)

            }
        })

    }

    private fun onMonthButtonClicked(apiService: ApiService, progressId: Long?) {

        // Create a Retrofit instance for your ProgressController
        val progressApi = RetrofitInstance.createProgressApi()
        val summaryApi = RetrofitInstance.createSummaryApi()

        summaryApi.getMonthCropHealthy(userId).enqueue(object : Callback<Double> {
            override fun onResponse(call: Call<Double>, response: Response<Double>) {
                if (response.isSuccessful) {
                    val temperature = response.body()?.toFloat() ?: 0F
                    binding?.tvLabelSoilH3?.text = "$temperature "
                    animateProgressBar(binding?.progressBar6!!, temperature.toInt(), animationDuration)
                } else {
                    binding?.tvLabelSoilH3?.text = "ERROR"
                }
            }

            override fun onFailure(call: Call<Double>, t: Throwable) {
                binding?.tvLabelSoilH3?.text = "23"
                val temperature =23
                animateProgressBar(binding?.progressBar6!!, temperature, animationDuration)

            }
        })


        summaryApi.getAverageThisMonthLightCrop(userId).enqueue(object : Callback<Double> {
            override fun onResponse(call: Call<Double>, response: Response<Double>) {
                if (response.isSuccessful) {
                    val temperature = response.body()?.toFloat() ?: 0F
                    binding?.tvLabelSoilT3?.text = "$temperature "
                    animateProgressBar(binding?.progressBar7!!, temperature.toInt(), animationDuration)
                } else {
                    binding?.tvLabelSoilT3?.text = "ERROR"
                }
            }

            override fun onFailure(call: Call<Double>, t: Throwable) {
                binding?.tvLabelSoilT3?.text = "34"
                val temperature =34
                animateProgressBar(binding?.progressBar7!!, temperature, animationDuration)

            }
        })

        summaryApi.getAverageThisMonthCropEarlyBlight(userId).enqueue(object : Callback<Double> {
            override fun onResponse(call: Call<Double>, response: Response<Double>) {
                if (response.isSuccessful) {
                    val temperature = response.body()?.toFloat() ?: 0F
                    binding?.tvLabelSoilC3?.text = "$temperature "
                    animateProgressBar(binding?.progressBar8!!, temperature.toInt(), animationDuration)
                } else {
                    binding?.tvLabelSoilC3?.text = "ERROR"
                }
            }

            override fun onFailure(call: Call<Double>, t: Throwable) {
                binding?.tvLabelSoilC3?.text = "41"
                val temperature =41
                animateProgressBar(binding?.progressBar8!!, temperature, animationDuration)

            }
        })

    }

    private fun onYearButtonClicked(apiService: ApiService, progressId: Long?) {

        // Create a Retrofit instance for your ProgressController
        val progressApi = RetrofitInstance.createProgressApi()
        val summaryApi = RetrofitInstance.createSummaryApi()

        summaryApi.getAverageYearCropHealthy(userId).enqueue(object : Callback<Double> {
            override fun onResponse(call: Call<Double>, response: Response<Double>) {
                if (response.isSuccessful) {
                    val temperature = response.body()?.toFloat() ?: 0F
                    binding?.tvLabelSoilH3?.text = "$temperature "
                    animateProgressBar(binding?.progressBar6!!, temperature.toInt(), animationDuration)
                } else {
                    binding?.tvLabelSoilH3?.text = "ERROR"
                }
            }

            override fun onFailure(call: Call<Double>, t: Throwable) {
                binding?.tvLabelSoilH3?.text = "23"
                val temperature =23
                animateProgressBar(binding?.progressBar6!!, temperature, animationDuration)

            }
        })


        summaryApi.getAverageThisYearLightCrop(userId).enqueue(object : Callback<Double> {
            override fun onResponse(call: Call<Double>, response: Response<Double>) {
                if (response.isSuccessful) {
                    val temperature = response.body()?.toFloat() ?: 0F
                    binding?.tvLabelSoilT3?.text = "$temperature "
                    animateProgressBar(binding?.progressBar7!!, temperature.toInt(), animationDuration)
                } else {
                    binding?.tvLabelSoilT3?.text = "ERROR"
                }
            }

            override fun onFailure(call: Call<Double>, t: Throwable) {
                binding?.tvLabelSoilT3?.text = "34"
                val temperature =34
                animateProgressBar(binding?.progressBar7!!, temperature, animationDuration)

            }
        })

        summaryApi.getAverageThisYearCropEarlyBlight(userId).enqueue(object : Callback<Double> {
            override fun onResponse(call: Call<Double>, response: Response<Double>) {
                if (response.isSuccessful) {
                    val temperature = response.body()?.toFloat() ?: 0F
                    binding?.tvLabelSoilC3?.text = "$temperature "
                    animateProgressBar(binding?.progressBar8!!, temperature.toInt(), animationDuration)
                } else {
                    binding?.tvLabelSoilC3?.text = "ERROR"
                }
            }

            override fun onFailure(call: Call<Double>, t: Throwable) {
                binding?.tvLabelSoilC3?.text = "34"
                val temperature =64
                animateProgressBar(binding?.progressBar8!!, temperature, animationDuration)

            }
        })
    }
    private fun animateProgressBar(progressBar: ProgressBar, targetProgress: Int, duration: Long) {
        val animator = ValueAnimator.ofInt(0, targetProgress)
        animator.duration = duration
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Int
            progressBar.progress = progress
        }
        animator.start()
    }


        private fun resetButtonBackgroundColors() {
            // Reset the background color (tint) for all buttons to the original color
            btnToday.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green1))
            btnMonth.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green1))
            btnYear.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green1))
        }


    }

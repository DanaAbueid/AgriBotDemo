package com.icare.icare.screens

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.icare.icare.R
import com.icare.icare.ViewModel.AuthViewModel
import com.icare.icare.backend.ApiService
import com.icare.icare.backend.ProgressApi
import com.icare.icare.backend.RetrofitInstance
import com.icare.icare.databinding.FragmentProgressBinding
import com.icare.icare.databinding.FragmentSoilBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SoilFragment : BaseFragment() {

    private val authViewModel: AuthViewModel by viewModels()
    private var binding: FragmentSoilBinding? = null

    private var progressId: Long? = null
    val userId = authViewModel.userId

    private var Averagetemperature: Double? = null
    private var AverageHumidity: Double? = null

    private lateinit var btnToday: Button
    private lateinit var btnMonth: Button
    private lateinit var btnYear: Button

    private val animationDuration = 2000L
    override fun isLoggedin() = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSoilBinding.inflate(inflater, container, false)
        return binding?.root
        val accessToken = authViewModel.accessToken

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding?.viewToolbar?.ivMenu?.visibility = View.VISIBLE
        binding?.viewToolbar?.ivMenu?.setOnClickListener {
            toggleSideMenu(true)
        }


        val summaryApi = RetrofitInstance.createSummaryApi()

        // Inside onViewCreated method of ProgressFragment
        val accessToken = authViewModel.accessToken
        val apiService = ApiService(accessToken, "BASE_URL")
        val progressApi = apiService.retrofit.create(ProgressApi::class.java)

        val userId = authViewModel.userId // Get the user ID from your ViewModel

        if (userId != null) {

            // Make the API request
            progressApi.getProgressIdCurrentTime(userId).enqueue(object : Callback<Long> {
                override fun onResponse(call: Call<Long>, response: Response<Long>) {
                    if (response.isSuccessful) {
                        progressId = response.body() // Assign the value to the class-level property
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




        progressApi.getSoilHealth(progressId).enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful) {
                    val CropHealthy = response.body()

                    if (CropHealthy == true) {
                        binding?.tvSoilH?.text = "Good "
                    }else {
                        binding?.tvSoilH?.text = "Poor " }

                } else {
                    binding?.tvSoilH?.text = "Good!"
                    val errorMessage = "ERROR Please try again later."
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                binding?.tvSoilH?.text = "Good"
                val errorMessage = "ERROR Please try again later."
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        })


        val progressValue1 = 61 // Set progress value for ProgressBar 1
        val progressValue2 = 23 // Set progress value for ProgressBar 2
        val progressValue3 = 65
        val progressValue4 = 14 // Set progress value for ProgressBar 3
        binding?.tvLabelSoilH7?.text = "$progressValue3%"
        binding?.tvLabelSoilT7?.text = "$progressValue4°"


        // Fetch data from the API for each TextView and update them
        progressApi.getSoilTemperature(progressId).enqueue(object : Callback<Double> {
            override fun onResponse(call: Call<Double>, response: Response<Double>) {
                if (response.isSuccessful) {
                    val temperature = response.body() // Get the temperature from the response
                    // Update the TextView with the new value
                    binding?.tvTempValue?.text = "$temperature °"

                } else {
                    binding?.tvTempValue?.text = "ERROR"
                }
            }

            override fun onFailure(call: Call<Double>, t: Throwable) {
                binding?.tvTempValue?.text = "20°"
                val errorMessage = "ERROR Please try again later."
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()

            }
        })


        progressApi.getSoilHumidity(progressId).enqueue(object : Callback<Double> {
            override fun onResponse(call: Call<Double>, response: Response<Double>) {
                if (response.isSuccessful) {
                    val Humidity = response.body() // Get the temperature from the response
                    // Update the TextView with the new value
                    binding?.tvHumValue?.text = "$Humidity %"



                } else {
                    binding?.tvHumValue?.text = "ERROR"
                    val errorMessage = "ERROR Please try again later."
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Double>, t: Throwable) {
                binding?.tvHumValue?.text = "61%"
                val errorMessage = "ERROR Please try again later."
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()

            }
        })


        // Initialize buttons
        btnToday = view.findViewById(R.id.bt_today)
        btnMonth = view.findViewById(R.id.btn_month)
        btnYear = view.findViewById(R.id.btn_year)

        // Set click listeners for the buttons
        btnToday.setOnClickListener { onTodayButtonClicked() }
        btnMonth.setOnClickListener { onMonthButtonClicked() }
        btnYear.setOnClickListener { onYearButtonClicked() }


        // Animate progress bars after defining animationDuration

        animateProgressBar(binding?.progressBar18!!, progressValue1, animationDuration)
        animateProgressBar(binding?.progressBar19!!, progressValue2, animationDuration)

    }


    private fun onButtonClicked(clickedButton: Button) {
        resetButtonBackgroundColors()

        clickedButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green3))
        when (clickedButton.id) {
            R.id.bt_today -> {
                resetButtonBackgroundColors()            }
            R.id.btn_month -> {
                resetButtonBackgroundColors()            }
            R.id.btn_year -> {
                resetButtonBackgroundColors()            }
        }
    }

    private fun resetButtonBackgroundColors() {
        // Reset the background color (tint) for all buttons to the original color
        btnToday.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green1))
        btnMonth.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green1))
        btnYear.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green1))
    }
    private fun onTodayButtonClicked() {

        val progressApi = RetrofitInstance.createProgressApi()
        val summaryApi = RetrofitInstance.createSummaryApi()

        summaryApi.getAverageTodayTemperature(userId).enqueue(object : Callback<Double> {
            override fun onResponse(call: Call<Double>, response: Response<Double>) {
                if (response.isSuccessful) {
                    Averagetemperature = response.body()
                    binding?.tvLabelSoilT7?.text = "$Averagetemperature °"
                    Averagetemperature?.let { animateProgressBar(binding?.progressBar19!!, it.toInt(), animationDuration) }
                } else {
                    binding?.tvLabelSoilT7?.text = "ERROR"
                }
            }

            override fun onFailure(call: Call<Double>, t: Throwable) {
                val errorMessage = "ERROR Please try again later."
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                binding?.tvLabelSoilT7?.text = "23°"
                val temperature =23
                animateProgressBar(binding?.progressBar19!!, temperature, animationDuration)

            }
        })


        summaryApi.getAverageTodayHumidity(userId).enqueue(object : Callback<Double> {
            override fun onResponse(call: Call<Double>, response: Response<Double>) {
                if (response.isSuccessful) {
                    AverageHumidity = response.body()
                    binding?.tvLabelSoilH7?.text = "$AverageHumidity "
                    AverageHumidity?.let { animateProgressBar(binding?.progressBar18!!, it.toInt(), animationDuration) }
                } else {
                    binding?.tvLabelSoilH7?.text = "ERROR"
                }
            }

            override fun onFailure(call: Call<Double>, t: Throwable) {
                val errorMessage = "ERROR Please try again later."
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                binding?.tvLabelSoilH7?.text = "66%"
                val temperature =66
                animateProgressBar(binding?.progressBar18!!, temperature, animationDuration)

            }
        })

    }

    private fun onMonthButtonClicked() {


        // Create a Retrofit instance for your ProgressController
        val progressApi = RetrofitInstance.createProgressApi()
        val summaryApi = RetrofitInstance.createSummaryApi()

        summaryApi.getAverageThisMonthTemperature(userId).enqueue(object : Callback<Double> {
            override fun onResponse(call: Call<Double>, response: Response<Double>) {
                if (response.isSuccessful) {
                    Averagetemperature = response.body()
                    binding?.tvLabelSoilT7?.text = "$Averagetemperature °"
                    Averagetemperature?.let { animateProgressBar(binding?.progressBar19!!, it.toInt(), animationDuration) }
                } else {
                    binding?.tvLabelSoilT7?.text = "ERROR"
                }
            }

            override fun onFailure(call: Call<Double>, t: Throwable) {
                binding?.tvLabelSoilT7?.text = "22°"
                val temperature =22
                animateProgressBar(binding?.progressBar19!!, temperature, animationDuration)

            }
        })


        summaryApi.getAverageThisMonthHumidity(userId).enqueue(object : Callback<Double> {
            override fun onResponse(call: Call<Double>, response: Response<Double>) {
                if (response.isSuccessful) {
                     AverageHumidity = response.body()
                    binding?.tvLabelSoilH7?.text = "$AverageHumidity %"
                    AverageHumidity?.let { animateProgressBar(binding?.progressBar18!!, it.toInt(), animationDuration) }
                } else {
                    binding?.tvLabelSoilH7?.text = "ERROR"
                }
            }

            override fun onFailure(call: Call<Double>, t: Throwable) {
                binding?.tvLabelSoilH7?.text = "51%"
                val temperature =51
                animateProgressBar(binding?.progressBar18!!, temperature, animationDuration)

            }
        })

    }

    private fun onYearButtonClicked() {

        val progressApi = RetrofitInstance.createProgressApi()
        val summaryApi = RetrofitInstance.createSummaryApi()

        summaryApi.getAverageThisYearTemperature(userId).enqueue(object : Callback<Double> {
            override fun onResponse(call: Call<Double>, response: Response<Double>) {
                if (response.isSuccessful) {
                    Averagetemperature = response.body()
                    binding?.tvLabelSoilT7?.text = "$Averagetemperature °"
                    Averagetemperature?.let { animateProgressBar(binding?.progressBar19!!, it.toInt(), animationDuration) }
                } else {
                    binding?.tvLabelSoilT7?.text = "ERROR"
                }
            }

            override fun onFailure(call: Call<Double>, t: Throwable) {
                binding?.tvLabelSoilT7?.text = "19°"
                val temperature =19
                animateProgressBar(binding?.progressBar19!!, temperature, animationDuration)
            }
        })


        summaryApi.getAverageThisYearHumidity(userId).enqueue(object : Callback<Double> {
            override fun onResponse(call: Call<Double>, response: Response<Double>) {
                if (response.isSuccessful) {
                    AverageHumidity = response.body()
                    binding?.tvLabelSoilH7?.text = "$AverageHumidity %"
                    AverageHumidity?.let { animateProgressBar(binding?.progressBar18!!, it.toInt(), animationDuration) }
                } else {
                    binding?.tvLabelSoilH7?.text = "ERROR"
                }
            }

            override fun onFailure(call: Call<Double>, t: Throwable) {
                binding?.tvLabelSoilH7?.text = "53%"
                val temperature =53
                animateProgressBar(binding?.progressBar18!!, temperature, animationDuration)

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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

package com.icare.icare.screens

import android.animation.ValueAnimator

import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.fragment_crop_health.barChart
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
import com.db.williamchart.slidertooltip.SliderTooltip
import kotlinx.android.synthetic.main.fragment_crop_health.*
import com.db.williamchart.ExperimentalFeature


class CropHealthFragment : BaseFragment() {


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

            val progressValue1 = 77 // Set progress value for ProgressBar 1
            val progressValue2 = 50 // Set progress value for ProgressBar 2
            val progressValue3 = 25 // Set progress value for ProgressBar 3

            binding?.progressBar1?.progress = progressValue1
            binding?.progressBar2?.progress = progressValue2
            binding?.progressBar3?.progress = progressValue3

            binding?.tvLabelSoilH?.text = "$progressValue1%"
            binding?.tvLabelSoilT?.text = "$progressValue2%"
            binding?.tvLabelSoilC?.text = "$progressValue3%"

            // Define your animationDuration and barSet here
            val barSet = listOf(
                "Healthy Crop" to 4F,  // Combine "Item 1A" and "Item 1B" into one label
                "Late Blight crop" to 7F,
                "Early Blight crop" to 2F,
            )

            barChart.animation.duration = animationDuration
            barChart.animate(barSet)

            // Initialize buttons
            btnToday = view.findViewById(R.id.bt_today)
            btnMonth = view.findViewById(R.id.btn_month)
            btnYear = view.findViewById(R.id.btn_year)

            // Set click listeners for the buttons
            btnToday.setOnClickListener { onButtonClicked(btnToday) }
            btnMonth.setOnClickListener { onButtonClicked(btnMonth) }
            btnYear.setOnClickListener { onButtonClicked(btnYear) }

            binding?.viewToolbar?.ivMenu?.visibility = View.VISIBLE
            binding?.viewToolbar?.ivMenu?.setOnClickListener {
                toggleSideMenu(true)
            }

            // Animate progress bars after defining animationDuration
            animateProgressBar(binding?.progressBar1!!, progressValue1, animationDuration)
            animateProgressBar(binding?.progressBar2!!, progressValue2, animationDuration)
            animateProgressBar(binding?.progressBar3!!, progressValue3, animationDuration)
        }

        override fun onDestroyView() {
            super.onDestroyView()
            binding = null
        }

        private fun onButtonClicked(clickedButton: Button) {
            // Reset the background color (tint) for all buttons
            resetButtonBackgroundColors()

            // Set a different background color (tint) for the clicked button
            clickedButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green3))

            // Handle the button's click action here
            when (clickedButton.id) {
                R.id.bt_today -> {
                    // Handle click for "Today" button
                }
                R.id.btn_month -> {
                    // Handle click for "This Month" button
                }
                R.id.btn_year -> {
                    // Handle click for "This Year" button
                }
            }
        }

        private fun resetButtonBackgroundColors() {
            // Reset the background color (tint) for all buttons to the original color
            btnToday.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green1))
            btnMonth.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green1))
            btnYear.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green1))
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
    }

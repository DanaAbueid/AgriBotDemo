
package com.icare.icare.screens

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import androidx.navigation.fragment.findNavController
import com.icare.icare.R
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
        val progressValue1 = 77 // Set progress value for ProgressBar 1
        val progressValue2 = 57 // Set progress value for ProgressBar 2
        val progressValue3 = 12 // Set progress value for ProgressBar 3
        val progressValue4 = 78 // Set progress value for ProgressBar 3

        binding?.progressBar9?.progress = progressValue1
        binding?.progressBar10?.progress = progressValue2
        binding?.progressBar11?.progress = progressValue3
        binding?.progressTaskPeriod?.progress = progressValue4

        binding?.tvLabelSoilH4?.text = "$progressValue1%"
        binding?.tvLabelSoilT4?.text = "$progressValue2%"
        binding?.tvLabelSoilC4?.text = "$progressValue3%"
        binding?.tvProgressCounterLabel?.text = "$progressValue4%"

        binding?.viewToolbar?.ivMenu?.visibility = View.VISIBLE
        binding?.viewToolbar?.ivMenu?.setOnClickListener {
            toggleSideMenu(true)
        }
        // Animate progress bars after defining animationDuration
        animateProgressBar(binding?.progressBar9!!, progressValue1, animationDuration)
        animateProgressBar(binding?.progressBar10!!, progressValue2, animationDuration)
        animateProgressBar(binding?.progressBar11!!, progressValue3, animationDuration)
        animateProgressBar(binding?.progressTaskPeriod!!, progressValue4, animationDuration)



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
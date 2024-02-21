package com.icare.icare.screens

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.icare.icare.databinding.FragmentValedationEmailBinding

class ValedationEmailFragment : BaseFragment() {
    private var binding: FragmentValedationEmailBinding? = null
    private var countdownTimer: CountDownTimer? = null
    private var isTimerRunning = false

    override fun isLoggedin(): Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentValedationEmailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { bindingNotNull ->

            bindingNotNull.buttonSendEmail.setOnClickListener {
                findNavController().navigate(ValedationEmailFragmentDirections.actionValidationToConfirm())
            }

            // Initially, make tv_resend_code not clickable
            bindingNotNull.tvResendCode.isClickable = false

            // Start the countdown timer automatically when the fragment is reached
            startCountdown(5 * 60 * 1000)

            // Set a click listener for your trigger element (tv_resenr_code)
            bindingNotNull.tvResendCode.setOnClickListener {
                if (!isTimerRunning) {
                    startCountdown(5 * 60 * 1000) // Start a 5-minute countdown
                    bindingNotNull.tvResendCode.text = "Send Code"
                    bindingNotNull.tvResendCode.isClickable = false // Make it not clickable
                } else {
                    // Timer is running, cancel it and start a new one
                    countdownTimer?.cancel()
                    startCountdown(5 * 60 * 1000)
                    bindingNotNull.tvResendCode.text = "Send Code"
                    bindingNotNull.tvResendCode.isClickable = false // Make it not clickable
                }
            }
        }
    }

    private fun startCountdown(milliseconds: Long) {
        countdownTimer = object : CountDownTimer(milliseconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 60000
                val seconds = (millisUntilFinished % 60000) / 1000
                binding?.textClock?.text = String.format("%02d:%02d", minutes, seconds)
                binding?.textClock?.visibility = View.VISIBLE // Reappear the countdown
            }

            override fun onFinish() {
                // Countdown is finished
                binding?.textClock?.text = ""
                binding?.textClock?.visibility = View.GONE
                binding?.tvResendCode?.text = "Resend Now!" // Change the text
                binding?.tvResendCode?.isClickable = true // Make it clickable
                isTimerRunning = false
            }
        }

        countdownTimer?.start()
        isTimerRunning = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Cancel the countdown timer when the fragment is destroyed
        countdownTimer?.cancel()
    }
}

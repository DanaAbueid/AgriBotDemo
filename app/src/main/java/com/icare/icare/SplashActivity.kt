package com.icare.icare

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.icare.icare.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        goToMainActivityDelayed()
    }
    private fun goToMainActivityDelayed() {
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }, 2000)
    }
}
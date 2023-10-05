package com.icare.icare.models

import com.icare.icare.enumclass.Role
import com.icare.icare.models.Robot

import java.time.LocalDateTime

data class RegisterRequest(
   val robot: Robot,
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val location: String,
    val accountStatus: Boolean,
    val role: Role,
    val otp: String,

    )
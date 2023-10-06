package com.icare.icare.ViewModel

import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    var accessToken: String? = null
    var userId: Long? = null
}
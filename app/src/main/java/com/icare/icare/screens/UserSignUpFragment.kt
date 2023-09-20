package com.icare.icare.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.icare.icare.R
import com.icare.icare.databinding.FragmentUserSignUpBinding
class UserSignUpFragment : BaseFragment() {

    private var binding: FragmentUserSignUpBinding? = null

    override fun isLoggedin(): Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserSignUpBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { bindingNotNull ->
            bindingNotNull.buttonLogin2.setOnClickListener {
                if (validateFields()) {
                    // All fields are filled, proceed to the next step
                    findNavController().navigate(UserSignUpFragmentDirections.actionSignupToCode())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun validateFields(): Boolean {
        var isValid = true

        val email = binding?.editTextEmail?.text.toString().trim()
        val username = binding?.editTextUsername?.text.toString().trim()
        val password = binding?.editTextPassword?.text.toString().trim()
        val phoneNumber = binding?.editTextPhoneNumber?.text.toString().trim()
        val name = binding?.editTextName?.text.toString().trim()

        if (email.isEmpty()) {
            binding?.editTextEmail?.error = "Email is required"
            isValid = false
        }

        if (username.isEmpty()) {
            binding?.editTextUsername?.error = "Username is required"
            isValid = false
        }

        if (password.isEmpty()) {
            binding?.editTextPassword?.error = "Password is required"
            isValid = false
        }

        if (phoneNumber.isEmpty()) {
            binding?.editTextPhoneNumber?.error = "Phone number is required"
            isValid = false
        }

        if (name.isEmpty()) {
            binding?.editTextName?.error = "Name is required"
            isValid = false
        }

        return isValid
    }
}


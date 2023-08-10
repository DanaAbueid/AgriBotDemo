package com.icare.icare.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.icare.icare.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment() {
    private var binding: FragmentLoginBinding? = null
    override fun isLoggedin(): Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { bindingNotNull ->

            bindingNotNull.tvCreateAccount.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupUpUserFragment())
            }
            bindingNotNull.tvForgotPassword.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgotpassword())
            }
            bindingNotNull.tvAdminLogin.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToTest())
            }

            bindingNotNull.buttonLogin.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToUserNutritionCentersFragment())
            }
        }
    }
}

package com.icare.icare.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.icare.icare.databinding.FragmentLoginNewBinding

class LoginNewFragment : BaseFragment() {
    private var binding: FragmentLoginNewBinding? = null
    override fun isLoggedin(): Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginNewBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { bindingNotNull ->


            bindingNotNull.tvForgotPassword2.setOnClickListener {
                findNavController().navigate(LoginNewFragmentDirections.actionLoginToForpsw())
            }

            bindingNotNull.tvLoginBtn2.setOnClickListener {
                findNavController().navigate(LoginNewFragmentDirections.actionLoginToDashboard())
            }
            bindingNotNull.tvForgotPassword2.setOnClickListener {
                findNavController().navigate(LoginNewFragmentDirections.actionLoginToForpsw())
            }
        }
    }
}

package com.icare.icare.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.icare.icare.databinding.FragmentWelcomePageBinding

class WelcomePageFragment : BaseFragment() {
    private var binding: FragmentWelcomePageBinding? = null
    override fun isLoggedin(): Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomePageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { bindingNotNull ->

            bindingNotNull.tvLoginBtn.setOnClickListener {
                findNavController().navigate(WelcomePageFragmentDirections.actionWelcomeToLogin())
            }
            bindingNotNull.tvSignupBtn.setOnClickListener {
                findNavController().navigate(WelcomePageFragmentDirections.actionWelcomeToSignup())
            }
            bindingNotNull.btnAdminLogin.setOnClickListener {
                findNavController().navigate(WelcomePageFragmentDirections.actionWelcomeToAdmin())
            }

        }
    }
}
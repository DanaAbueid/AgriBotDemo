


package com.icare.icare.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.icare.icare.R
import com.icare.icare.databinding.FragmentConfirmNewPaymentBinding


class ConfirmNewPaymentFragment : BaseFragment() {

    private var binding: FragmentConfirmNewPaymentBinding? = null

    override fun isLoggedin(): Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmNewPaymentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbar?.ivMenu?.visibility = View.VISIBLE
        binding?.toolbar?.ivMenu?.setOnClickListener {
            toggleSideMenu(true)
        }

        binding?.let { bindingNotNull ->
            bindingNotNull.tvButtonNextMysub.setOnClickListener {
                findNavController().navigate(ConfirmNewPaymentFragmentDirections.actionNewConfirmToSublist())
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
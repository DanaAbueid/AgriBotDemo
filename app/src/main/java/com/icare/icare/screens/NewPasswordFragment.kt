package com.icare.icare.screens
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.icare.icare.R
import com.icare.icare.databinding.FragmentNewPasswordBinding


class NewPasswordFragment : BaseFragment() {

    private var binding: FragmentNewPasswordBinding? = null

    override fun isLoggedin(): Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewPasswordBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbar?.ivMenu?.visibility = View.VISIBLE
        binding?.toolbar?.ivMenu?.setOnClickListener {
            toggleSideMenu(true)
        }

        binding?.let { bindingNotNull ->
            bindingNotNull.buttonSendNewPwd.setOnClickListener {
                findNavController().navigate(NewPasswordFragmentDirections.actionNewConfirmToSettings())
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

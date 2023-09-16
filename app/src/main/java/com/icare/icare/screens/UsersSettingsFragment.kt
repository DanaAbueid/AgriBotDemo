
package com.icare.icare.screens
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.icare.icare.R
import com.icare.icare.databinding.FragmentUsersSettingsBinding


class UsersSettingsFragment : BaseFragment() {

    private var binding: FragmentUsersSettingsBinding? = null

    override fun isLoggedin(): Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUsersSettingsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewToolbar?.ivMenu?.visibility = View.VISIBLE
        binding?.viewToolbar?.ivMenu?.setOnClickListener {
            toggleSideMenu(true)
        }

        binding?.let { bindingNotNull ->
            bindingNotNull.button.setOnClickListener {
                findNavController().navigate(UsersSettingsFragmentDirections.actionSettingsToMyinfo())
            }
            bindingNotNull.button2.setOnClickListener {
                findNavController().navigate(UsersSettingsFragmentDirections.actionSettingsToSubinfo())
            }
            bindingNotNull.button3.setOnClickListener {
                findNavController().navigate(UsersSettingsFragmentDirections.actionSettingsToChangepsw())
            }
            bindingNotNull.button7.setOnClickListener {
                findNavController().navigate(UsersSettingsFragmentDirections.actionSettingsToReport())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}


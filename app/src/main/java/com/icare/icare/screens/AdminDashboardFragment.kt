
package com.icare.icare.screens
import com.icare.icare.screens.UserNotificationsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.icare.icare.R
import com.icare.icare.databinding.FragmentAdminDashboardBinding
import com.icare.icare.models.Notifications
import com.icare.icare.screens.BaseFragment


class AdminDashboardFragment : BaseFragment() {

    private var binding: FragmentAdminDashboardBinding? = null

    override fun isLoggedin(): Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminDashboardBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val UserNotificationsAdapter = activity?.let {
            binding?.rvUserNotifications?.let { it1 ->
                UserNotificationsAdapter(
                    it1, it
                )
            }
        }
        val NotificationsList = listOf<Notifications>(
            Notifications("New Report Was just add please check it out",
                R.drawable.list_alt),
            Notifications("New Report Was just add please check it out",
                R.drawable.list_alt),
            Notifications("New Report Was just add please check it out",
                R.drawable.list_alt),
            Notifications("New Report Was just add please check it out",
                R.drawable.list_alt),
            Notifications("New Report Was just add please check it out",
                R.drawable.list_alt),
            Notifications("New Report Was just add please check it out",
                R.drawable.list_alt),
        )
        val dividerItemDecoration = DividerItemDecoration(
            binding?.rvUserNotifications?.context,
            RecyclerView.VERTICAL
        )
        context?.let {
            ContextCompat.getDrawable(it, R.drawable.divider)
                ?.let { dividerItemDecoration.setDrawable(it) }
        }
        UserNotificationsAdapter?.addItemDecoration(dividerItemDecoration)
        UserNotificationsAdapter?.addAll(NotificationsList)?.refresh()


        binding?.let { bindingNotNull ->


            bindingNotNull.btnToReportList.setOnClickListener {
                findNavController().navigate(AdminDashboardFragmentDirections.actionDashboardToReportList())
            }

        }

    }


}
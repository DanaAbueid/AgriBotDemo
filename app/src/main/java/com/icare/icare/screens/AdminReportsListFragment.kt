
package com.icare.icare.screens

import com.icare.icare.screens.AdminReportListAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.icare.icare.R
import com.icare.icare.databinding.FragmentAdminReportsListBinding
import com.icare.icare.models.AdminReports
import com.icare.icare.screens.BaseFragment

class AdminReportsListFragment : BaseFragment() {
    private var binding: FragmentAdminReportsListBinding? = null

    override fun isLoggedin(): Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminReportsListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewToolbar?.ivMenu?.visibility = View.VISIBLE
        binding?.viewToolbar?.ivMenu?.setOnClickListener {
            toggleSideMenu(true)
        }


        val adminReportListAdapter = activity?.let {
            binding?.rvAdminReportList?.let { it1 ->
                AdminReportListAdapter(
                    it1, it
                ) { position ->
                    // Button click listener for each item
                    val action = AdminReportsListFragmentDirections.listToBook(position)
                    findNavController().navigate(action)
                }
            }
        }


        // Add your admin reports to the list
        val adminReportsList = listOf(
            AdminReports("Test 1", "Resolved"),
            AdminReports("Test 2", "Pending"),
            AdminReports("Test 3", "Pending"),
            AdminReports("Test 4", "Resolved"),
            AdminReports("Test 5", "Resolved")
        )

        val dividerItemDecoration = DividerItemDecoration(
            binding?.rvAdminReportList?.context,
            RecyclerView.VERTICAL
        )
        context?.let {
            ContextCompat.getDrawable(it, R.drawable.divider)
                ?.let { dividerItemDecoration.setDrawable(it) }
        }

        adminReportListAdapter?.addItemDecoration(dividerItemDecoration)
        adminReportListAdapter?.addAll(adminReportsList)?.refresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}



package com.icare.icare.screens

import com.icare.icare.screens.AdminReportListAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.icare.icare.R
import com.icare.icare.databinding.FragmentAdminReportsListBinding
import com.icare.icare.models.AdminReports
import com.icare.icare.screens.BaseFragment
class AdminReportsListFragment : BaseFragment() {
    private var binding: FragmentAdminReportsListBinding? = null
    private lateinit var adminReportListAdapter: AdminReportListAdapter
    private val adminReports = mutableListOf<AdminReports>()

    private lateinit var checkboxResolved: CheckBox
    private lateinit var checkboxUnresolved: CheckBox
    private lateinit var searchView: SearchView

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

        checkboxResolved = view.findViewById(R.id.checkboxResolved)
        checkboxUnresolved = view.findViewById(R.id.checkboxUnresolved)
        searchView = view.findViewById(R.id.searchView)

        adminReportListAdapter = AdminReportListAdapter(binding?.rvAdminReportList!!, requireContext())

        // Add your admin reports to the list
        adminReports.addAll(
            listOf(
                AdminReports("Test 1",true),
                AdminReports("Test 2",true),
                AdminReports("Test 3",true),
                AdminReports("Test 4",false),
                AdminReports("Test 5",false)
            )
        )

        val dividerItemDecoration = DividerItemDecoration(
            binding?.rvAdminReportList?.context,
            RecyclerView.VERTICAL
        )
        context?.let {
            ContextCompat.getDrawable(it, R.drawable.divider)
                ?.let { dividerItemDecoration.setDrawable(it) }
        }
        binding?.rvAdminReportList?.addItemDecoration(dividerItemDecoration)
        adminReportListAdapter.addAll(adminReports).refresh()

        // Set initial state and listeners for CheckBoxes
        checkboxResolved.isChecked = true
        checkboxUnresolved.isChecked = true

        checkboxResolved.setOnCheckedChangeListener { _, _ ->
            updateFilter()
        }

        checkboxUnresolved.setOnCheckedChangeListener { _, _ ->
            updateFilter()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search query submission if needed
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                updateFilter()
                return true
            }
        })

        // Initialize the filter
        updateFilter()

    }

    private fun updateFilter() {
        val query = searchView.query.toString()
        val resolvedFilter = checkboxResolved.isChecked
        val unresolvedFilter = checkboxUnresolved.isChecked

        val filteredList = adminReports.filter { report ->
            val matchesQuery = report.title.contains(query, true)
            if (resolvedFilter && unresolvedFilter) {
                // Both CheckBoxes are checked, no filtering
                return@filter matchesQuery
            } else if (resolvedFilter) {
                return@filter matchesQuery && report.isResolved
            } else if (unresolvedFilter) {
                return@filter matchesQuery && !report.isResolved
            }
            return@filter false
        }

        adminReportListAdapter.filter(query, resolvedFilter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

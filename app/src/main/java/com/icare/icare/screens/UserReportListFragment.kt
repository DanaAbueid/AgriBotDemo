package com.icare.icare.screens

import com.icare.icare.screens.UserReportsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.icare.icare.R
import com.icare.icare.databinding.FragmentUserReportListBinding
import com.icare.icare.models.UserReports
import com.icare.icare.screens.BaseFragment


class UserReportListFragment : BaseFragment() {
    private var binding: FragmentUserReportListBinding? = null
    override fun isLoggedin(): Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserReportListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewToolbar?.ivMenu?.visibility = View.VISIBLE
        binding?.viewToolbar?.ivMenu?.setOnClickListener {
            toggleSideMenu(true)
        }
        binding?.let { bindingNotNull ->
            bindingNotNull.button10.setOnClickListener {
                findNavController().navigate(UserReportListFragmentDirections.makeToReport())
            }
        }

        val UserReportsAdapter = activity?.let {
            binding?.rvUserReportList?.let { it1 ->
                UserReportsAdapter(
                    it1, it
                ){ position ->
                    val action = UserReportListFragmentDirections.listToReport(position)
                    findNavController().navigate(action)
                }
            }
        }
        val ReportsList = listOf<UserReports>(
            UserReports("Report 1"),
            UserReports("Report 2"),

        )
        val dividerItemDecoration = DividerItemDecoration(
            binding?.rvUserReportList?.context,
            RecyclerView.VERTICAL
        )
        context?.let {
            ContextCompat.getDrawable(it, R.drawable.divider)
                ?.let { dividerItemDecoration.setDrawable(it) }
        }
        UserReportsAdapter?.addItemDecoration(dividerItemDecoration)
        UserReportsAdapter?.addAll(ReportsList)?.refresh()
    }


}
package com.icare.icare.screens

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.icare.icare.databinding.ViewCellAdminReportsBinding
import com.icare.icare.generics.BaseRecyclerViewAdapter
import com.icare.icare.models.AdminReports
import kotlinx.android.synthetic.main.view_cell_admin_reports.view.tv_user

class AdminReportListAdapter(
    recyclerView: RecyclerView,
    val context: Context,
    val onItemClick: (position: Int) -> Unit // Callback function for item click
) : BaseRecyclerViewAdapter<AdminReports, AdminReportListAdapter.ViewHolder>(
    recyclerView
) {


    override fun createViewHolder(
        viewGroup: ViewGroup,
        viewType: Int,
        layoutInflater: LayoutInflater
    ): ViewHolder {
        return ViewHolder(
            ViewCellAdminReportsBinding.inflate(
                layoutInflater,
                viewGroup,
                false
            )
        )
    }



    override fun bindViewHolder(
        viewHolder: ViewHolder,
        position: Int,
        item: AdminReports
    ) {
        with(viewHolder.binding) {
            tvUser.text = item.title
            DeleteReport.text = item.isResolved
            resolveReport2.setOnClickListener {
                onItemClick(position) // Invoke the callback when the button is clicked
            }
        }
    }

    class ViewHolder(val binding: ViewCellAdminReportsBinding) : RecyclerView.ViewHolder(binding.root)
}

package com.icare.icare.screens

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.icare.icare.databinding.ViewCellReportsListBinding
import com.icare.icare.generics.BaseRecyclerViewAdapter
import com.icare.icare.models.AdminReports

class AdminReportListAdapter(
    recyclerView: RecyclerView,
    private val context: Context
) : BaseRecyclerViewAdapter<AdminReports, AdminReportListAdapter.ViewHolder>(recyclerView) {

    private val originalItems: MutableList<AdminReports> = mutableListOf()

    override fun createViewHolder(
        viewGroup: ViewGroup,
        viewType: Int,
        layoutInflater: LayoutInflater
    ): ViewHolder {
        return ViewHolder(
            ViewCellReportsListBinding.inflate(
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
        }
    }

    fun filter(query: String, resolvedFilter: Boolean) {
        val filteredList = if (resolvedFilter) {
            originalItems.filter { it.isResolved && it.title.contains(query, true) }
        } else {
            originalItems.filter { !it.isResolved && it.title.contains(query, true) }
        }
        clear()
        addAll(filteredList)
        refresh()
    }

    fun setOriginalItems(items: List<AdminReports>) {
        originalItems.clear()
        originalItems.addAll(items)
        clear()
        addAll(items)
        refresh()
    }

    class ViewHolder(val binding: ViewCellReportsListBinding) : RecyclerView.ViewHolder(binding.root)
}

package com.icare.icare.screens

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.icare.icare.databinding.ViewCellSubscribtionBinding
import com.icare.icare.generics.BaseRecyclerViewAdapter
import com.icare.icare.models.Notifications
import com.icare.icare.models.Subscription

class UserSubcrebtionAdapter(
    private val context: Context,
    private val onItemClick: (Subscription) -> Unit
) : BaseRecyclerViewAdapter<Subscription, UserSubcrebtionAdapter.ViewHolder>(null) {

    override fun createViewHolder(
        viewGroup: ViewGroup,
        viewType: Int,
        layoutInflater: LayoutInflater
    ): ViewHolder {
        return ViewHolder(
            ViewCellSubscribtionBinding.inflate(
                layoutInflater,
                viewGroup,
                false
            )
        )
    }

    override fun bindViewHolder(
        viewHolder: ViewHolder,
        position: Int,
        item: Subscription
    ) {
        with(viewHolder.binding) {
            tvPlanYear.text = item.title
            tvPlanDescribtion.text = item.description
            tvPrice.text = item.price

            // Handle item click
            root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    class ViewHolder(val binding: ViewCellSubscribtionBinding) : RecyclerView.ViewHolder(binding.root)
}

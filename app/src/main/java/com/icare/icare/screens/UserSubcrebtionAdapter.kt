
package com.icare.icare.screens

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.icare.icare.databinding.ViewCellSubscribtionBinding
import com.icare.icare.generics.BaseRecyclerViewAdapter
import com.icare.icare.models.Subscription
import kotlinx.android.synthetic.main.view_cell_notification.view.tv_notification_text

class UserSubcrebtionAdapter(
    val recyclerView: RecyclerView,
    val context: Context
) : BaseRecyclerViewAdapter<Subscription, UserSubcrebtionAdapter.ViewHolder>(
    recyclerView
) {
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



            tvPlanYear.text=item.title
            tvPlanDescribtion.text=item.description
            tvPrice.text=item.price


        }
    }

    class ViewHolder(val binding: ViewCellSubscribtionBinding) : RecyclerView.ViewHolder(binding.root)

}
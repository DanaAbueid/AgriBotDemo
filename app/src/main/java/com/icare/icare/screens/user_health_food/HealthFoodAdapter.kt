package com.icare.icare.screens.user_health_food

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.icare.icare.databinding.ViewCellHealthFoodBinding
import com.icare.icare.databinding.ViewCellReportsListBinding
import com.icare.icare.generics.BaseRecyclerViewAdapter
import com.icare.icare.models.HealthFood

class HealthFoodAdapter(
    val recyclerView: RecyclerView,
    val context: Context
) : BaseRecyclerViewAdapter<HealthFood, HealthFoodAdapter.ViewHolder>(
    recyclerView
) {
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
        item: HealthFood
    ) {
        with(viewHolder.binding) {
            ivBag.background = item.imageUrl?.let {
                ContextCompat.getDrawable(
                    context,
                    it
                )
            }
            tvUser.text = item.name

        }
    }

    class ViewHolder(val binding: ViewCellReportsListBinding) : RecyclerView.ViewHolder(binding.root)

}
package com.icare.icare.screens.user_nutrition_centers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.icare.icare.databinding.ViewCellCenterBinding
import com.icare.icare.generics.BaseRecyclerViewAdapter
import com.icare.icare.models.Center

class UserNutritionCentersAdapter(
    val recyclerView: RecyclerView,
    val context: Context
) : BaseRecyclerViewAdapter<Center, UserNutritionCentersAdapter.ViewHolder>(
    recyclerView
) {
    override fun createViewHolder(
        viewGroup: ViewGroup,
        viewType: Int,
        layoutInflater: LayoutInflater
    ): ViewHolder {
        return ViewHolder(
            ViewCellCenterBinding.inflate(
                layoutInflater,
                viewGroup,
                false
            )
        )
    }

    override fun bindViewHolder(
        viewHolder: ViewHolder,
        position: Int,
        item: Center
    ) {
        with(viewHolder.binding) {
            ivBg.background = item.imageUrl?.let {
                ContextCompat.getDrawable(
                    context,
                    it
                )
            }
            tvName.text = item.name
            tvLocation.text = item.location
            tvOpenHours.text = item.workingHours
            rating.rating = item.rating ?: 0.0f
        }
    }

    class ViewHolder(val binding: ViewCellCenterBinding) : RecyclerView.ViewHolder(binding.root)

}
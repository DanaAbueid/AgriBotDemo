
package com.icare.icare.screens

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.icare.icare.databinding.ViewCellPricesBinding
import com.icare.icare.generics.BaseRecyclerViewAdapter
import com.icare.icare.models.Prices
import kotlinx.android.synthetic.main.view_cell_notification.view.tv_notification_text
import kotlinx.android.synthetic.main.view_cell_prices.view.tv_price

class SubDescriptionAdapter(
    val recyclerView: RecyclerView,
    val context: Context
) : BaseRecyclerViewAdapter<Prices, SubDescriptionAdapter.ViewHolder>(
    recyclerView
) {
    override fun createViewHolder(
        viewGroup: ViewGroup,
        viewType: Int,
        layoutInflater: LayoutInflater

    ): ViewHolder {
        return ViewHolder(
            ViewCellPricesBinding.inflate(
                layoutInflater,
                viewGroup,
                false
            )
        )
    }

    override fun bindViewHolder(
        viewHolder: ViewHolder,
        position: Int,
        item: Prices
    ) {
        with(viewHolder.binding) {

            tvPlanYear.text=item.description
            tvPrice.text=item.price

        }
    }

    class ViewHolder(val binding: ViewCellPricesBinding) : RecyclerView.ViewHolder(binding.root)

}

package com.icare.icare.screens

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.icare.icare.databinding.ViewCellPricesBinding
import com.icare.icare.generics.BaseRecyclerViewAdapter
import com.icare.icare.models.Prices

class SubDescriptionAdapter(
    val recyclerView: RecyclerView,
    val context: Context,
    val onItemClick: (position: Int) -> Unit

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
            btnToPay.setOnClickListener {
                onItemClick(position)

                // Invoke the callback when the button is clicked
            }
        }
    }

    class ViewHolder(val binding: ViewCellPricesBinding) : RecyclerView.ViewHolder(binding.root)

}
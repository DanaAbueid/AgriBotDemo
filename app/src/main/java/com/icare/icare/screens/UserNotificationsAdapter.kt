
package com.icare.icare.screens

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.icare.icare.databinding.ViewCellNotificationBinding
import com.icare.icare.generics.BaseRecyclerViewAdapter
import com.icare.icare.models.Notifications
import kotlinx.android.synthetic.main.view_cell_notification.view.tv_notification_text

class UserNotificationsAdapter(
    val recyclerView: RecyclerView,
    val context: Context
) : BaseRecyclerViewAdapter<Notifications, UserNotificationsAdapter.ViewHolder>(
    recyclerView
) {
    override fun createViewHolder(
        viewGroup: ViewGroup,
        viewType: Int,
        layoutInflater: LayoutInflater

    ): ViewHolder {
        return ViewHolder(
            ViewCellNotificationBinding.inflate(
                layoutInflater,
                viewGroup,
                false
            )
        )
    }

    override fun bindViewHolder(
        viewHolder: ViewHolder,
        position: Int,
        item: Notifications
    ) {
        with(viewHolder.binding) {

            cvNotifcationIcon.background = item.imageUrl?.let {
                ContextCompat.getDrawable(
                    context,
                    it
                )
            }

       tvNotificationText.text=item.title


        }
    }

    class ViewHolder(val binding: ViewCellNotificationBinding) : RecyclerView.ViewHolder(binding.root)

}
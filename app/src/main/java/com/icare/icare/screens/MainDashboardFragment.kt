
package com.icare.icare.screens

import com.icare.icare.screens.UserNotificationsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.icare.icare.R
import com.icare.icare.databinding.FragmentMainDashboardBinding

import com.icare.icare.models.Notifications

import com.icare.icare.screens.BaseFragment
import kotlinx.android.synthetic.main.fragment_main_dashboard.iv_condition
import kotlinx.android.synthetic.main.fragment_main_dashboard.tv_condition_label
import kotlinx.android.synthetic.main.fragment_main_dashboard.tv_condition_text
import kotlinx.android.synthetic.main.fragment_main_dashboard.tv_image_condition


class MainDashboardFragment : BaseFragment() {
    private var binding: FragmentMainDashboardBinding? = null
    private lateinit var btncrops: Button
    private lateinit var btnsoil: Button
    private lateinit var btntask: Button
    override fun isLoggedin(): Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainDashboardBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewToolbar?.ivMenu?.visibility = View.VISIBLE
        binding?.viewToolbar?.ivMenu?.setOnClickListener {
            toggleSideMenu(true)
        }
        btncrops = view.findViewById(R.id.btn_crops)
        btnsoil = view.findViewById(R.id.btn_soil)
        btntask = view.findViewById(R.id.btn_task)

        btncrops.setOnClickListener { onButtonClicked(btncrops) }
        btnsoil.setOnClickListener { onButtonClicked(btnsoil) }
        btntask.setOnClickListener { onButtonClicked(btntask) }

        val UserNotificationsAdapter = activity?.let {
            binding?.rvUserNotifications?.let { it1 ->
                UserNotificationsAdapter(
                    it1, it
                )
            }
        }
        val NotificationsList = listOf<Notifications>(
            Notifications("The Robot task is now finished check the available analysis for this task!",R.drawable.correct),
            Notifications("please Stop robot due to weather condition ",R.drawable.cloud_error_24_regular__1_),
            Notifications("Test 3",R.drawable.cloud_error_24_regular__2_),
            Notifications("Test 4",R.drawable.correct),
            Notifications("Test 5",R.drawable.correct),
        )
        val dividerItemDecoration = DividerItemDecoration(
            binding?.rvUserNotifications?.context,
            RecyclerView.VERTICAL
        )
        context?.let {
            ContextCompat.getDrawable(it, R.drawable.divider)
                ?.let { dividerItemDecoration.setDrawable(it) }
        }
        UserNotificationsAdapter?.addItemDecoration(dividerItemDecoration)
        UserNotificationsAdapter?.addAll(NotificationsList)?.refresh()


    }
    private fun onButtonClicked(clickedButton: Button) {
        // Reset the background color (tint) for all buttons
        resetButtonBackgroundColors()

        // Set a different background color (tint) for the clicked button
        clickedButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green3))

        // Handle the button's click action here
        when (clickedButton.id) {
            R.id.btn_crops -> {
                iv_condition.setImageResource(R.drawable.plant_bold) // Replace with your drawable resource
                tv_condition_label.text = "Healthy Crops"
                tv_condition_text.text="16"
                val newDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.plant_line)
                tv_image_condition.text="33"
                tv_image_condition.setCompoundDrawablesWithIntrinsicBounds(newDrawable, null, null, null)

            }
            R.id.btn_soil -> {
                iv_condition.setImageResource(R.drawable.soil_temperature_field__1_) // Replace with your drawable resource
                tv_condition_label.text = "Soil Health"
                tv_condition_text.text="Good"
                tv_image_condition.text="33%"
                val newDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.humidity__1_)
                tv_image_condition.setCompoundDrawablesWithIntrinsicBounds(newDrawable, null, null, null)
            }
            R.id.btn_task -> {
                iv_condition.setImageResource(R.drawable.progress__1_) // Replace with your drawable resource
                tv_condition_label.text = "Task Period"
                tv_condition_text.text="Running"
                tv_image_condition.text="33%"
                val newDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.refresh__1_)
                tv_image_condition.setCompoundDrawablesWithIntrinsicBounds(newDrawable, null, null, null)
            }
        }
    }
    private fun resetButtonBackgroundColors() {
        // Reset the background color (tint) for all buttons to the original color
        btncrops.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green1))
        btnsoil.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green1))
        btntask.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green1))
    }


}
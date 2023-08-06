package com.icare.icare.screens.user_health_food

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.icare.icare.R
import com.icare.icare.databinding.FragmentUserHealthFoodBinding
import com.icare.icare.models.HealthFood
import com.icare.icare.screens.BaseFragment


class UserHealthFoodFragment : BaseFragment() {
    private var binding: FragmentUserHealthFoodBinding? = null
    override fun isLoggedin(): Boolean = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserHealthFoodBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewToolbar?.ivMenu?.visibility = View.VISIBLE
        binding?.viewToolbar?.ivMenu?.setOnClickListener {
            toggleSideMenu(true)
        }

        val healthFoodAdapter = activity?.let {
            binding?.rvHealthFood?.let { it1 ->
                HealthFoodAdapter(
                    it1, it
                )
            }
        }
        val healthFoodList = listOf<HealthFood>(
            HealthFood("Test 1", R.drawable.ic_food_bg, "079999999"),
            HealthFood("Test 2", R.drawable.ic_food_bg, "079999999"),
            HealthFood("Test 3", R.drawable.ic_food_bg, "079999999"),
            HealthFood("Test 4", R.drawable.ic_food_bg, "079999999"),
            HealthFood("Test 5", R.drawable.ic_food_bg, "079999999"),
            HealthFood("Test 6", R.drawable.ic_food_bg, "079999999"),
            HealthFood("Test 7", R.drawable.ic_food_bg, "079999999"),
        )
        val dividerItemDecoration = DividerItemDecoration(
            binding?.rvHealthFood?.context,
            RecyclerView.VERTICAL
        )
        context?.let {
            ContextCompat.getDrawable(it, R.drawable.divider)
                ?.let { dividerItemDecoration.setDrawable(it) }
        }
        healthFoodAdapter?.addItemDecoration(dividerItemDecoration)
        healthFoodAdapter?.addAll(healthFoodList)?.refresh()
    }


}
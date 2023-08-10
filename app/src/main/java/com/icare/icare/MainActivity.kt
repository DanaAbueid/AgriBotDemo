package com.icare.icare

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.navigation.findNavController
import com.icare.icare.databinding.ActivityMainBinding
import com.icare.icare.databinding.ViewMenuItemBinding
import android.view.Menu
import android.view.MenuItem




class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var selectedItemId = R.id.nav_bar_item_nutrient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        binding?.dlMain?.openDrawer(Gravity.LEFT)
        binding?.dlMain?.closeDrawer(Gravity.LEFT)
        UserSession.defaultMenu = R.menu.menu_user


    }

    fun isLoggedIn(state: Boolean) {
        binding?.nvMain?.isEnabled = state
        binding?.bottomNavigation?.visibility = View.VISIBLE.takeIf { state } ?: View.GONE
        if (state) {
            setBottomNav()
            setMenu()
        }
    }

    val bottomNavItems = listOf(
        Triple(R.id.User_Reports_List, R.drawable.round_dashboard__1_, "Home"),
        Triple(R.id.User_Reports_List, R.drawable.cloud_circle, "Weather"),
        Triple(R.id.User_Reports_List, R.drawable.plant_bold, "Soil"),
        Triple(R.id.User_Reports_List, R.drawable.setting__2_, "Settings"),
    )
    private fun setBottomNav() {
        binding?.bottomNavigation?.let { bottomNavView ->
            if (bottomNavView.menu.isEmpty()) {
                // Clear existing items if any
                bottomNavView.menu.clear()

                // Add menu items from bottomNavItems
                for (item in bottomNavItems) {
                    bottomNavView.menu.add(Menu.NONE, item.first, Menu.NONE, item.third)
                        .setIcon(item.second)
                }
            }

            // Set the selected item based on the selectedItemId
            bottomNavView.selectedItemId = selectedItemId

            // Set the item selection listener
            bottomNavView.setOnItemSelectedListener { menuItem ->
                matchIdToFragment(menuItem.itemId)?.let { idNotNull ->
                    selectedItemId = menuItem.itemId
                    navigate(idNotNull)
                }
                true
            }
        }
    }

    private val sideMenuItems = listOf(
        Triple(R.id.User_Reports_List, R.drawable.user_4_fill__4_, "My Profile"),
        Triple(R.id.User_Reports_List, R.drawable.round_dashboard__1_, "Dashboard"),
        Triple(R.id.User_Reports_List, R.drawable.cloud_circle, "Weather"),
        Triple(R.id.User_Reports_List, R.drawable.plant_bold, "Soil"),
        Triple(R.id.User_Reports_List, R.drawable.progress__2_, "Progress"),
        Triple(R.id.User_Reports_List, R.drawable.robot_2__1_, "Robot"),
        Triple(R.id.User_Reports_List, R.drawable.round_report, "Reports"),
    )
    private fun setMenu() {
        binding?.clMenu?.llItems?.removeAllViews()

        for (item in sideMenuItems) {
            val itemBinding = ViewMenuItemBinding.inflate(layoutInflater)
            itemBinding.ivLogo.setImageDrawable(ContextCompat.getDrawable(this, item.second))
            itemBinding.tvTitle.text = item.third

            itemBinding.root.setOnClickListener { view ->
                toggleSideMenu(false)
                matchIdToFragment(item.first)?.let { idNotNull ->
                    selectedItemId = item.first
                    navigate(idNotNull)
                }
            }
            if (item.first == selectedItemId) {
                itemBinding.tvTitle.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.dark_green1
                    )
                )
                itemBinding.ivLogo.setColorFilter(
                    ContextCompat.getColor(
                        this,
                        R.color.dark_green1
                    )
                )
                itemBinding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.semi_transparent_light_green
                    )
                )
            }
            binding?.clMenu?.llItems?.addView(itemBinding.root)
        }
    }


    private fun matchIdToFragment(id: Int): Int? {
        if (id == R.id.nav_bar_item_myprofile)
            return R.id.User_Reports_List
        else if (id == R.id.nav_bar_item_dashboard)
            return R.id.User_Reports_List
        else if (id == R.id.nav_bar_item_my_weather)
            return R.id.User_Reports_List
        else if (id == R.id.nav_bar_item_soil)
            return R.id.User_Reports_List
        else if (id == R.id.nav_bar_item_reports)
            return R.id.User_Reports_List
        else if (id == R.id.nav_bar_item_robot)
            return R.id.User_Reports_List
        else if (id == R.id.nav_bar_item_progress)
            return R.id.User_Reports_List

        return null
    }


    private fun navigate(@IdRes fragId: Int) {

        if (isFragmentInBackStack(fragId))
            findNavController(R.id.nav_host_fragment).popBackStack(fragId, false)
        else
            findNavController(R.id.nav_host_fragment).navigate(fragId)
    }

    private fun isFragmentInBackStack(destinationId: Int) =
        try {
            findNavController(R.id.nav_host_fragment).getBackStackEntry(destinationId)
            true
        } catch (e: Exception) {
            false
        }

    fun toggleSideMenu(state: Boolean) {
        if (state)
            binding?.dlMain?.openDrawer(Gravity.LEFT)
        else
            binding?.dlMain?.closeDrawer(Gravity.LEFT)
    }
}
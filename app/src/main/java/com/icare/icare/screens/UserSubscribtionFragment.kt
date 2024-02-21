
package com.icare.icare.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.icare.icare.R
import com.icare.icare.databinding.FragmentUserSubcrebtionBinding
import com.icare.icare.databinding.FragmentUserSubscribtionBinding
import com.icare.icare.models.Subscription

class UserSubscribtionFragment : BaseFragment() {
    private var binding: FragmentUserSubscribtionBinding? = null

    override fun isLoggedin(): Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserSubscribtionBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbar?.ivMenu?.visibility = View.VISIBLE
        binding?.toolbar?.ivMenu?.setOnClickListener {
            toggleSideMenu(true)
        }

        // Inside UserSubcrebtionFragment onViewCreated
        val userSubcrebtionList = listOf<Subscription>(
            Subscription("TypeA", "1 Day", "Essential: Do you want a weed-free farm? Tap for more info!", "From 15$"),
            Subscription("TypeB", "1 Day", "Standard: Striving for a weed-free, sustainable farm future with soil management? Tap for more info!\n", "From 25$"),
            Subscription("TypeD", "1 Day", "Premium: Working towards a weed-free, sustainable farm and advanced soil and crop health management. Tap for more info!", "From 40$"),
        )

// Create the adapter and set the item click callback
        val userSubcrebtionAdapter = UserSubcrebtionAdapter(requireContext()) { subscription ->
            // Handle item click and navigate based on the subscription type
            when (subscription.type) {
                "TypeA" -> findNavController().navigate(UserSubscribtionFragmentDirections.actionSignupToPlan1())
                "TypeB" -> findNavController().navigate(UserSubscribtionFragmentDirections.actionSignupToPlan3())
                "TypeD" -> findNavController().navigate(UserSubscribtionFragmentDirections.actionSignupToPlan2())
            }
        }

        binding?.rvSubscriptionPlans?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userSubcrebtionAdapter

            val dividerItemDecoration = DividerItemDecoration(
                context,
                RecyclerView.VERTICAL
            )
            ContextCompat.getDrawable(requireContext(), R.drawable.divider)?.let {
                dividerItemDecoration.setDrawable(it)
            }
            addItemDecoration(dividerItemDecoration)
        }

        userSubcrebtionAdapter.addAll(userSubcrebtionList).refresh()
    }





    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

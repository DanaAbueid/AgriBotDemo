

package com.icare.icare.screens
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.icare.icare.R
import com.icare.icare.ViewModel.AuthViewModel
import com.icare.icare.backend.RetrofitInstance
import com.icare.icare.databinding.FragmentSubDescriptionPBinding
import com.icare.icare.databinding.FragmentSubDescriptionSBinding
import com.icare.icare.models.Prices

import com.icare.icare.screens.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SubSDescriptionSFragment : BaseFragment() {
    private var binding: FragmentSubDescriptionSBinding? = null
    private val authViewModel: AuthViewModel by viewModels()

    private val userId = "304" // Replace with the actual user ID

    override fun isLoggedin(): Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubDescriptionSBinding.inflate(inflater, container, false)
        return binding?.root
        val accessToken = authViewModel.accessToken

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewToolbar?.ivMenu?.visibility = View.VISIBLE
        binding?.viewToolbar?.ivMenu?.setOnClickListener {
            toggleSideMenu(true)
        }

        val planPrices = listOf<Prices>(
            Prices("1 Day", "15$"),
            Prices("1 Week", "45$"),
            Prices("1 Month", "90$"),
        )

        val dividerItemDecoration = DividerItemDecoration(
            binding?.rvPrices?.context,
            RecyclerView.VERTICAL
        )

        context?.let {
            ContextCompat.getDrawable(it, R.drawable.divider)
                ?.let { dividerItemDecoration.setDrawable(it) }
        }

        val subDescriptionAdapter = SubDescriptionAdapter(
            binding?.rvPrices!!, // Safely unwrap the RecyclerView
            requireContext()
        ) { position ->
            val userId = 304L // Replace with the actual user ID (if not already defined)
            val subscriptionPlanId = when (position) {
                0 -> 2L
                1 -> 5L
                2 -> 8L
                else -> 0L
            }

            if (subscriptionPlanId > 0) {
                makeApiPostRequest(userId, subscriptionPlanId)

                val action = SubSDescriptionSFragmentDirections.actionMyS9ubToPayment(position)
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "Network error. Please try again later", Toast.LENGTH_SHORT).show()

            }
        }


        subDescriptionAdapter.addItemDecoration(dividerItemDecoration)
        subDescriptionAdapter.addAll(planPrices).refresh()
    }

    private fun makeApiPostRequest(userId: Long, subscriptionPlanId: Long) {
        val userSubscriptionApi = RetrofitInstance.UserSubscriptionApi()

        // Call the addSubscriptionPlan function to make the POST request
        val call = userSubscriptionApi.addSubscriptionPlan(userId, subscriptionPlanId)

        // Enqueue the call to execute it asynchronously
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    findNavController().navigate(SubSDescriptionSFragmentDirections.actionSubToPaymentD2())
                } else {

                    Toast.makeText(requireContext(), "Network error. Please try again later", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(requireContext(), "Network error. Please try again later", Toast.LENGTH_SHORT).show()

            }
        })
    }

}
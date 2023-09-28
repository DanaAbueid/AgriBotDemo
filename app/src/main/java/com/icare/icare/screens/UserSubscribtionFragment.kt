
package com.icare.icare.screens
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.icare.icare.R
import com.icare.icare.databinding.FragmentUserSubscribtionBinding

import com.icare.icare.models.Subscription

import com.icare.icare.screens.BaseFragment


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

        binding?.let { bindingNotNull ->
            bindingNotNull.tvButtonNextPay.setOnClickListener {
                findNavController().navigate(UserSubscribtionFragmentDirections.actionSignupToPayment())
            }
        }

        val UserSubcrebtionAdapter = activity?.let {
            binding?.rvSubscriptionPlans?.let { it1 ->
                UserSubcrebtionAdapter(
                    it1, it
                )
            }
        }
        val UserSubcrebtion = listOf<Subscription>(
            Subscription("1 Day","Essential:  Experience a weed-free future on your farm and more, Tap for more info! ","From 15$"),
            Subscription("1 Day","Standard: Striving for a weed-free, sustainable farm with soil management? Tap for more info! ","From 25$"),
            Subscription("1 Day","Premium: Working towards a weed-free, sustainable farm and advanced soil and crop health management? Tap for more info!","From 40$"),

        )
        val dividerItemDecoration = DividerItemDecoration(
            binding?.rvSubscriptionPlans?.context,
            RecyclerView.VERTICAL
        )
        context?.let {
            ContextCompat.getDrawable(it, R.drawable.divider)
                ?.let { dividerItemDecoration.setDrawable(it) }
        }
        UserSubcrebtionAdapter?.addItemDecoration(dividerItemDecoration)
        UserSubcrebtionAdapter?.addAll(UserSubcrebtion)?.refresh()
    }


}
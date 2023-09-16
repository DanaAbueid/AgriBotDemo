package com.icare.icare.screens
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.icare.icare.R
import com.icare.icare.databinding.FragmentUserSubcrebtionBinding

import com.icare.icare.models.Subscription

import com.icare.icare.screens.BaseFragment


class UserSubcrebtionFragment : BaseFragment() {
    private var binding: FragmentUserSubcrebtionBinding? = null
    override fun isLoggedin(): Boolean = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserSubcrebtionBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbar?.ivMenu?.visibility = View.VISIBLE
        binding?.toolbar?.ivMenu?.setOnClickListener {
            toggleSideMenu(true)
        }

        val UserSubcrebtionAdapter = activity?.let {
            binding?.rvSubscriptionPlans?.let { it1 ->
                UserSubcrebtionAdapter(
                    it1, it
                )
            }
        }
        val UserSubcrebtion = listOf<Subscription>(
            Subscription("1 Year","Test this sub ","66$"),
            Subscription("1 Year","Test this sub ","66$"),
            Subscription("1 Year","Test this sub ","66$"),
            Subscription("1 Year","Test this sub ","66$"),
            Subscription("1 Year","Test this sub ","66$"),
            Subscription("1 Year","Test this sub ","66$"),
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
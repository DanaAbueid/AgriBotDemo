

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
import com.icare.icare.databinding.FragmentSubDescriptionPBinding
import com.icare.icare.models.Prices

import com.icare.icare.screens.BaseFragment


class SubDescriptionPFragment : BaseFragment() {
    private var binding: FragmentSubDescriptionPBinding? = null
    override fun isLoggedin(): Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubDescriptionPBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewToolbar?.ivMenu?.visibility = View.VISIBLE
        binding?.viewToolbar?.ivMenu?.setOnClickListener {
            toggleSideMenu(true)
        }

        // binding?.let { bindingNotNull ->
        // bindingNotNull.tvButtonNextPay.setOnClickListener {
        // findNavController().navigate(UserSubcrebtionFragmentDirections.actionSignupToPayment())
        // }


        val SubDescriptionAdapter = activity?.let {
            binding?.rvPrices?.let { it1 ->
                SubDescriptionAdapter(
                    it1, it
                )
            }
        }
        val planPrices = listOf<Prices>(
            Prices("1 Day","15$"),
            Prices("1 Week","45$"),
            Prices("1 Month","90$"),

            )
        val dividerItemDecoration = DividerItemDecoration(
            binding?.rvPrices?.context,
            RecyclerView.VERTICAL
        )

        context?.let {
            ContextCompat.getDrawable(it, R.drawable.divider)
                ?.let { dividerItemDecoration.setDrawable(it) }
        }
        SubDescriptionAdapter?.addItemDecoration(dividerItemDecoration)
        SubDescriptionAdapter?.addAll(planPrices)?.refresh()

    }


}
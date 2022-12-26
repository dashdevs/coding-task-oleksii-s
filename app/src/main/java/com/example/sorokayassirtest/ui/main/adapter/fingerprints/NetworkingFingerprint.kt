package com.example.sorokayassirtest.ui.main.adapter.fingerprints

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sorokayassirtest.R
import com.example.sorokayassirtest.databinding.ItemNetworkingBinding
import com.example.sorokayassirtest.domain.entity.Item
import com.example.sorokayassirtest.domain.entity.Networking
import com.example.sorokayassirtest.ui.main.adapter.BaseViewHolder
import com.example.sorokayassirtest.ui.main.adapter.ItemClickListener
import com.example.sorokayassirtest.ui.main.adapter.ItemFingerprint

class NetworkingFingerprint(private val tryAgain: PressTryAgain) : ItemFingerprint<ItemNetworkingBinding, Networking> {
    override fun isRelativeItem(item: Item) = item is Networking

    override fun getLayoutId() = R.layout.item_networking

    override fun getViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup): BaseViewHolder<ItemNetworkingBinding, Networking> {
        val binding = ItemNetworkingBinding.inflate(layoutInflater, parent, false)
        return NetworkingViewHolder(binding, tryAgain)
    }
}

interface PressTryAgain {
    fun tryAgain()
}

class NetworkingViewHolder(binding: ItemNetworkingBinding, private val tryAgain: PressTryAgain) :
    BaseViewHolder<ItemNetworkingBinding, Networking>(binding) {

    override fun onBind(item: Networking, itemClickListener: ItemClickListener) {
        binding.apply {
            if (item.isItError) {
                button.apply {
                    visibility = View.VISIBLE
                    setOnClickListener {
                    }
                }
                progressBar.visibility = View.INVISIBLE
                tvMessage.text = item.message
                button.setOnClickListener {
                    tryAgain.tryAgain()
                }

            } else {
                button.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
                tvMessage.text = "Loading"
            }
        }
    }
}
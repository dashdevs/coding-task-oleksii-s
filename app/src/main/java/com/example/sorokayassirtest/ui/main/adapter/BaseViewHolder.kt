package com.example.sorokayassirtest.ui.main.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.sorokayassirtest.domain.entity.Item

abstract class BaseViewHolder<out V : ViewBinding, in I : Item>(val binding: V) : RecyclerView.ViewHolder(binding.root) {

    abstract fun onBind(item: I, itemClickListener: ItemClickListener)
}
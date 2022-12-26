package com.example.sorokayassirtest.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import com.example.sorokayassirtest.domain.entity.Item

interface ItemFingerprint<V : ViewBinding, I : Item> {

    fun isRelativeItem(item: Item): Boolean

    @LayoutRes
    fun getLayoutId(): Int

    fun getViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup): BaseViewHolder<V, I>
}
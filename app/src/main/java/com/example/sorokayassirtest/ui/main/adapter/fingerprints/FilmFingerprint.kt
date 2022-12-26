package com.example.sorokayassirtest.ui.main.adapter.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.sorokayassirtest.R
import com.example.sorokayassirtest.databinding.ItemFilmBinding
import com.example.sorokayassirtest.domain.entity.Film
import com.example.sorokayassirtest.domain.entity.Item
import com.example.sorokayassirtest.ui.main.adapter.BaseViewHolder
import com.example.sorokayassirtest.ui.main.adapter.ItemClickListener
import com.example.sorokayassirtest.ui.main.adapter.ItemFingerprint

class FilmFingerprint() : ItemFingerprint<ItemFilmBinding, Film> {

    override fun isRelativeItem(item: Item) = item is Film

    override fun getLayoutId() = R.layout.item_film

    override fun getViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup): BaseViewHolder<ItemFilmBinding, Film> {
        val binding = ItemFilmBinding.inflate(layoutInflater, parent, false)
        return FilmViewHolder(binding)
    }
}

class FilmViewHolder(binding: ItemFilmBinding) : BaseViewHolder<ItemFilmBinding, Film>(binding) {

    override fun onBind(item: Film, itemClickListener: ItemClickListener) {
        binding.apply {
            binding.root.setOnClickListener {
                itemClickListener.omItemClick(item)
            }
            Glide.with(this.root)
                .load(item.imageUrl)
                .error(R.drawable.ic_film_error)
                .into(ivFilmLogo)
            tvFilmTitle.text = item.title
            tvFilmVote.text = item.voteAverage.toString()
            tvFilmVoteCount.text = item.voteCount.toString()
            tvFilmDateRelease.isVisible = item.releaseDate.isNotEmpty()
            tvFilmDateRelease.text = item.releaseDate
        }
    }
}
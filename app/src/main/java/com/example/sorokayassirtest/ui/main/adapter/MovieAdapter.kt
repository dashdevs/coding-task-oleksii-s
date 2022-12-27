package com.example.sorokayassirtest.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sorokayassirtest.R
import com.example.sorokayassirtest.databinding.ItemMovieBinding
import com.example.sorokayassirtest.domain.entity.Movie

class MovieAdapter(
    private val itemClickListener: ItemClickListener
) : PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(MoviesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item: Movie? = getItem(position)
        item?.let {
            holder.onBind(it, itemClickListener)
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val newItem = payloads[0] as Movie
            holder.onBind(newItem, itemClickListener)
        }
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Movie, itemClickListener: ItemClickListener) {
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
}

class MoviesDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
}

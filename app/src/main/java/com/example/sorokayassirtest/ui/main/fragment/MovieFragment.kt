package com.example.sorokayassirtest.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.sorokayassirtest.R
import com.example.sorokayassirtest.databinding.FragmentMovieBinding

class MovieFragment : Fragment(R.layout.fragment_movie) {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private val args: MovieFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = args.movie
        binding.apply {
            movie.let {
                Glide.with(this.root)
                    .load(movie.imageUrl)
                    .error(R.drawable.ic_film_error)
                    .into(ivFilmLogo)
                tvFilmTitle.text = movie.title
                tvFilmVote.text = movie.voteAverage.toString()
                tvFilmVoteCount.text = movie.voteCount.toString()
                tvFilmDescription.text = movie.description
                tvFilmDateRelease.isVisible = movie.releaseDate.isNotEmpty()
                tvFilmDateRelease.text = movie.releaseDate
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

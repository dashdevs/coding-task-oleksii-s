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
import com.example.sorokayassirtest.data.net.moshi
import com.example.sorokayassirtest.databinding.FragmentFilmBinding
import com.example.sorokayassirtest.domain.entity.Film
import com.squareup.moshi.JsonAdapter

class FilmFragment : Fragment(R.layout.fragment_film) {

    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!
    private val args: FilmFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFilmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val jsonAdapter: JsonAdapter<Film> = moshi.adapter(Film::class.java)
        val film = jsonAdapter.fromJson(args.film)
        binding.apply {
            film?.let {
                Glide.with(this.root)
                    .load(film.imageUrl)
                    .error(R.drawable.ic_film_error)
                    .into(ivFilmLogo)
                tvFilmTitle.text = film.title
                tvFilmVote.text = film.voteAverage.toString()
                tvFilmVoteCount.text = film.voteCount.toString()
                tvFilmDescription.text = film.description
                tvFilmDateRelease.isVisible = film.releaseDate.isNotEmpty()
                tvFilmDateRelease.text = film.releaseDate
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

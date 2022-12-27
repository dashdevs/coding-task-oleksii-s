package com.example.sorokayassirtest.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.example.sorokayassirtest.R
import com.example.sorokayassirtest.databinding.FragmentAllMoviesBinding
import com.example.sorokayassirtest.domain.entity.Movie
import com.example.sorokayassirtest.ui.main.MainViewModel
import com.example.sorokayassirtest.ui.main.adapter.ItemClickListener
import com.example.sorokayassirtest.ui.main.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllMoviesFragment : Fragment(), ItemClickListener {
    private var _binding: FragmentAllMoviesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private val movieAdapter = MovieAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAllMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            recyclerView.apply {
                animation = null
                adapter = movieAdapter
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val llm = recyclerView.layoutManager as LinearLayoutManager
                        binding.ibScrollUp.isVisible = llm.findLastVisibleItemPosition() > 20
                    }
                })
            }

            binding.ibScrollUp.setOnClickListener {
                scrollToTop()
            }

            infoItems.btnTryAgain.setOnClickListener {
                viewModel.pressTryAgain()
            }
        }
        subscribeToFilmsFlow()
        movieAdapter.addLoadStateListener { loadState ->
            if ((loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading) && movieAdapter.itemCount < 1) {
                updateLoadingUIState(true)
                updateInfoUIState(true, resources.getString(R.string.loading))
            } else {
                updateLoadingUIState(false)
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    updateLoadingUIState(true)
                    updateInfoUIState(false, it.error.message.toString())
                }
            }
        }
    }

    private fun updateInfoUIState(isLoading: Boolean, infoText: String) {
        binding.apply {
            infoItems.tvInfoDescription.text = infoText
            infoItems.tvInfoDescription.isVisible = true
            infoItems.progressBar.isVisible = isLoading
            infoItems.btnTryAgain.isVisible = !isLoading
        }
    }

    private fun updateLoadingUIState(isLoading: Boolean) {
        binding.apply {
            recyclerView.isVisible = !isLoading
            infoItems.root.isVisible = isLoading
        }
    }

    private fun scrollToTop() {
        val llm = binding.recyclerView.layoutManager as LinearLayoutManager
        val smoothScroller: SmoothScroller = object : LinearSmoothScroller(context) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }

            override fun calculateTimeForScrolling(dx: Int): Int {
                return 200
            }
        }
        smoothScroller.targetPosition = 0
        llm.startSmoothScroll(smoothScroller)
    }

    private fun subscribeToFilmsFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.filmsFlow.collectLatest {
                movieAdapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun omItemClick(movie: Movie) {
        val action = AllMoviesFragmentDirections.actionMoviesAllFragmentToMovieFragment(movie)
        findNavController().navigate(action)
    }
}


package com.applogist.movietest.ui.movies

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.applogist.movietest.BR
import com.applogist.movietest.R
import com.applogist.movietest.base.BaseFragment
import com.applogist.movietest.databinding.FragmentMoviesBinding
import com.applogist.movietest.databinding.ItemMovieLayoutBinding
import com.applogist.movietest.network.response.MovieItemResponse
import com.applogist.movietest.utils.PAGING_PARAMS
import com.applogist.movietest.utils.formatDate
import com.applogist.movietest.utils.loadImage
import com.applogist.movietest.utils.showDialog
import com.blankj.utilcode.util.LogUtils
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.Type
import com.murgupluoglu.request.STATUS_ERROR
import com.murgupluoglu.request.STATUS_LOADING
import com.murgupluoglu.request.STATUS_SUCCESS
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import org.koin.androidx.viewmodel.ext.android.viewModel


class MoviesFragment(override val layoutId: Int = R.layout.fragment_movies) :
    BaseFragment() {
    private lateinit var binding: FragmentMoviesBinding
    private val viewModel: MoviesViewModel by viewModel()
    lateinit var moviesAdapter: LastAdapter
    lateinit var upComingPagingAdapter: UpComingMoviesAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initUpComingMoviesAdapter()
        initObserver()
        viewModel.getMovies()
        getUpComingMovies()
    }

    override fun setUpBinding() {
        binding = bBinding as FragmentMoviesBinding
    }

    private fun initObserver() {
        viewModel.moviesResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                STATUS_LOADING -> {
                    showProgressDialog()
                }
                STATUS_SUCCESS -> {
                    hideProgressDialog()
                    viewModel.addMovies(it.responseObject?.results)
                    moviesAdapter.notifyDataSetChanged()
                    viewModel.savedStateHandle.set(PAGING_PARAMS, "")
                }
                STATUS_ERROR -> {
                    hideProgressDialog()
                    requireContext().showDialog()
                    LogUtils.e("code ${it.errorCode} msg ${it.errorMessage}")

                }
            }
        })
    }

    private fun getUpComingMovies() {
        lifecycleScope.launchWhenCreated {
            viewModel.getUpComingMovies().collectLatest {
                upComingPagingAdapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            upComingPagingAdapter.loadStateFlow.distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }.collect {
                    binding.upComingRecyclerView.scrollToPosition(0)

                }
        }

        lifecycleScope.launchWhenCreated {
            upComingPagingAdapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh is LoadState.Loading) {
                    showProgressDialog()
                } else {
                    hideProgressDialog()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initAdapter() {
        moviesAdapter = LastAdapter(
            viewModel.nowPlayingMovieList,
            BR.item
        ).map<MovieItemResponse>(Type<ItemMovieLayoutBinding>(R.layout.item_movie_layout).onBind { holder ->
            val data = holder.binding.item
            data?.let { movie ->
                holder.binding.apply {
                    movieImageView.loadImage(movie.backdropPath)
                    movieNameTextView.text = "${movie.title} (${movie.releaseDate.formatDate()})"

                    movieDescriptionTextView.text = movie.overview
                }
            }

        }.onClick { holder ->/*
            val data = holder.binding.item
            data?.imdbID?.let { imdbID ->
                val action = MovieSearchFragmentDirections.movieSearchToDetail(imdbID)
                holder.binding.root.findNavController().navigate(action)

            }*/
        })
        binding.nowPlayingViewPager.adapter = moviesAdapter
    }

    private fun initUpComingMoviesAdapter() {

        upComingPagingAdapter = UpComingMoviesAdapter { data ->
            recyclerViewItemClick(data)
        }
        binding.upComingRecyclerView?.adapter = upComingPagingAdapter
    }

    private fun recyclerViewItemClick(item: MovieItemResponse?) {

    }


}
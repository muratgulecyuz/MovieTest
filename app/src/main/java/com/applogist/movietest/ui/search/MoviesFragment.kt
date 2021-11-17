package com.applogist.movietest.ui.search

import android.os.Bundle
import android.view.View
import com.applogist.movietest.BR
import com.applogist.movietest.R
import com.applogist.movietest.base.BaseFragment
import com.applogist.movietest.databinding.FragmentMoviesBinding
import com.applogist.movietest.databinding.ItemMovieLayoutBinding
import com.applogist.movietest.network.response.MovieItemResponse
import com.applogist.movietest.utils.loadImage
import com.applogist.movietest.utils.showDialog
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.LogUtils
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.Type
import com.murgupluoglu.request.STATUS_ERROR
import com.murgupluoglu.request.STATUS_LOADING
import com.murgupluoglu.request.STATUS_SUCCESS
import org.koin.androidx.viewmodel.ext.android.viewModel


class MoviesFragment(override val layoutId: Int = R.layout.fragment_movies) :
    BaseFragment() {
    private lateinit var binding: FragmentMoviesBinding
    private val viewModel: MovieSearchViewModel by viewModel()
    lateinit var moviesAdapter: LastAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObserver()
        viewModel.getMovies()
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
                    KeyboardUtils.hideSoftInput(requireActivity())
                }
                STATUS_ERROR -> {
                    hideProgressDialog()
                    requireContext().showDialog()
                    LogUtils.e("code ${it.errorCode} msg ${it.errorMessage}")

                }
            }
        })
    }

    private fun initAdapter() {
        moviesAdapter = LastAdapter(
            viewModel.nowPlayingMovieList,
            BR.item
        ).map<MovieItemResponse>(Type<ItemMovieLayoutBinding>(R.layout.item_movie_layout).onBind { holder ->
            val data = holder.binding.item
            data?.let { movie ->
                holder.binding.apply {
                    movieImageView.loadImage(movie.backdropPath)
                    movieNameTextView.text = movie.title
                }
            }

        }.onClick { holder ->/*
            val data = holder.binding.item
            data?.imdbID?.let { imdbID ->
                val action = MovieSearchFragmentDirections.movieSearchToDetail(imdbID)
                holder.binding.root.findNavController().navigate(action)

            }*/
        }).into(binding.moviesRecyclerView)
    }


}
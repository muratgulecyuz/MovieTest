package com.applogist.movietest.ui.detail

import android.os.Bundle
import android.view.View
import com.applogist.movietest.R
import com.applogist.movietest.base.BaseFragment
import com.applogist.movietest.databinding.FragmentMovieDetailBinding
import com.applogist.movietest.network.response.MovieDetailResponse
import com.applogist.movietest.utils.DEFAULT_DATE_FORMAT
import com.applogist.movietest.utils.formatDate
import com.applogist.movietest.utils.loadImage
import com.applogist.movietest.utils.showDialog
import com.blankj.utilcode.util.LogUtils
import com.murgupluoglu.request.STATUS_ERROR
import com.murgupluoglu.request.STATUS_LOADING
import com.murgupluoglu.request.STATUS_SUCCESS
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailFragment(override val layoutId: Int = R.layout.fragment_movie_detail) :
    BaseFragment() {
    private val viewModel: MovieDetailViewModel by viewModel()
    private lateinit var binding: FragmentMovieDetailBinding

    override fun setUpBinding() {
        binding = bBinding as FragmentMovieDetailBinding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBundle()
        initObserver()
    }

    private fun initObserver() {
        viewModel.movieDetailResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                STATUS_LOADING -> {
                    showProgressDialog()
                }
                STATUS_SUCCESS -> {
                    hideProgressDialog()
                    val response = it.responseObject
                    response?.let { result ->
                        initUi(result)
                    }
                }
                STATUS_ERROR -> {
                    hideProgressDialog()
                    requireContext().showDialog()
                    LogUtils.e("code ${it.errorCode} msg ${it.errorMessage}")

                }
            }
        })
    }

    private fun initUi(movieDetailResponse: MovieDetailResponse) {
        binding.movieImageView.loadImage(movieDetailResponse.backdropPath)
        binding.movieNameTextView.text = movieDetailResponse.title
        binding.movieDescriptionTextView.text = movieDetailResponse.overview
        binding.imdbTextView.text = "${movieDetailResponse.voteAverage}/10"
        binding.dateTextView.text = movieDetailResponse.releaseDate.formatDate(DEFAULT_DATE_FORMAT)
    }

    private fun getBundle() {

        val movieId = MovieDetailFragmentArgs.fromBundle(requireArguments()).movieId
        viewModel.getMovieDetail(movieId)

    }

}
package com.applogist.movietest.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.applogist.movietest.MainActivity
import com.applogist.movietest.R
import com.applogist.movietest.base.BaseFragment
import com.applogist.movietest.databinding.FragmentMovieDetailBinding
import com.applogist.movietest.ui.search.MovieSearchViewModel
import com.applogist.movietest.utils.loadImage
import com.applogist.movietest.utils.showDialog
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.LogUtils
import com.google.android.material.appbar.MaterialToolbar
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
                    binding.movieImageView.loadImage(response?.poster)
                    binding.movieTypeTextView.text = response?.genre
                    binding.movieDescriptionTextView.text = response?.plot
                }
                STATUS_ERROR -> {
                    hideProgressDialog()
                    requireContext().showDialog()
                    LogUtils.e("code ${it.errorCode} msg ${it.errorMessage}")

                }
            }
        })
    }

    private fun getBundle() {

        val imdbId = MovieDetailFragmentArgs.fromBundle(requireArguments()).imdbid
        viewModel.getMovieDetail(imdbId)

    }

}
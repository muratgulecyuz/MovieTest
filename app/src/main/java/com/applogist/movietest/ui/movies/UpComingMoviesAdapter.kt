package com.applogist.movietest.ui.movies

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.applogist.movietest.R
import com.applogist.movietest.network.response.MovieItemResponse
import com.applogist.movietest.utils.DEFAULT_DATE_FORMAT
import com.applogist.movietest.utils.formatDate
import com.applogist.movietest.utils.loadImage

class UpComingMoviesAdapter(private val clickListener: (MovieItemResponse?,View) -> Unit) :
    PagingDataAdapter<MovieItemResponse, UpComingMoviesViewHolder>(
        POST_COMPARATOR
    ) {


    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<MovieItemResponse>() {
            override fun areContentsTheSame(
                oldItem: MovieItemResponse,
                newItem: MovieItemResponse
            ): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(
                oldItem: MovieItemResponse,
                newItem: MovieItemResponse
            ): Boolean =
                oldItem.id == newItem.id

            override fun getChangePayload(
                oldItem: MovieItemResponse,
                newItem: MovieItemResponse
            ): Any? {
                return newItem
            }
        }
    }

    override fun onBindViewHolder(holder: UpComingMoviesViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onBindViewHolder(
        holder: UpComingMoviesViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpComingMoviesViewHolder {
        return UpComingMoviesViewHolder.create(parent, clickListener)
    }
}

class UpComingMoviesViewHolder(
    view: View,
    private val clickListener: (MovieItemResponse?,View) -> Unit
) :
    RecyclerView.ViewHolder(view) {

    private val container: ConstraintLayout = view.findViewById(R.id.container)
    private val movieImageView: ImageView = view.findViewById(R.id.movieImageImageView)
    private val movieNameTextView: TextView = view.findViewById(R.id.movieNameTextView)
    private val movieDescriptionTextView: TextView =
        view.findViewById(R.id.movieDescriptionTextView)
    private val movieReleasedDateTextView: TextView =
        view.findViewById(R.id.movieReleaseDateTextView)

    companion object {
        fun create(
            parent: ViewGroup,
            clickListener: (MovieItemResponse?,View) -> Unit
        ): UpComingMoviesViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_upcoming_layout, parent, false)
            return UpComingMoviesViewHolder(
                view, clickListener
            )
        }
    }

    fun bind(model: MovieItemResponse) {
        container.setOnClickListener {
            clickListener(model,it)
        }

        movieImageView.loadImage(model.backdropPath)
        movieNameTextView.text = model.title
        movieDescriptionTextView.text = model.overview
        movieReleasedDateTextView.text = model.releaseDate.formatDate(DEFAULT_DATE_FORMAT)

    }
}
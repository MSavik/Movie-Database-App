package com.msavik.movie_database_app.ui.home.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.msavik.data.utility.BaseUrl
import com.msavik.domain.model.movie.Movie
import com.msavik.movie_database_app.databinding.ItemMovieDetailsBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MovieAdapter : ListAdapter<Movie, MovieAdapter.ViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.ViewHolder {
        return ViewHolder(
            ItemMovieDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieAdapter.ViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    open inner class ViewHolder(
        private val binding: ItemMovieDetailsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        open fun bindTo(movie: Movie) {
            binding.apply {
                Glide.with(this@ViewHolder.itemView.context)
                    .load(BaseUrl.BASE_IMAGE_URL + movie.poster_path)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(ivMovie)

                tvTitle.text = movie.title
                tvReleaseDate.text = DateFormat.getDateInstance().format(
                    SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH).parse(movie.release_date) ?: ""
                )
                tvRating.text = "${movie.vote_average.toString()} / 10"
            }
        }
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
    }
}
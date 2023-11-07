package com.msavik.movie_database_app.ui.home

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

class MovieAdapter(val onItemClick: (Int) -> Unit) : ListAdapter<Movie, MovieAdapter.ViewHolder>(
    ItemDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMovieDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    open inner class ViewHolder(
        private val binding: ItemMovieDetailsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        open fun bindTo(movie: Movie) {
            binding.apply {
                clMovieItem.setOnClickListener {
                    onItemClick(movie.id)
                }

                Glide.with(this@ViewHolder.itemView.context)
                    .load(BaseUrl.BASE_IMAGE_URL + movie.poster_path)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(ivMovie)

                tvTitle.text = movie.title
                tvReleaseDate.text = if (movie.release_date?.isNotEmpty() == true) {
                    DateFormat.getDateInstance().format(
                        SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH).parse(movie.release_date ?: "") ?: ""
                    )
                } else {
                    ""
                }
                tvRating.text = String.format("%.1f", movie.vote_average) + " / 10"
            }
        }
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
    }
}
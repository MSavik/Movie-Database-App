package com.msavik.movie_database_app.ui.home.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.msavik.domain.model.movie.Movie
import com.msavik.movie_database_app.databinding.ItemMovieDetailsBinding

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
                tvTitle.text = movie.title
            }
        }
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
    }
}
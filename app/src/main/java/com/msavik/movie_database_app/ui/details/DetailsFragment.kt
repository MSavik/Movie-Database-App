package com.msavik.movie_database_app.ui.details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import com.msavik.data.utility.BaseUrl
import com.msavik.domain.model.movie.Movie
import com.msavik.domain.utility.Resource
import com.msavik.movie_database_app.R
import com.msavik.movie_database_app.databinding.FragmentDetailsBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by sharedViewModel()
    private var movieId = 0
    private var movie: Movie? = null
    private var favoriteMoviesList: List<Movie> = emptyList()
    private lateinit var page: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)

        binding.ivToolbarBack.setOnClickListener {
            activity?.onBackPressed()
        }

        movieId = arguments?.getString("movieId")?.toInt() ?: 0
        page = arguments?.getString("page") ?: ""

        initMovieObserver()
        viewModel.getMovieById(movieId, page)
        initMovieListObserver()
        viewModel.getFavoriteMoviesList()
        setFavoriteButton()
    }

    private fun initMovieObserver() {
        viewModel.movieLiveData.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Loading -> {
                    Log.d(TAG, "Loading...")
                }
                is Resource.Success -> {
                    response.data?.let { movie ->
                        Log.d(TAG, "Success!")
                        this.movie = movie

                        Log.d(TAG, "LOGOBSERVER: ${this.movie}")

                        initView()
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e(TAG, "Error: $message")
                        Snackbar.make(requireView(), "Error: $message", Snackbar.LENGTH_LONG)
                    }
                }
            }
        }
    }

    private fun initMovieListObserver() {
        viewModel.favoriteMovieListLiveData.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Loading -> {
                    Log.d(TAG, "Loading...")
                }
                is Resource.Success -> {
                    response.data?.let { movieList ->
                        Log.d(TAG, "Success!")
                        this.favoriteMoviesList = movieList

                        Log.d(TAG, "LOGOBSERVER: ${this.favoriteMoviesList}")

                        if (isMovieContainedInList()) {
                            binding.ivFavorite.setImageResource(R.drawable.ic_favorite_red)
                        } else {
                            binding.ivFavorite.setImageResource(R.drawable.ic_favorite_border_red)
                        }
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e(TAG, "Error: $message")
                        Snackbar.make(requireView(), "Error: $message", Snackbar.LENGTH_LONG)
                    }
                }
            }
        }
    }

    private fun initView() {
        binding.apply {
            Glide.with(requireContext())
                .load(BaseUrl.BASE_IMAGE_URL + movie?.poster_path)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(ivMovie)

            val stringList = mutableListOf<String>()

            tvTitle.text = movie?.title
            tvOverview.text = movie?.overview
            tvVoteAverage.text = String.format("%.1f", movie?.vote_average)
            tvReleaseDate.text = if (movie?.release_date?.isNotEmpty() == true) {
                DateFormat.getDateInstance().format(
                    SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH).parse(movie?.release_date ?: "") ?: ""
                )
            } else {
                ""
            }

            movie?.genres?.forEach {
                stringList.add(it.name)
            }
            tvGenres.text = stringList.joinToString(", ")
            stringList.clear()

            movie?.production_countries?.forEach {
                stringList.add(it.name)
            }
            tvProductionCountries.text = stringList.joinToString(", ")
            stringList.clear()

            movie?.spoken_languages?.forEach {
                stringList.add(it.name)
            }
            tvSpokenLanguage.text = stringList.joinToString(", ")
            stringList.clear()

            val numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH)
            tvBudget.text = "\$${numberFormat.format(movie?.budget)}"
            tvRevenue.text = "\$${numberFormat.format(movie?.revenue)}"

            movie?.production_companies?.forEach {
                stringList.add(it.name)
            }
            tvProductionCompanies.text = stringList.joinToString(", ")
            stringList.clear()

            if (!isMovieContainedInList()) {
                viewModel.getFavoriteMoviesList()
            }
        }
    }

    private fun setFavoriteButton() {
        binding.ivFavorite.setOnClickListener {
            if (movie != null) {
                if (isMovieContainedInList()) {
                    viewModel.deleteFavoriteMovie(movie!!)
                    binding.ivFavorite.setImageResource(R.drawable.ic_favorite_border_red)
                } else {
                    viewModel.saveFavoriteMovie(movie!!)
                    binding.ivFavorite.setImageResource(R.drawable.ic_favorite_red)
                }
            }
        }
    }

    private fun isMovieContainedInList(): Boolean {
        if (movie == null) return false

        favoriteMoviesList.forEach {
            if (it.id == movie?.id) {
                return true
            }
        }
        return false
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName
    }
}
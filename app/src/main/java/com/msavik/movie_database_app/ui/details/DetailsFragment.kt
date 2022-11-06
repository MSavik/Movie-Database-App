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
import com.msavik.domain.utility.Page
import com.msavik.domain.utility.Resource
import com.msavik.movie_database_app.R
import com.msavik.movie_database_app.databinding.FragmentDetailsBinding
import com.msavik.movie_database_app.ui.home.movie.HomeFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by sharedViewModel()
    private lateinit var movie: Movie

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)

        val movieId = arguments?.getString("movieId")?.toInt()
        val page = arguments?.getString("page")

        viewModel.getMovieById(movieId ?: 0, page ?: "")
        initObserver()
    }

    private fun initObserver() {
        viewModel.movieLiveData.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Loading -> {
                    Log.d(HomeFragment.TAG, "Loading...")
                }
                is Resource.Success -> {
                    response.data?.let { movie ->
                        Log.d(HomeFragment.TAG, "Success!")
                        this.movie = movie

                        Log.d(HomeFragment.TAG, "LOGOBSERVER: ${this.movie}")

                        initView()
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e(HomeFragment.TAG, "Error: $message")
                        Snackbar.make(requireView(), "Error: $message", Snackbar.LENGTH_LONG)
                    }
                }
            }
        }
    }

    private fun initView() {
        binding.apply {
            Glide.with(requireContext())
                .load(BaseUrl.BASE_IMAGE_URL + movie.poster_path)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(ivMovie)

            val stringList = mutableListOf<String>()

            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            tvVoteAverage.text = String.format("%.1f", movie.vote_average)
            tvReleaseDate.text = DateFormat.getDateInstance().format(
                SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH).parse(movie.release_date) ?: ""
            )

            movie.genres?.forEach {
                stringList.add(it.name)
            }
            tvGenres.text = stringList.joinToString(", ")
            stringList.clear()

            movie.production_countries?.forEach {
                stringList.add(it.name)
            }
            tvProductionCountries.text = stringList.joinToString(", ")
            stringList.clear()

            movie.spoken_languages?.forEach {
                stringList.add(it.name)
            }
            tvSpokenLanguage.text = stringList.joinToString(", ")
            stringList.clear()

            val numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH)
            tvBudget.text = "\$${numberFormat.format(movie.budget)}"
            tvRevenue.text = "\$${numberFormat.format(movie.revenue)}"

            movie.production_companies?.forEach {
                stringList.add(it.name)
            }
            tvProductionCompanies.text = stringList.joinToString(", ")
            stringList.clear()
        }
    }

    private fun getCallerFragment(): String? {
        val fm = requireFragmentManager()
        val count = fm.backStackEntryCount
        return fm.getBackStackEntryAt(count-1).name
    }

    companion object {
        val TAG: String = this::class.java.simpleName
    }
}
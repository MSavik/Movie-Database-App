package com.msavik.movie_database_app.ui.home.movie

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.msavik.movie_database_app.ui.home.movie.page_fragments.PopularFragment
import com.msavik.movie_database_app.ui.home.movie.page_fragments.TopRatedFragment
import com.msavik.movie_database_app.ui.home.movie.page_fragments.UpcomingFragment

class HomePagerAdapter(private val fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                PopularFragment()
//                    .apply {
//                    arguments = fragment.requireArguments()
//                }
            }
            1 -> {
                TopRatedFragment()
            }
            2 -> {
                UpcomingFragment()
            }
            else -> throw Exception("No pages")
        }
    }

}
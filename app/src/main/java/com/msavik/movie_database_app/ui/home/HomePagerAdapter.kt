package com.msavik.movie_database_app.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.msavik.movie_database_app.ui.home.page_fragments.PopularFragment
import com.msavik.movie_database_app.ui.home.page_fragments.TopRatedFragment
import com.msavik.movie_database_app.ui.home.page_fragments.UpcomingFragment

class HomePagerAdapter(
    private val fragment: Fragment,
    private val fragmentFactory: FragmentFactory
    ) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                fragmentFactory.instantiate(
                    fragment.requireActivity().classLoader,
                    PopularFragment::class.java.name
                )
            }
            1 -> {
                fragmentFactory.instantiate(
                    fragment.requireActivity().classLoader,
                    TopRatedFragment::class.java.name
                )
            }
            2 -> {
                fragmentFactory.instantiate(
                    fragment.requireActivity().classLoader,
                    UpcomingFragment::class.java.name
                )
            }
            else -> throw Exception("No pages")
        }
    }

    override fun getItemId(position: Int): Long {
        return when(position) {
            0 -> 0
            1 -> 1
            2 -> 2
            else -> throw Exception("No pages")
        }
    }
}
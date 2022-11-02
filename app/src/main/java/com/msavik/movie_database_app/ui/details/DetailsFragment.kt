package com.msavik.movie_database_app.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.msavik.movie_database_app.R
import com.msavik.movie_database_app.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private lateinit var binding: FragmentDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)
    }
}
package com.example.kontrola.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.kontrola.R
import com.example.kontrola.viewmodel.ViewModel

class SettingsFragment : Fragment() {


    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        viewModel.setMenuAddIconVisibility(false)
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }
}
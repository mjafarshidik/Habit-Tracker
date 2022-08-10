package com.penatabahasa.habittracker.ui.onboarding.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.penatabahasa.habittracker.R
import com.penatabahasa.habittracker.databinding.FragmentOnBoardingOneBinding

class OnBoardingOneFragment : Fragment() {

    private var _binding: FragmentOnBoardingOneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
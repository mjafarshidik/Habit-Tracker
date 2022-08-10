package com.penatabahasa.habittracker.ui.onboarding.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.penatabahasa.habittracker.databinding.FragmentOnBoardingThreeBinding
import com.penatabahasa.habittracker.ui.list.HabitListActivity

class OnBoardingThreeFragment : Fragment() {
    private var _binding: FragmentOnBoardingThreeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingThreeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intentToMainActivity()

    }

    private fun intentToMainActivity() {
        binding.apply {
            btnNext.setOnClickListener {
                val intent = Intent(activity, HabitListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
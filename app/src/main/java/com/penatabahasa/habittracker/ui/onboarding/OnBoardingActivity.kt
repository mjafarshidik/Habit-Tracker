package com.penatabahasa.habittracker.ui.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.penatabahasa.habittracker.databinding.ActivityOnBoardingBinding
import com.penatabahasa.habittracker.ui.onboarding.fragments.OnBoardingOneFragment
import com.penatabahasa.habittracker.ui.onboarding.fragments.OnBoardingThreeFragment
import com.penatabahasa.habittracker.ui.onboarding.fragments.OnBoardingTwoFragment

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        showOnBoarding()
    }

    private fun showOnBoarding() {
        binding.apply {
            val fragmentList: ArrayList<Fragment> = arrayListOf(
                OnBoardingOneFragment(),
                OnBoardingTwoFragment(),
                OnBoardingThreeFragment()
            )

            val adapter = ViewPagerAdapter(fragmentList, this@OnBoardingActivity)
            vpOnBoarding.adapter = adapter
            dotsIndicator.setViewPager2(vpOnBoarding)
        }
    }
}
package com.penatabahasa.habittracker.ui.random

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.penatabahasa.habittracker.R
import com.penatabahasa.habittracker.databinding.ActivityRandomHabitBinding
import com.penatabahasa.habittracker.ui.ViewModelFactory
import com.penatabahasa.habittracker.ui.countdown.CountDownActivity
import com.penatabahasa.habittracker.utils.HABIT

class RandomHabitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRandomHabitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.apply {
            ivBack.setOnClickListener {
                onBackPressed()
            }
        }

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        val adapter = RandomHabitAdapter { habit ->
            val intent = Intent(this, CountDownActivity::class.java)
            intent.putExtra(HABIT, habit)
            startActivity(intent)
        }
        viewPager.adapter = adapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = "Habit ${position + 1}"
        }.attach()

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[RandomHabitViewModel::class.java]

//        if (!(viewModel.priorityLevelHigh.value != [] && viewModel.priorityLevelMedium.value != null && viewModel.priorityLevelLow.value != null)) {
//            Toast.makeText(
//                this,
//                "Please enter high, medium, low priority data first",
//                Toast.LENGTH_SHORT
//            ).show()
//        } else {
            viewModel.priorityLevelHigh.observe(this) {
                adapter.submitData(RandomHabitAdapter.PageType.HIGH, it)
            }
            viewModel.priorityLevelMedium.observe(this) {
                adapter.submitData(RandomHabitAdapter.PageType.MEDIUM, it)
            }
            viewModel.priorityLevelLow.observe(this) {
                adapter.submitData(RandomHabitAdapter.PageType.LOW, it)
            }
//        }
    }
}
package com.penatabahasa.habittracker.ui.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.penatabahasa.habittracker.R
import com.penatabahasa.habittracker.data.Habit
import com.penatabahasa.habittracker.ui.ViewModelFactory
import com.penatabahasa.habittracker.ui.countdown.CountDownActivity
import com.penatabahasa.habittracker.utils.HABIT
import com.penatabahasa.habittracker.utils.HABIT_ID

class DetailHabitActivity : AppCompatActivity() {

    private lateinit var selectedHabit: Habit
    private lateinit var viewModel: DetailHabitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_habit)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val habitId = intent.getIntExtra(HABIT_ID, 0)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailHabitViewModel::class.java]

        viewModel.start(habitId)
        viewModel.habit.observe(this) { habit ->
            if (habit != null) {
                selectedHabit = habit
                findViewById<TextView>(R.id.tvTitle).text = habit.title
                findViewById<EditText>(R.id.detail_ed_time_minutes).setText(habit.minutesFocus.toString())
                findViewById<EditText>(R.id.detail_ed_start_time).setText(habit.startTime)
            }
        }

        findViewById<Button>(R.id.btn_open_count_down).setOnClickListener {
            val intent = Intent(this, CountDownActivity::class.java)
            intent.putExtra(HABIT, selectedHabit)
            startActivity(intent)
        }
        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            onBackPressed()
        }
    }
}
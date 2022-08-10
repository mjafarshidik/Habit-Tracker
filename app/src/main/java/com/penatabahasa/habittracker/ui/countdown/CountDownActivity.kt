package com.penatabahasa.habittracker.ui.countdown

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.penatabahasa.habittracker.R
import com.penatabahasa.habittracker.data.Habit
import com.penatabahasa.habittracker.databinding.ActivityCountDownBinding
import com.penatabahasa.habittracker.notification.NotificationWorker
import com.penatabahasa.habittracker.utils.HABIT
import com.penatabahasa.habittracker.utils.HABIT_ID
import com.penatabahasa.habittracker.utils.HABIT_TITLE
import com.penatabahasa.habittracker.utils.NOTIF_UNIQUE_WORK
import java.util.concurrent.TimeUnit

class CountDownActivity : AppCompatActivity() {
    private lateinit var notificationWorker: OneTimeWorkRequest
    private lateinit var tvCountDown: TextView
    private lateinit var binding: ActivityCountDownBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountDownBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.apply {
            ivBack.setOnClickListener {
                onBackPressed()
            }
        }

        val habit = intent.getParcelableExtra<Habit>(HABIT) as Habit

        findViewById<TextView>(R.id.tv_count_down_title).text = habit.title
        tvCountDown = findViewById(R.id.tv_count_down)
        val data = workDataOf(
            HABIT_ID to habit.id,
            HABIT_TITLE to habit.title
        )

        notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInputData(data)
            .setInitialDelay(habit.minutesFocus, TimeUnit.MINUTES)
            .build()

        val viewModel = ViewModelProvider(this)[CountDownViewModel::class.java]

        //TODO 10 : Set initial time and observe current time. Update button state when countdown is finished
        viewModel.setInitialTime(habit.minutesFocus)
        viewModel.apply {
            currentTimeString.observe(this@CountDownActivity) {
                tvCountDown.text = it
            }
            eventCountDownFinish.observe(this@CountDownActivity) {
                updateButtonState(false)
            }
        }

        //TODO 13 : Start and cancel One Time Request WorkManager to notify when time is up.
        findViewById<Button>(R.id.btn_start).setOnClickListener {
            updateButtonState(true)
            viewModel.startTimer()
            WorkManager.getInstance(this).enqueueUniqueWork(
                NOTIF_UNIQUE_WORK,
                ExistingWorkPolicy.REPLACE,
                notificationWorker
            )
        }

        findViewById<Button>(R.id.btn_stop).setOnClickListener {
            updateButtonState(false)
            viewModel.resetTimer()
            WorkManager.getInstance(this).cancelUniqueWork(NOTIF_UNIQUE_WORK)
        }
    }

    private fun updateButtonState(isRunning: Boolean) {
        findViewById<Button>(R.id.btn_start).isEnabled = !isRunning
        findViewById<Button>(R.id.btn_stop).isEnabled = isRunning
    }
}
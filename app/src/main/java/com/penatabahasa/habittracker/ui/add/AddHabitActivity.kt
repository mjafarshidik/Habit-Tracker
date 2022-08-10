package com.penatabahasa.habittracker.ui.add

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.penatabahasa.habittracker.R
import com.penatabahasa.habittracker.data.Habit
import com.penatabahasa.habittracker.databinding.ActivityAddHabitBinding
import com.penatabahasa.habittracker.ui.ViewModelFactory
import com.penatabahasa.habittracker.utils.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.*

class AddHabitActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private lateinit var viewModel: AddHabitViewModel
    private lateinit var binding: ActivityAddHabitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[AddHabitViewModel::class.java]

        binding.apply {
            ivBack.setOnClickListener {
                onBackPressed()
            }
            ivCheck.setOnClickListener {
                binding.apply {
                    val title = findViewById<EditText>(R.id.add_ed_title).text.toString()
                    val minutesFocus =
                        findViewById<EditText>(R.id.add_ed_minutes_focus).text.toString()
                    val startTime = findViewById<TextView>(R.id.add_tv_start_time).text.toString()
                    val priorityLevel =
                        findViewById<Spinner>(R.id.sp_priority_level).selectedItem.toString()
                        if (title.isEmpty() || title == "") {
                            Toast.makeText(
                                this@AddHabitActivity,
                                "Please enter the name of the activity",
                                Toast.LENGTH_SHORT
                            ).show()
                    } else if (minutesFocus.isEmpty() || minutesFocus == "") {
                            Toast.makeText(
                                this@AddHabitActivity,
                                "Please enter the duration of the activity",
                                Toast.LENGTH_SHORT
                            ).show()
                    } else if (startTime == "Start Time") {
                        Toast.makeText(
                            this@AddHabitActivity,
                            "Please choose the time first",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val habit = Habit(
                            title = title,
                            minutesFocus = minutesFocus.toLong(),
                            startTime = startTime,
                            priorityLevel = priorityLevel
                        )
                        viewModel.saveHabit(habit)
                        finish()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    fun showTimePicker(view: View) {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, "timePicker")
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        findViewById<TextView>(R.id.add_tv_start_time).text = dateFormat.format(calendar.time)
    }
}
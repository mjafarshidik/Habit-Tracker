package com.penatabahasa.habittracker.ui.random

import androidx.lifecycle.*
import com.penatabahasa.habittracker.data.Habit
import com.penatabahasa.habittracker.data.HabitRepository

class RandomHabitViewModel(habitRepository: HabitRepository) : ViewModel() {
    val priorityLevelHigh: LiveData<Habit> = habitRepository.getRandomHabitByPriorityLevel("High")
    val priorityLevelMedium: LiveData<Habit> =
        habitRepository.getRandomHabitByPriorityLevel("Medium")
    val priorityLevelLow: LiveData<Habit> = habitRepository.getRandomHabitByPriorityLevel("Low")
}
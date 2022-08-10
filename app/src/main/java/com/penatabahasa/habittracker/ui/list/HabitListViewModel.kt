package com.penatabahasa.habittracker.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.paging.PagedList
import com.penatabahasa.habittracker.R
import com.penatabahasa.habittracker.data.Habit
import com.penatabahasa.habittracker.data.HabitRepository
import com.penatabahasa.habittracker.utils.Event
import com.penatabahasa.habittracker.utils.HabitSortType

class HabitListViewModel(private val habitRepository: HabitRepository) : ViewModel() {
    val priorityLevelHigh: LiveData<Habit> = habitRepository.getRandomHabitByPriorityLevel("High")
    val priorityLevelMedium: LiveData<Habit> =
        habitRepository.getRandomHabitByPriorityLevel("Medium")
    val priorityLevelLow: LiveData<Habit> = habitRepository.getRandomHabitByPriorityLevel("Low")

    private val _filter = MutableLiveData<HabitSortType>()

    val habits: LiveData<PagedList<Habit>> = _filter.switchMap {
        habitRepository.getHabits(it)
    }

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    private val _undo = MutableLiveData<Event<Habit>>()
    val undo: LiveData<Event<Habit>> = _undo

    init {
        _filter.value = HabitSortType.START_TIME
    }

    fun filter(filterType: HabitSortType) {
        _filter.value = filterType
    }

    fun deleteHabit(habit: Habit) {
        habitRepository.deleteHabit(habit)
        _snackbarText.value = Event(R.string.habit_deleted)
        _undo.value = Event(habit)
    }

    fun insert(habit: Habit) {
        habitRepository.insertHabit(habit)
    }
}
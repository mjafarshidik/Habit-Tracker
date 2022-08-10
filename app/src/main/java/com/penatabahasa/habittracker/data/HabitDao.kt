package com.penatabahasa.habittracker.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.penatabahasa.habittracker.data.Habit

@Dao
interface HabitDao {
    @RawQuery(observedEntities = [Habit::class])
    fun getHabits(query: SupportSQLiteQuery): DataSource.Factory<Int, Habit>

    @Query("SELECT * FROM habits WHERE id = :habitId")
    fun getHabitById(habitId: Int): LiveData<Habit>

    @Insert
    fun insertHabit(habit: Habit): Long

    @Insert
    fun insertAll(vararg habits: Habit)

    @Delete
    fun deleteHabit(habits: Habit)

    @Query("SELECT * FROM habits WHERE priorityLevel = :level ORDER BY RANDOM() LIMIT 1")
    fun getRandomHabitByPriorityLevel(level: String): LiveData<Habit>
}

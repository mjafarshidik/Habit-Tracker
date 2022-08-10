package com.penatabahasa.habittracker.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.penatabahasa.habittracker.R
import com.penatabahasa.habittracker.data.Habit

class HabitAdapter(
    private val onClick: (Habit) -> Unit
) : PagedListAdapter<Habit, HabitAdapter.HabitViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.habit_item, parent, false)
        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        //TODO 9 : Get data and bind them to ViewHolder
        val habit = getItem(position) as Habit
        holder.bind(habit)
    }

    inner class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle: TextView = itemView.findViewById(R.id.item_tv_title)
        private val ivPriority: ImageView = itemView.findViewById(R.id.item_priority_level)
        private val tvStartTime: TextView = itemView.findViewById(R.id.item_tv_start_time)
        private val tvMinutes: TextView = itemView.findViewById(R.id.item_tv_minutes)

        lateinit var getHabit: Habit
        fun bind(habit: Habit) {
            getHabit = habit
            tvTitle.text = habit.title
            tvStartTime.text = habit.startTime
            tvMinutes.text = habit.minutesFocus.toString()
            itemView.setOnClickListener {
                onClick(habit)
            }
            if (habit.priorityLevel == itemView.context.getString(R.string.low)) {
                ivPriority.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_priority_low
                    )
                )
            }
            if (habit.priorityLevel == itemView.context.getString(R.string.medium)) {
                ivPriority.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_priority_medium
                    )
                )
            }
            if (habit.priorityLevel == itemView.context.getString(R.string.high)) {
                ivPriority.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_priority_high
                    )
                )
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Habit>() {
            override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
                return oldItem == newItem
            }
        }
    }
}
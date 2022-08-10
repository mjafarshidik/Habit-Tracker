package com.penatabahasa.habittracker.ui.random

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.penatabahasa.habittracker.R
import com.penatabahasa.habittracker.data.Habit

class RandomHabitAdapter(
    private val onClick: (Habit) -> Unit
) : RecyclerView.Adapter<RandomHabitAdapter.PagerViewHolder>() {

    private val habitMap = LinkedHashMap<PageType, Habit>()

    fun submitData(key: PageType, habit: Habit) {
        habitMap[key] = habit
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PagerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.pager_item, parent, false)
        )

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val key = getIndexKey(position) ?: return
        val pageData = habitMap[key] ?: return
        holder.bind(key, pageData)
    }

    override fun getItemCount() = habitMap.size

    private fun getIndexKey(position: Int) = habitMap.keys.toTypedArray().getOrNull(position)

    enum class PageType {
        HIGH, MEDIUM, LOW
    }

    inner class PagerViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val pagerTvStartTime: TextView = itemView.findViewById(R.id.pager_tv_start_time)
        private val itemPriorityLevel: ImageView = itemView.findViewById(R.id.item_priority_level)
        private val pagerTvTitle: TextView = itemView.findViewById(R.id.pager_tv_title)
        private val pagerTvMinutes: TextView = itemView.findViewById(R.id.pager_tv_minutes)
        private val btnOpenCountDown: Button = itemView.findViewById(R.id.btn_open_count_down)
        fun bind(pageType: PageType, pageData: Habit) {
            pagerTvStartTime.text = pageData.startTime
            if (pageType == PageType.LOW) {
                itemPriorityLevel.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_priority_low
                    )
                )
            }
            if (pageType == PageType.MEDIUM) {
                itemPriorityLevel.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_priority_medium
                    )
                )
            }
            if (pageType == PageType.HIGH) {
                itemPriorityLevel.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_priority_high
                    )
                )
            }
            pagerTvTitle.text = pageData.title
            pagerTvMinutes.text = pageData.minutesFocus.toString()
            btnOpenCountDown.setOnClickListener {
                onClick(pageData)
            }
        }
    }
}

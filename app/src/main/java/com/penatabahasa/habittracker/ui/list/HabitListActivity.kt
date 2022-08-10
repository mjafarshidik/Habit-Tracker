package com.penatabahasa.habittracker.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.penatabahasa.habittracker.R
import com.penatabahasa.habittracker.data.Habit
import com.penatabahasa.habittracker.databinding.ActivityHabitListBinding
import com.penatabahasa.habittracker.setting.SettingsActivity
import com.penatabahasa.habittracker.ui.ViewModelFactory
import com.penatabahasa.habittracker.ui.add.AddHabitActivity
import com.penatabahasa.habittracker.ui.detail.DetailHabitActivity
import com.penatabahasa.habittracker.ui.random.RandomHabitActivity
import com.penatabahasa.habittracker.utils.Event
import com.penatabahasa.habittracker.utils.HABIT_ID
import com.penatabahasa.habittracker.utils.HabitSortType

class HabitListActivity : AppCompatActivity() {
    private lateinit var habitAdapter: HabitAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var viewModel: HabitListViewModel
    private lateinit var binding: ActivityHabitListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHabitListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val addIntent = Intent(this, AddHabitActivity::class.java)
            startActivity(addIntent)
        }

        recycler = findViewById(R.id.rv_habit)
        recycler.layoutManager = GridLayoutManager(this, 2)
        habitAdapter = HabitAdapter {
            val intent = Intent(this, DetailHabitActivity::class.java)
            intent.putExtra(HABIT_ID, it.id)
            startActivity(intent)
        }
        recycler.adapter = habitAdapter
        initAction()

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[HabitListViewModel::class.java]

        viewModel.apply {
            habits.observe(this@HabitListActivity) {
                habitAdapter.submitList(it)
            }
            snackbarText.observe(
                this@HabitListActivity,
                Observer(this@HabitListActivity::showSnackBar)
            )
        }

        binding.apply {
            btnSetting.setOnClickListener {
                val intent = Intent(this@HabitListActivity, SettingsActivity::class.java)
                startActivity(intent)
            }
            tvFilter.setOnClickListener {
                showFilteringPopUpMenu()
            }
            btnRandom.setOnClickListener {
//                val viewModel = ViewModelProvider(
//                    this@HabitListActivity,
//                    factory
//                )[HabitListViewModel::class.java]
//                if (viewModel.priorityLevelHigh.value != null || viewModel.priorityLevelMedium.value!= null || viewModel.priorityLevelLow.value != null) {
                    val intent = Intent(this@HabitListActivity, RandomHabitActivity::class.java)
                    startActivity(intent)
//                } else {
//                    Toast.makeText(
//                        this@HabitListActivity,
//                        "Please enter high, medium, low priority data first",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
            }
        }
    }

    private fun showSnackBar(eventMessage: Event<Int>) {
        val message = eventMessage.getContentIfNotHandled() ?: return
        Snackbar.make(
            findViewById(R.id.coordinator_layout),
            getString(message),
            Snackbar.LENGTH_SHORT
        ).setAction("Undo") {
            viewModel.insert(viewModel.undo.value?.getContentIfNotHandled() as Habit)
        }.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun showFilteringPopUpMenu() {
        val view = findViewById<View>(R.id.tvFilter) ?: return
        PopupMenu(this, view).run {
            menuInflater.inflate(R.menu.filter_habits, menu)

            setOnMenuItemClickListener {
                viewModel.filter(
                    when (it.itemId) {
                        R.id.minutes_focus -> HabitSortType.MINUTES_FOCUS
                        R.id.title_name -> HabitSortType.TITLE_NAME
                        else -> HabitSortType.START_TIME
                    }
                )
                true
            }
            show()
        }
    }

    private fun initAction() {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0, ItemTouchHelper.RIGHT)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val habit = (viewHolder as HabitAdapter.HabitViewHolder).getHabit
                viewModel.deleteHabit(habit)
            }
        })
        itemTouchHelper.attachToRecyclerView(recycler)
    }
}

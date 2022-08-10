package com.penatabahasa.habittracker.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.penatabahasa.habittracker.R
import com.penatabahasa.habittracker.ui.list.HabitListActivity
import com.penatabahasa.habittracker.ui.onboarding.OnBoardingActivity
import kotlinx.coroutines.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private var time = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        withCoroutine(time)
    }

    private fun withCoroutine(time: Long) {
        val mScope = CoroutineScope(Dispatchers.IO)
        mScope.launch {
            delay(time)
            withContext(Dispatchers.Main) {
                launchPostSplashActivity()
                mScope.cancel(null)
            }
        }
    }

    private fun launchPostSplashActivity() {
        val intent = Intent(this, OnBoardingActivity::class.java)
        startActivity(intent)
        finish()
    }
}
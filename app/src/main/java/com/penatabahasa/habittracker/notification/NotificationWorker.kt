package com.penatabahasa.habittracker.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.penatabahasa.habittracker.R
import com.penatabahasa.habittracker.ui.detail.DetailHabitActivity
import com.penatabahasa.habittracker.utils.HABIT_ID
import com.penatabahasa.habittracker.utils.HABIT_TITLE
import com.penatabahasa.habittracker.utils.NOTIFICATION_CHANNEL_ID

class NotificationWorker(val ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val habitId = inputData.getInt(HABIT_ID, 0)
    private val habitTitle = inputData.getString(HABIT_TITLE)

    private fun getPendingIntent(): PendingIntent? {
        val intent = Intent(applicationContext, DetailHabitActivity::class.java).apply {
            putExtra(HABIT_ID, habitId)
        }
        return TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    override fun doWork(): Result {
        val prefManager =
            androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val shouldNotify =
            prefManager.getBoolean(applicationContext.getString(R.string.pref_key_notify), false)

        //TODO 12 : If notification preference on, show notification with pending intent
        if (shouldNotify) {
            val pendingIntent = getPendingIntent()
            val notificationManagerCompat =
                ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationBuilder = NotificationCompat.Builder(ctx, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(habitTitle)
                .setContentText(ctx.getString(R.string.notify_content))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    ctx.getString(R.string.notify_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID)
                notificationManagerCompat.createNotificationChannel(channel)
            }

            val notification = notificationBuilder.build()
            notificationManagerCompat.notify(NOTIFICATIOON_CODE_REMINDER, notification)
        }
        return Result.success()
    }

    companion object {
        const val NOTIFICATIOON_CODE_REMINDER = 100
    }
}

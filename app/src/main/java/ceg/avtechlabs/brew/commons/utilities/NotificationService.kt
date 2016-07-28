package ceg.avtechlabs.brew.commons.utilities

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Vibrator
import android.support.design.R
import android.support.v4.app.NotificationCompat

/**
 * Created by adhithyan-3592 on 27/07/16.
 */

fun showNotification(act: Context, title: String, message: String) {
    val builder = NotificationCompat.Builder(act)
        .setSmallIcon(R.drawable.notification_template_icon_bg)
        .setContentTitle(title)
        .setContentText(message)

    val manager = act.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    manager.notify(1, builder.build())
}

fun startService(context: Context, time: Int) {
    val intent = Intent(context, NotificationService::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, 3592, intent, 0)
    val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarm.set(AlarmManager.RTC_WAKEUP, time + System.currentTimeMillis(), pendingIntent)
    //recurring(context)
}

fun recurring(context: Context) {
    //val afterADay = 24 * 60 * 60 * 1000;
    startService(context, 6000)
}

fun vibrate(context: Context) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    vibrator.vibrate(1000)
}
class NotificationService(): BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        vibrate(context)
        showNotification(context, "Brew ! - Surprise in store", "Tap here to know what's brewing today" + System.currentTimeMillis())
    }

}
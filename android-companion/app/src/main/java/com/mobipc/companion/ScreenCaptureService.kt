package com.mobipc.companion

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.IBinder

class ScreenCaptureService : Service() {
    private var projection: MediaProjection? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val channelId = "mobipc_screen_share"
        val manager = getSystemService(NotificationManager::class.java)
        if (Build.VERSION.SDK_INT >= 26) {
            manager.createNotificationChannel(NotificationChannel(channelId, "MobiPC screen sharing", NotificationManager.IMPORTANCE_LOW))
        }
        val notification: Notification = if (Build.VERSION.SDK_INT >= 26) {
            Notification.Builder(this, channelId).setContentTitle("MobiPC Connect is sharing screen")
                .setContentText("Sharing is active. Tap Stop in the app to end.")
                .setSmallIcon(android.R.drawable.ic_menu_view).build()
        } else {
            @Suppress("DEPRECATION")
            Notification.Builder(this).setContentTitle("MobiPC Connect is sharing screen")
                .setContentText("Sharing is active.").setSmallIcon(android.R.drawable.ic_menu_view).build()
        }
        startForeground(77, notification)
        val resultCode = intent?.getIntExtra("resultCode", 0) ?: 0
        val resultData = if (Build.VERSION.SDK_INT >= 33) intent?.getParcelableExtra("resultData", Intent::class.java) else {
            @Suppress("DEPRECATION")
            intent?.getParcelableExtra("resultData")
        }
        if (resultData != null) {
            val mgr = getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
            projection = mgr.getMediaProjection(resultCode, resultData)
        }
        // Consent and visible foreground service foundation only. No frames are captured or transmitted yet.
        return START_NOT_STICKY
    }
    override fun onDestroy() { projection?.stop(); projection = null; super.onDestroy() }
    override fun onBind(intent: Intent?): IBinder? = null
}

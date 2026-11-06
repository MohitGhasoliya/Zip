package com.mobipc.companion

import android.app.Activity
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    private val requestCode = 4102
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(36, 64, 36, 36)
            setBackgroundColor(0xFF101827.toInt())
        }
        val title = TextView(this).apply {
            text = "MobiPC Connect\n\nScreen sharing"
            textSize = 25f
            setTextColor(0xFFFFFFFF.toInt())
        }
        val info = TextView(this).apply {
            text = "Screen sharing starts only after you approve Android's system prompt. A persistent notification stays visible while sharing. This starter does not yet stream to another device."
            textSize = 15f
            setTextColor(0xFFCAD5E5.toInt())
            setPadding(0, 28, 0, 28)
        }
        val start = Button(this).apply {
            text = "Allow screen sharing"
            setOnClickListener {
                val manager = getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
                startActivityForResult(manager.createScreenCaptureIntent(), requestCode)
            }
        }
        val stop = Button(this).apply {
            text = "Stop sharing"
            setOnClickListener { stopService(Intent(this@MainActivity, ScreenCaptureService::class.java)) }
        }
        root.addView(title); root.addView(info); root.addView(start); root.addView(stop)
        setContentView(root)
    }

    @Deprecated("Deprecated in Android API; kept for broad starter compatibility")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == this.requestCode && resultCode == RESULT_OK && data != null) {
            val service = Intent(this, ScreenCaptureService::class.java).apply {
                putExtra("resultCode", resultCode)
                putExtra("resultData", data)
            }
            startForegroundService(service)
        }
    }
}

package ru.SnowVolf.pcompiler.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.transition.Explode
import android.transition.Fade
import android.view.WindowManager

import ru.SnowVolf.pcompiler.util.LocaleGirl
import ru.SnowVolf.pcompiler.util.ThemeWrapper

/**
 * Created by Snow Volf on 19.08.2017, 12:09
 */

open class BaseActivity : AppCompatActivity() {
    //Theme
    private val mThemeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (SettingsActivity::class.java == this@BaseActivity.javaClass) {
                finish()
                startActivity(getIntent())
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            } else
                recreate()
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleGirl.onAttach(base))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        LocalBroadcastManager.getInstance(this).registerReceiver(mThemeReceiver, IntentFilter("org.openintents.action.REFRESH_THEME"))
        ThemeWrapper.applyTheme(this)
        super.onCreate(savedInstanceState)

        window.enterTransition = Explode()
        window.exitTransition = Fade()
        window.allowEnterTransitionOverlap = true
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

    public override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mThemeReceiver)
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAfterTransition()
    }
}

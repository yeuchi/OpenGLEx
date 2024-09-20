package com.ctyeung.openglex.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.SurfaceView
import androidx.databinding.DataBindingUtil
import com.ctyeung.openglex.MainActivity
import com.ctyeung.openglex.R
import com.ctyeung.openglex.basic.BasicGLSurfaceView
import com.ctyeung.openglex.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {
    private var mAndroidSurface: SurfaceView? = null
    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView (this, R.layout.activity_base)

        mAndroidSurface = GLSurfaceView(this)
        setContentView(mAndroidSurface)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        when (itemId) {
            android.R.id.home -> {
                startActivity(Intent(this, MainActivity::class.java))
            }

            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}
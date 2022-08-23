package com.ctyeung.openglex.basic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceView
import com.ctyeung.openglex.R

class BasicActivity : AppCompatActivity() {
    var mAndroidSurface: SurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic)

        mAndroidSurface = BasicGLSurfaceView(this)
        setContentView(mAndroidSurface)
    }
}
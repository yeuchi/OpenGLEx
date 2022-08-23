package com.ctyeung.openglex.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceView
import com.ctyeung.openglex.R
import com.ctyeung.openglex.basic.BasicGLSurfaceView

class BaseActivity : AppCompatActivity() {
    var mAndroidSurface: SurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        mAndroidSurface = GLSurfaceView(this)
        setContentView(mAndroidSurface)
    }
}
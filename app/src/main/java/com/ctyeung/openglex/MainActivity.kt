package com.ctyeung.openglex

import android.os.Bundle
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity

/*
 * OpenGL exercise - Bring up to date from demos in text.
 * Android Wireless Application Development by Lauren Darcey
 */
class MainActivity : AppCompatActivity() {

    var mAndroidSurface: SurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        mAndroidSurface = BasicGLSurfaceView(this)
        mAndroidSurface = GLSurfaceView(this)
        setContentView(mAndroidSurface)
    }
}
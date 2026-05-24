package com.ctyeung.openglex.demo

import android.app.Activity
import android.opengl.GLSurfaceView
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ctyeung.openglex.R

/*
 * Google developer Open GL lessons
 * https://developer.android.com/develop/ui/views/graphics/opengl
 */
class OpenGLES20Activity : Activity() {

    private lateinit var gLView: GLSurfaceView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        gLView = MyGLSurfaceView(this)
        setContentView(gLView)
    }
}
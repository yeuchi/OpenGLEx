package com.ctyeung.openglex.demo

import android.content.Context
import android.opengl.GLSurfaceView

/**
 * Google Developer example
 * https://developer.android.com/develop/ui/views/graphics/opengl/draw
 */
class MyGLSurfaceView(context: Context) : GLSurfaceView(context) {

    private val renderer: MyGLRenderer

    init {

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2)

        renderer = MyGLRenderer()

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
    }
}

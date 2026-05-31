package com.ctyeung.openglex.offGL

import android.content.Context
import android.opengl.GLSurfaceView
import com.ctyeung.openglex.off.OffDecoder

/**
 * Google Developer example
 * https://developer.android.com/develop/ui/views/graphics/opengl/draw
 */
class MyOFFGLSurfaceView(
    context: Context,
    off: OffDecoder
) : GLSurfaceView(context) {

    private val renderer: MyOFFGLRenderer

    init {

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2)

        renderer = MyOFFGLRenderer(off)

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
    }
}

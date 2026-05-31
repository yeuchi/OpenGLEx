package com.ctyeung.openglex.offGL

import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.ctyeung.openglex.off.OffDecoder

/**
 * Google Developer example
 * https://developer.android.com/develop/ui/views/graphics/opengl/draw
 */
class MyOFFGLRenderer(val off: OffDecoder) : GLSurfaceView.Renderer {

    private val listTriangle = arrayListOf<OFFTriangle>()

    override fun onSurfaceCreated(unused: GL10, config: EGLConfig) {
        // Set the background frame color
        // GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)

        // initialize a triangle
        listTriangle.clear()
        off.listFaces.forEach { face ->
            val traingle = OFFTriangle(face, off.listVertices, off.meshBound)
            listTriangle.add(traingle)
        }
    }

    override fun onDrawFrame(unused: GL10) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        listTriangle.forEach {
            it.draw()
        }
    }

    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }


}
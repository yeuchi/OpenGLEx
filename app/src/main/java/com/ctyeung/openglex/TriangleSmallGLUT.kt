package com.ctyeung.openglex

import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10

/*
 * Source from Android Wireless Application Develop by Lauren Darcey
 */
class TriangleSmallGLUT internal constructor(size: Float)  {
    private val mVertexBuffer: FloatBuffer
    private val mColorBuffer: FloatBuffer
    fun draw(gl: GL10) {
        gl.glFrontFace(GL10.GL_CW)
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer)
        gl.glNormal3f(0f, 0f, 1f)
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3)
    }

    fun drawColorful(gl: GL10) {
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY)
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer)
        draw(gl)
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY)
    }

    var colors = floatArrayOf(
        1f, 0f, 0f, 1f, 0f, 1f, 0f, 1f, 0f, 0f, 1f, 1f
    )
    var vertices = floatArrayOf(
        -0.559016994f, 0f, 0f,
        0.25f, 0.5f, 0f,
        0.25f, -0.5f, 0f
    )

    init {
        if (size != 1.0f) {
            for (vertex in vertices.indices) {
                vertices[vertex] *= size
            }
        }
        mVertexBuffer = SmallGLUT.getFloatBufferFromFloatArray(vertices)
        mColorBuffer = SmallGLUT.getFloatBufferFromFloatArray(colors)
    }
}
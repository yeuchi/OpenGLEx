package com.ctyeung.openglex

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer

/*
 * Source from Android Wireless Application Develop by Lauren Darcey
 */
object SmallGLUT {
    const val PI = 3.14159265358979323846f
    fun getByteBufferFromByteArray(array: ByteArray): ByteBuffer {
        val buffer = ByteBuffer.allocateDirect(array.size)
        buffer.put(array)
        buffer.position(0)
        return buffer
    }

    fun getFloatBufferFromFloatArray(array: FloatArray): FloatBuffer {
        val tempBuffer = ByteBuffer.allocateDirect(array.size * 4)
        tempBuffer.order(ByteOrder.nativeOrder())
        val buffer = tempBuffer.asFloatBuffer()
        buffer.put(array)
        buffer.position(0)
        return buffer
    }

    fun getIntBufferFromIntArray(array: IntArray): IntBuffer {
        val tempBuffer = ByteBuffer.allocateDirect(array.size * 4)
        tempBuffer.order(ByteOrder.nativeOrder())
        val buffer = tempBuffer.asIntBuffer()
        buffer.put(array)
        buffer.position(0)
        return buffer
    }
}
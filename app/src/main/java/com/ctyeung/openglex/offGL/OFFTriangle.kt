package com.ctyeung.openglex.offGL

import android.opengl.GLES20
import com.ctyeung.openglex.geometry.PointF3D
import com.ctyeung.openglex.off.MeshBound
import com.ctyeung.openglex.off.OffFace
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import kotlin.collections.arrayListOf
import kotlin.math.cos
import kotlin.math.sin

/**
 * Google Developer example
 * https://developer.android.com/develop/ui/views/graphics/opengl/draw
 */

// number of coordinates per vertex in this array
const val COORDS_PER_VERTEX = 3


class OFFTriangle ( val face: OffFace,
                    val vertices: ArrayList<PointF3D>,
                    val meshBound: MeshBound){

    private val vertexShaderCode =
        "attribute vec4 vPosition;" +
                "void main() {" +
                "  gl_Position = vPosition;" +
                "}"

    private val fragmentShaderCode =
        "precision mediump float;" +
                "uniform vec4 vColor;" +
                "void main() {" +
                "  gl_FragColor = vColor;" +
                "}"
    var triangleCoords = floatArrayOf(     // in counterclockwise order:
        0.0f, 0.622008459f, 0.0f,      // top
        -0.5f, -0.311004243f, 0.0f,    // bottom left
        0.5f, -0.311004243f, 0.0f      // bottom right
    )

    // Set color with red, green, blue and alpha (opacity) values
    val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)

    init {
        val radX = Math.PI / 180.0 * 0
        val radY = Math.PI / 180.0 * 0
        val radZ = Math.PI / 180.0 * 0

        val center = meshBound.getCenter()

        for(j in 0 until (face.list[0]-1)) {
            val vIndex = face.list[j + 1];
            if (vIndex >= vertices.size || vIndex < 0) {
                break
            }
            // retrieve vertex
            val vtx1 = vertices[vIndex].let { PointF3D(it.x-center.x, it.y-center.y, it.z-center.z) }

            val y = cos(radX) * vtx1.y - sin(radX) * vtx1.z
            val z = sin(radX) * vtx1.y + cos(radX) * vtx1.z

            val x = cos(radY) * vtx1.x + sin(radY) * z
            //val zz = -sin(radY) * vtx1.x + cos(radY) * z

            triangleCoords[j*3] = x.toFloat()
            triangleCoords[j*3+1] = y.toFloat()
            triangleCoords[j*3+2] = z.toFloat()
        }
    }

    private var vertexBuffer: FloatBuffer =
        // (number of coordinate values * 4 bytes per float)
        ByteBuffer.allocateDirect(triangleCoords.size * 4).run {
            // use the device hardware's native byte order
            order(ByteOrder.nativeOrder())

            // create a floating point buffer from the ByteBuffer
            asFloatBuffer().apply {
                // add the coordinates to the FloatBuffer
                put(triangleCoords)
                // set the buffer to read the first coordinate
                position(0)
            }
        }


        private var mProgram: Int

        init {
            val vertexShader: Int = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
            val fragmentShader: Int = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)

            // create empty OpenGL ES Program
            mProgram = GLES20.glCreateProgram().also {

                // add the vertex shader to program
                GLES20.glAttachShader(it, vertexShader)

                // add the fragment shader to program
                GLES20.glAttachShader(it, fragmentShader)

                // creates OpenGL ES program executables
                GLES20.glLinkProgram(it)
            }
        }

    fun loadShader(type: Int, shaderCode: String): Int {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        return GLES20.glCreateShader(type).also { shader ->

            // add the source code to the shader and compile it
            GLES20.glShaderSource(shader, shaderCode)
            GLES20.glCompileShader(shader)
        }
    }

    private var positionHandle: Int = 0
    private var mColorHandle: Int = 0

    private val vertexCount: Int = triangleCoords.size / COORDS_PER_VERTEX
    private val vertexStride: Int = COORDS_PER_VERTEX * 4 // 4 bytes per vertex

    fun draw() {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram)

        // get handle to vertex shader's vPosition member
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition").also {

            // Enable a handle to the triangle vertices
            GLES20.glEnableVertexAttribArray(it)

            // Prepare the triangle coordinate data
            GLES20.glVertexAttribPointer(
                it,
                COORDS_PER_VERTEX,
                GLES20.GL_FLOAT,
                false,
                vertexStride,
                vertexBuffer
            )

            // get handle to fragment shader's vColor member
            mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor").also { colorHandle ->

                // Set color for drawing the triangle
                GLES20.glUniform4fv(colorHandle, 1, color, 0)
            }

            // Draw the triangle
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)

            // Disable vertex array
            GLES20.glDisableVertexAttribArray(it)
        }
    }
}
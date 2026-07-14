package com.ctyeung.openglex.offGL

import android.opengl.GLES20
import android.opengl.GLES20.GL_UNSIGNED_SHORT
import com.ctyeung.openglex.geometry.PointF3D
import com.ctyeung.openglex.off.MeshBound
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer
import kotlin.math.cos
import kotlin.math.sin

/*
 * TODO need to scale
 */
class OffShape(val faces: ShortArray,
               val normals: ShortArray,
               val verticies: ArrayList<PointF3D>,
               val meshBound: MeshBound) {

    private val vertexShaderCode = """
        attribute vec4 vPosition;
        uniform float radX;
        uniform float radY;

        const float PI = 3.1415926535897932384626433832795;

        mat4 rotate(float radX, float radY) {
          mat4 rotationMatrix;
          rotationMatrix[0] = vec4(cos(radY), 0, sin(radY), 0);
          rotationMatrix[1] = vec4(0, cos(radX), -sin(radX), 0);
          rotationMatrix[2] = vec4(0, sin(radX), cos(radX), 0);
          rotationMatrix[3] = vec4(0, 0, 0, 1);
          return rotationMatrix;
        }

        void main() {
          mat4 rotatedModelMatrix = rotate(radX, radY);
          gl_Position = rotatedModelMatrix * vPosition;
        }
    """.trimIndent()

    private val fragmentShaderCode =
        "precision mediump float;" +
                "uniform vec4 vColor;" +
                "void main() {" +
                "  gl_FragColor = vColor;" +
                "}"

    val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)
    var rotateX = 90
    val center = meshBound.getCenter()

    val xScale =  1 / (meshBound.maxX - meshBound.minX)
    val yScale =  1 / (meshBound.maxY - meshBound.minY)
    val zScale =  1 / (meshBound.maxZ - meshBound.minZ)

    /**
     * initialize vertex byte buffer for shape coordinates
     */
    private fun getVertexBuffer(): FloatBuffer {
        /*
         * TODO migrate all this stuff into vertex and fragment shaders
         */
        val TRIANGLE_THREE_POINTS = 3
        var index = 0
        var coords = FloatArray(verticies.size * TRIANGLE_THREE_POINTS)
        verticies.forEach { vertex ->
            val vX = (vertex.x - center.x) * xScale
            val vY = (vertex.y - center.y) * yScale
            val vZ = (vertex.z - center.z) * zScale

            coords[index++] = vX
            coords[index++] = vY
            coords[index++] = vZ
        }

        // (# of coordinate values * 4 bytes per float)
        val buffer = ByteBuffer.allocateDirect(coords.size * 4).run {
            order(ByteOrder.nativeOrder())
            asFloatBuffer().apply {
                put(coords)
                position(0)
            }
        }
        return buffer
    }

    // initialize byte buffer for the draw list
    private val drawListBuffer: ShortBuffer =
        // (# of coordinate values * 2 bytes per short)
        ByteBuffer.allocateDirect(faces.size * 2).run {
            order(ByteOrder.nativeOrder())
            asShortBuffer().apply {
                put(faces)
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

    private val vertexStride: Int = com.ctyeung.openglex.demo.COORDS_PER_VERTEX * 4 // 4 bytes per vertex
    private val radianY = (Math.PI / 180.0 * 0).toFloat()

    /**
     * Draw
     *  1. pass arguments to shaders
     *  2. draw elements
     */
    fun draw() {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram)

        // get handle to fragment shader's vColor member
        GLES20.glGetUniformLocation(mProgram, "vColor").also { colorHandle ->

            // Set color for drawing the triangle
            GLES20.glUniform4fv(colorHandle, 1, color, 0)
        }

        rotateX += 2
        if(rotateX>360) {
            rotateX -= 360
        }
        GLES20.glGetUniformLocation(mProgram, "radX").also { radX ->
            GLES20.glUniform1f(radX, (Math.PI / 180.0 * rotateX).toFloat())
        }
        GLES20.glGetUniformLocation(mProgram, "radY").also { radX ->
            GLES20.glUniform1f(radX, radianY)
        }

        // get handle to vertex shader's vPosition member
        val vPositions = GLES20.glGetAttribLocation(mProgram, "vPosition").also {

            // Enable a handle to the triangle vertices
            GLES20.glEnableVertexAttribArray(it)

            // Prepare the triangle coordinate data
            GLES20.glVertexAttribPointer(
                it,
                com.ctyeung.openglex.demo.COORDS_PER_VERTEX,
                GLES20.GL_FLOAT,
                false,
                vertexStride,
                getVertexBuffer()
            )
        }

        // Draw the triangles
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, faces.size, GL_UNSIGNED_SHORT, drawListBuffer)

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(vPositions)
    }
}

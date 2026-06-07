package com.ctyeung.openglex.offGL

import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.ctyeung.openglex.geometry.PointF3D
import com.ctyeung.openglex.off.MeshBound
import com.ctyeung.openglex.off.OffDecoder
import com.ctyeung.openglex.off.OffFace
import java.nio.ByteBuffer
import kotlin.collections.forEach
import kotlin.math.cos
import kotlin.math.sin

/**
 * Google Developer example
 * https://developer.android.com/develop/ui/views/graphics/opengl/draw
 */
class MyOFFGLRenderer(val off: OffDecoder) : GLSurfaceView.Renderer {

    private lateinit var offShape : OffShape

    override fun onSurfaceCreated(unused: GL10, config: EGLConfig) {
        // Set the background frame color
        // GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)

        // initialize a triangle
        offShape = OffShape(faces = getDrawOrders(off.listFaces),
            verticies = off.listVertices,
            meshBound = off.meshBound)
    }

    override fun onDrawFrame(unused: GL10) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        offShape.draw()
    }

    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    protected fun getCoordinates(verticies:ArrayList<PointF3D>,
                                 meshBound: MeshBound): FloatArray {

        val radX = Math.PI / 180.0 * 0
        val radY = Math.PI / 180.0 * 0
        val radZ = Math.PI / 180.0 * 0

        val TRIANGLE_THREE_POINTS = 3
        var index = 0
        val center = meshBound.getCenter()

        val xScale =  1 / (meshBound.maxX - meshBound.minX)
        val yScale =  1 / (meshBound.maxY - meshBound.minY)
        val zScale =  1 / (meshBound.maxZ - meshBound.minZ)
        var squareCoords = FloatArray(verticies.size * TRIANGLE_THREE_POINTS)
        verticies.forEach { vertex ->

            /*
             * TODO Scale -1 to 1
             */
//            val vtx1 = PointF3D(vertex.x-center.x, vertex.y-center.y, vertex.z-center.z)
//
//            /*
//             * TODO Rotate
//             */
//            val y = cos(radX) * vtx1.y - sin(radX) * vtx1.z
//            val z = sin(radX) * vtx1.y + cos(radX) * vtx1.z
//
//            val x = cos(radY) * vtx1.x + sin(radY) * z
//
//            squareCoords[index++] = x.toFloat()
//            squareCoords[index++] = y.toFloat()
//            squareCoords[index++] = z.toFloat()


            squareCoords[index++] = (vertex.x - center.x) * xScale
            squareCoords[index++] = (vertex.y - center.y) * yScale
            squareCoords[index++] = (vertex.z - center.z) * zScale
        }
        return squareCoords
    }

    protected fun getDrawOrders(faces:ArrayList<OffFace>): ShortArray {
        val TRIANGLE_THREE_POINTS = 3
        var index = 0
        var drawOrder = ShortArray(faces.size * TRIANGLE_THREE_POINTS)
        faces.forEach {face ->
            drawOrder[index++] = face.list[0]
            drawOrder[index++] = face.list[1]
            drawOrder[index++] = face.list[2]
        }
        return drawOrder
    }
}
package com.ctyeung.openglex.off

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import com.ctyeung.openglex.geometry.PointF3D
import kotlin.math.cos
import kotlin.math.sin

class MyKnotsView(
    context: Context,
    attrs: AttributeSet
) :
    View(context, attrs) {

    private val dotColor = Color.BLUE
    private val lineColor = Color.GREEN

    private var _vertices = arrayListOf<PointF3D>()
    private var _faces = arrayListOf<OffFace>()

    private var _rotateX:Float = 0F
    private var _rotateY:Float = 0F
    private var _rotateZ:Float = 0F

    private lateinit var _meshBound: MeshBound

    public fun setRotation(rX:Float, rY:Float, rZ:Float) {
        _rotateX = rX
        _rotateY = rY
        _rotateZ = rZ
        invalidate()
    }

    public fun setData(faces: ArrayList<OffFace>, vertices: ArrayList<PointF3D>, meshBound: MeshBound) {
        _faces = faces
        _vertices = vertices
        _meshBound = meshBound
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        drawWireframe(canvas)
        //drawKnots(canvas)
        //drawLine(canvas)
    }

    private fun drawWireframe(canvas: Canvas) {
        _faces.forEach { face ->
            drawTriangles(canvas, face)
        }
    }


    private fun drawTriangles(canvas: Canvas, face: OffFace): Boolean {

        val radX = Math.PI / 180.0 * _rotateX
        val radY = Math.PI / 180.0 * _rotateY
        val radZ = Math.PI / 180.0 * _rotateZ

        val mag = 5F

        Paint().let { paint ->
            paint.isAntiAlias = true
            paint.strokeWidth = 3f
            paint.style = Paint.Style.STROKE
            paint.strokeJoin = Paint.Join.ROUND
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = lineColor

            val halfWidth = canvas.width/2F
            val halfHeight = canvas.height/2F

            var last:PointF = PointF()
            for(j in 0 until (face.list[0]-1)) {
                val vIndex = face.list[j+1];
                if(vIndex >=_vertices.size||vIndex<0) {
                    return false;
                }
                // retrieve vertex
                val vtx1 = _vertices[vIndex]

                val y = cos(radX) * vtx1.y - sin(radX) * vtx1.z
                val z = sin(radX) * vtx1.y + cos(radX) * vtx1.z

                val x = cos(radY) * vtx1.x + sin(radY) * z
                //val zz = -sin(radY) * vtx1.x + cos(radY) * z

                if(j==0){
                    last = PointF((x*mag+halfWidth).toFloat(),(y*mag+halfHeight).toFloat())
                }
                else {
                    val path = Path()
                    path.moveTo(last.x, last.y)
                    val p = PointF((x*mag+halfWidth).toFloat(),(y*mag+halfHeight).toFloat())
                    path.lineTo(p.x, p.y)
                    canvas.drawPath(path, paint)
                    last = p
                }
            }
        }
        return true
    }

    private fun drawKnots(canvas: Canvas) {
        Paint().let { paint ->
            paint.style = Paint.Style.FILL
            paint.color = dotColor

            val halfWidth = canvas.width/2F
            val halfHeight = canvas.height/2F
            _vertices.apply {
                for (p in _vertices) {
                    // highlight point
                    canvas.drawCircle((p.y * 10F + halfWidth), (p.z * 10F + halfHeight), 5f, paint)
                }
            }
        }
    }
}
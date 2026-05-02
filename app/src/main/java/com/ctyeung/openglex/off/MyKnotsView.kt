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

class MyKnotsView(
    context: Context,
    attrs: AttributeSet
) :
    View(context, attrs) {

    private val dotColor = Color.BLUE
    private val lineColor = Color.GREEN

    private var _knots = arrayListOf<PointF3D>()

    public fun setVertices(knots: ArrayList<PointF3D>) {
        _knots = knots
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        drawKnots(canvas)
        //drawLine(canvas)
    }

    private fun drawKnots(canvas: Canvas) {
        Paint().let { paint ->
            paint.style = Paint.Style.FILL
            paint.color = dotColor

            val halfWidth = canvas.width/2F
            val halfHeight = canvas.height/2F
            _knots.apply {
                for (p in _knots) {
                    // highlight point
                    canvas.drawCircle((p.y * 10F + halfWidth), (p.z * 10F + halfHeight), 5f, paint)
                }
            }
        }
    }

    private fun drawLine(canvas: Canvas) {
        Paint().let { paint ->
            paint.isAntiAlias = true
            paint.strokeWidth = 3f
            paint.style = Paint.Style.STROKE
            paint.strokeJoin = Paint.Join.ROUND
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = lineColor

            val size = _knots.size
            if (size > 1) {
                val path = Path()
                for (i in 0..size - 2) {
                    val p = _knots[i]
                    val pp = _knots[i + 1]
                    path.moveTo(p.x, p.y)
                    path.lineTo(pp.x, pp.y)
                }
                canvas.drawPath(path, paint)
            }
        }
    }

    fun clear() {
        _knots.clear()
        postInvalidate() // Indicate view should be redrawn
    }
}
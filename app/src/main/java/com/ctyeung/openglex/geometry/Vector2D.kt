package com.ctyeung.openglex.geometry

import android.graphics.PointF

/*
 * TODO work in progress
 *  Port from LinearRegression project
 */
class Vector2D {

    val p0:PointF
    val p1:PointF

    constructor( p0: PointF,  p1: PointF)  {
        this.p0 = p0
        this.p1 = p1
    }
    /*
     * construct from 1 point and slope
     */
    constructor(p0: PointF, slope: Float) {
        this.p0 = p0

        // horionzontal line
        if (0F == slope) {
            this.p1 = PointF(p0.x + 5, p0.y)
        } else {
            // default as yIntercept
            val y = p0.y - slope * p0.x
            this.p1 = PointF(0F, y)
        }
    }

    /*
     * Find slope of this line
     * return: Double.NAN if vertical
     */
    open val slope: Float
        get() = if (isVertical) {
            java.lang.Float.NaN
        } else (p1.y - p0.y) / (p1.x - p0.x)

    /*
     * Find Y-Intercept or b
     * - x point where y=0
     *
     * return: x if line is not vertical or horizontal
     */
    open val yIntercept: Float
        get() {
            if (isHorizontal) {
                return java.lang.Float.NaN
            } else {
                val m = slope
                return if (m == java.lang.Float.NaN) java.lang.Float.NaN else p1.y - p1.x * m

            }
        }

    /*
     * Is this a vertical line ?
     */
    open val isVertical: Boolean
        get() = if (p0.x == p1.x) true else false

    /*
     * Is this a horizontal line ?
     */
    open val isHorizontal: Boolean
        get() = if (p0.y == p1.y) true else false


    /*
     * returns:
     * 1. null - no intersection
     * 2. pointF - intersection point
     */
    fun intersectWith(pt: PointF): PointF? {
        return null
    }

    /*
     * returns:
     * 1. null - no intersection
     * 2. pointF - intersection point
     * 3. lineF - overlapping line
     */
    fun intersectWith(line: Vector2D): PointF? {
        // parallel lines
        if (this.isVertical && line.isVertical || this.isHorizontal && line.isHorizontal)
            return null

        if (isVertical) {
            val x = this.p0.x
            val y = line.findY(x)
            return PointF(x, y)
        } else if (isHorizontal) {
            val y = this.p0.y
            val x = line.findX(y)
            return PointF(x, y)
        } else {
            val m = this.slope
            val b = this.yIntercept
            val x = (line.yIntercept - b) / (m - line.slope)
            val y = findY(x)
            return PointF(x, y)

            /*
             * alternative method
             * https://math.stackexchange.com/questions/27388/intersection-of-2-lines-in-2d-via-general-form-linear-equations
             */
        }
    }

    open fun findY(x: Float): Float {
        return if (isVertical) {
            Float.NaN
        } else if (isHorizontal) {
            p0.y
        } else {
            // y = mx + b
            slope * x + yIntercept
        }
    }

    open fun findX(y: Float): Float {
        return if (isVertical) {
            Float.NaN
        } else if (isHorizontal) {
            p0.x
        } else {
            (y - yIntercept) / slope
        }
    }

    /*
     * returns:
     * 1. false - not parallel (intersect)
     * 2. true - is overlapped or parallel
     */
    fun isParallel(vector: Vector2D): Boolean {
        return false
    }

    /*
     * returns:
     * 1. false - not normal
     * 2. true - is normal
     */
    fun isNormal(vector: Vector2D): Boolean {
        return false
    }

    fun isOverlapped(vector:Vector2D) : Boolean {
        return false
    }

    /*
     * returns:
     * 1. 0 - if point is on line
     * 2. N - project normal line onto line - determine shortest distance
     */
    fun findNormalLineFrom(p: PointF): Vector2D {
        // if vertical line
        return if (isVertical) {
            Vector2D(p, PointF(p0.x, p.y))
        } else if (isHorizontal) {
            Vector2D(p, PointF(p.x, p0.y))
        } else {
            // slope is inverse of this line
            Vector2D(p, -1 / slope)
        }// tangent line
        // if horizontal line
    }

    /*
     * Assume Line p1 is top and p2 is bottom, is pt LEFT or RIGHT of line ?
     */
    fun findOrientationFrom(pt: PointF): Orientation {
        return Orientation.Line.Right
    }
}


package com.ctyeung.openglex.geometry

import android.graphics.PointF

/*
 * TODO work in progress
 */
data class TwoVectors2D(val line1:Vector2D, val line2:Vector2D) {
    /*
     * Assuming Line p1 is top and p2 is bottom.
     *
     * returns:
     * Intersecting lines
     * 1. Left, Right, Above, Below
     *
     * Parallel lines
     * 1. Left, Right, Between
     */
    fun findOrientationFrom(pt:PointF) : Orientation {
        return Orientation.Parallel.Between
    }

    fun areParallel():Boolean {
        return line1.isParallel(line2)
    }

    fun areOverlapped():Boolean {
        return line1.isOverlapped(line2)
    }

    fun areNormal():Boolean {
        return line1.isNormal(line2)
    }

    fun getIntersect():PointF? {
        return line1.intersectWith(line2)
    }

    /*
     * return:
     * Angle from 2 vectors
     */
    fun findDotProduct() : Double {
        return 0.0
    }

// 3D only
//    fun crossProduct() :Vector {
//        return Vector(PointF(0F,0F), PointF(1F, 1F))
//    }
}




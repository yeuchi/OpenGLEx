package com.ctyeung.openglex.geometry

import android.graphics.PointF

/*
 * TODO work in progress
 */
data class Vector2D(val p1: PointF, val p2: PointF) {

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
        return null
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
    fun findNearestNormal(pt: PointF): PointF {
        return PointF(0F, 0F)
    }

    /*
     * Assume Line p1 is top and p2 is bottom, is pt LEFT or RIGHT of line ?
     */
    fun findOrientationFrom(pt: PointF): Orientation {
        return Orientation.Line.Right
    }
}


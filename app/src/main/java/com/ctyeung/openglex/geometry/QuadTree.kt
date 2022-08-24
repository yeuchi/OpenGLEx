package com.ctyeung.openglex.geometry

import android.graphics.PointF

/*
 * TODO work in progress
 */
data class QuadTree(val p:PointF) {

    fun insert(p:PointF) {

    }

    fun delete(p:PointF):Boolean {
        return false
    }

    fun nearest(p:PointF):PointF? {
        return null
    }
}
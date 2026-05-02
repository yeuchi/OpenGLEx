package com.ctyeung.openglex.geometry

data class PointF3D (val x: Float = 0f,
                     val y: Float = 0f,
                     val z: Float = 0f
) {
    // Optional: add helper methods for distance or translation
    fun distanceTo(other: PointF3D): Double {
        val dx = x - other.x
        val dy = y - other.y
        val dz = z - other.z
        return Math.sqrt((dx * dx + dy * dy + dz * dz).toDouble())
    }
}
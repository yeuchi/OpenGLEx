package com.ctyeung.openglex.geometry

sealed class Orientation() {
    class Line : Orientation() {
        object Left : Orientation()
        object Right : Orientation()
    }
    class Parallel : Orientation() {
        object Left : Orientation()
        object Right : Orientation()
        object Between : Orientation()
    }
    class Intersect : Orientation() {
        object Left : Orientation()
        object Right : Orientation()
        object Above : Orientation()
        object Below : Orientation()
    }
}
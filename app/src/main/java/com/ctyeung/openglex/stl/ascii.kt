package com.ctyeung.openglex.stl

data class Ascii(val solids: ArrayList<Solid>) {
    companion object {
        fun decode(string: String): Ascii? {
            val solids = ArrayList<Solid>()
            val lines = string.split('\n')
            var solidStartFound = false
            val solidContent = ArrayList<String>()
            lines.forEach { line ->
                // start
                if (line.contains(Solid.start) && !line.contains(Solid.end)) {
                    solidStartFound = true
                }
                // end
                else if (line.contains(Solid.end) && solidStartFound) {
                    Solid.decode(solidContent)?.let {
                        solids.add(it)
                    }
                    solidStartFound = false
                    solidContent.clear()
                }
                // content in between solid start/end
                else {
                    solidContent.add(line)
                }
            }

            return if (solids.isNotEmpty()) {
                Ascii(solids)
            } else {
                null
            }
        }
    }
}

data class Solid(var facets: ArrayList<Facet>) {
    companion object {
        val start = "solid"
        val end = "endsolid"

        fun decode(lines: ArrayList<String>): Solid? {
            val facets = ArrayList<Facet>()
            var facetStartFound = false
            var normal: Normal? = null
            val facetContent = ArrayList<String>()
            lines.forEach { line ->
                if (line.contains(Facet.start) && !line.contains(Facet.end)) {
                    facetStartFound = true
                    normal = Normal.decode(line)
                }
                // end
                else if (line.contains(Facet.end) && facetStartFound) {
                    normal?.let {
                        Facet.decode(it, facetContent)?.let { facet ->
                            facets.add(facet)
                        }
                    }
                    facetStartFound = false
                    facetContent.clear()
                } else {
                    facetContent.add(line)
                }
            }
            return if (facets.isNotEmpty()) {
                Solid(facets)
            } else {
                null
            }
        }
    }
}

data class Facet(val normal: Normal, val outerLoops: ArrayList<OuterLoop>) {
    companion object {
        val start = "facet normal"
        val end = "endfacet"
        var loopStartFound = false
        val loopContent = ArrayList<String>()
        var loops = ArrayList<OuterLoop>()
        fun decode(normal: Normal, lines: ArrayList<String>): Facet? {

            lines.forEach { line ->
                // start
                if (line.contains(OuterLoop.start) && !line.contains(OuterLoop.end)) {
                    loopStartFound = true
                }
                // end
                else if (line.contains(OuterLoop.end) && loopStartFound) {
                    OuterLoop.decode(loopContent)?.let {
                        loops.add(it)
                    }
                    loopStartFound = false
                    loopContent.clear()
                } else {
                    loopContent.add(line)
                }
            }
            return if (loops.isNotEmpty()) {
                Facet(normal, loops)
            } else {
                null
            }
        }
    }
}

data class Normal(val x: Double, val y: Double, val z: Double) {
    companion object {
        val start = "normal"
        fun decode(string: String): Normal? {
            try {
                string.split(Regex("\\s+")).let {
                    val size = it.size
                    return if (size > 3) {
                        val x = it[size - 3].toDouble()
                        val y = it[size - 2].toDouble()
                        val z = it[size - 1].toDouble()
                        Normal(x, y, z)
                    } else {
                        null
                    }
                }
            }
            catch (ex:Exception){
                return null
            }
        }
    }
}

data class OuterLoop(val vertexes: ArrayList<Vertex>) {
    companion object {
        val start = "outer loop"
        val end = "endloop"
        fun decode(lines: ArrayList<String>): OuterLoop? {
            val vertexes = ArrayList<Vertex>()
            lines.forEach {
                Vertex.decode(it)?.let { vertex ->
                    vertexes.add(vertex)
                }
            }
            return if (vertexes.size >= 3) {
                OuterLoop(vertexes)
            } else {
                null
            }
        }
    }
}

data class Vertex(val x: Double, val y: Double, val z: Double) {
    companion object {
        val start = "vertex"
        fun decode(string: String): Vertex? {
            try{
                string.split(Regex("\\s+")).let {
                    val size = it.size
                    return if (size > 3) {
                        val x = it[size-3].toDouble()
                        val y = it[size-2].toDouble()
                        val z = it[size-1].toDouble()
                        Vertex(x, y, z)
                    } else {
                        null
                    }
                }
            }
            catch (ex:Exception){
                return null
            }
        }
    }
}

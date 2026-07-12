package com.ctyeung.openglex.off

/*
 * Same Decoder from VulkanEx, previously written
 */
import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import com.ctyeung.openglex.geometry.PointF3D
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import kotlin.collections.arrayListOf
import kotlin.math.sqrt

class OffDecoder {
    protected var pos: Int = 0;
    private var _numVertices: Int = 0
    val numVertices: Int
        get() {
            return _numVertices
        }

    private var _numFaces: Int = 0
    val numFaces: Int
        get() {
            return _numFaces
        }

    private var _numEdges: Int = 0
    val numEdges: Int
        get() {
            return _numEdges
        }

    // find the rect that contains our mesh
    var meshBound = MeshBound()
    var listVertices = arrayListOf<PointF3D>()

    // face normal for each vertex (3 identical value -> Gourad shading)
    var listNormals = arrayListOf<PointF3D>()

    var listFaces = ArrayList<OffFace>()

    val facesNormal: FloatArray?
        get() {
            /*
             * compute cross product of face plane
             */
            return null
        }

    fun loadFrom(ascii: String): Boolean {
        try {
            pos = 0;
            preloadInit()
            val lines = ascii.split('\n')

            if (parseOFFHeader(lines)) {
                if (parseCount(lines)) {
                    if (parseVertices(lines)) {
                        return parseFaces(lines);
                    }
                }
            }
            return false
        } catch (e: Exception) {
            Log.e("OffDecoder", e.toString())
            return false
        }
    }

    private fun preloadInit() {
        _numEdges = 0
        _numVertices = 0
        _numFaces = 0
    }

    private fun isComment(string: String): Boolean {
        return string.contains("#")
    }

    private fun skipComment(lines: List<String>) {
        while (isComment(lines[pos])) {
            pos++
        }
    }

    /*
     * 1st line is OFF (optional)
     */
    private fun parseOFFHeader(lines: List<String>): Boolean {
        skipComment(lines)
        lines[pos].let {
            val result = it.contains("OFF")
            pos++
            return result
        }
    }

    /*
     * 2nd line: the number of vertices, number of faces, and number of edges
     */
    private fun parseCount(lines: List<String>): Boolean {
        skipComment(lines)
        lines[pos].replace("\r", "").let {
            val metrics = it.split(' ')
            pos++
            if (metrics.size == 3) {
                _numVertices =
                    metrics.get(0).toIntOrNull() ?: throw Exception("missing num verticies")
                _numFaces = metrics.get(1).toIntOrNull() ?: throw Exception("missing num faces")
                _numEdges = metrics.get(2).toIntOrNull() ?: throw Exception("missing num edges")
                return true
            }
        }
        return false
    }

    /*
     * One line for each colored vertex:
     * x y z r g b a
     * for vertex 0, 1, ..., vertex_count-1
     */
    private fun parseVertices(lines: List<String>): Boolean {
        skipComment(lines)
        listVertices.apply {
            clear()

            if ((numVertices + pos) > lines.size) {
                // invalid - not enough lines
                return false
            }

            for (i in 0 until numVertices) {
                lines[pos].replace("\r", "").let {
                    val list = it.split(' ')
                    pos++

                    if (list.size >= 3) {
                        val vertex =
                            PointF3D(list[0].toFloat(), list[1].toFloat(), list[2].toFloat())
                        listVertices.add(vertex)
                        meshBound.collect(vertex.x, vertex.y, vertex.z)
                    }
                }
            }
            return true
        }
    }

    /*
     * One triangle for each face:
     * n v1 v2 ... vn r g b a,
     * the number of vertices, and the vertex indices for each face.
     */
    private fun parseFaces(lines: List<String>): Boolean {
        skipComment(lines)
        listFaces.clear()
        /*
         * TODO list size must == 3
         */
        for (i in 0 until numFaces) {
            lines[pos].replace("\r", "").let {
                it.split(" ").let { list ->
                    pos++

                    val face = arrayListOf<Short>()

                    /*
                     * TODO list size must be 4 [count, index1, index2, index3]
                     */
                    for (j in list.indices) {
                        face.add(list[j].toShort())
                    }
                    val normalVector = parseNormals(face)
                    listFaces.add(OffFace(face, normalVector))
                }
            }
        }
        return true
    }

    private fun parseNormals(face: ArrayList<Short>): PointF3D {

        // get vertices
        val a = listVertices[face[1].toInt()]
        val b = listVertices[face[2].toInt()]
        val c = listVertices[face[3].toInt()]

        // calculate edge vector
        val e1x = b.x - a.x
        val e1y = b.y - a.y
        val e1z = b.z - a.z

        val e2x = c.x - a.x
        val e2y = c.y - a.y
        val e2z = c.z - a.z

        // calculate cross product
        val nx = (e1y * e2z) - (e1z * e2y)
        val ny = (e1z * e2x) - (e1x * e2z)
        val nz = (e1x * e2y) - (e1y * e2x)

        // normalize vector
        val len = sqrt(nx * nx + ny * ny + nz * nz)
        val normalX = if (len > 0) {
            nx / len
        } else {
            nx
        }
        val normalY = if (len > 0) {
            ny / len
        } else {
            ny
        }
        val normalZ = if (len > 0) {
            nz / len
        } else {
            nz
        }

        return PointF3D(normalX, normalY, normalZ)
    }
}

/**
 * A Face - triangle - list of 3 indexes
 */
data class OffFace(val list: ArrayList<Short>, val normalVector: PointF3D)
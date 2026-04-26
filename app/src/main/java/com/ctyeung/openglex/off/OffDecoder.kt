package com.ctyeung.openglex.off

/*
 * Same Decoder from VulkanEx, previously written
 */
import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception

class OffDecoder {
    protected var pos:Int = 0;
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

    private var meshBound = MeshBound()
    private var listVertices = arrayListOf<ArrayList<Float>>()


    private var listFaces = ArrayList<OffFace>()
    val faces: IntArray?
        get() {
            /*
             * TODO Tesselation needed -> 3 edge face triangle
             *  or some other format of array -- there are N sides to a face.
             */
            return IntArray(3)
        }

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

            if(parseOFFHeader(lines)) {
                if(parseCount(lines)) {
                    if (parseVertices(lines)) {
                        return parseFaces(lines)
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
        while(isComment(lines[pos])){
            pos ++
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

            val vertices = arrayListOf<Float>()
            for(i in 0 until numVertices){
                lines[pos].replace("\r", "").let {
                    val list = it.split(' ')
                    pos ++
                    for(j in 0 until list.size) {
                        val n = list[j].toFloat()
                        vertices.add(n)
                    }
                    listVertices.add(vertices)
                }
            }
            return true
        }
    }

    /*
     * One line for each polygonal face:
     * n v1 v2 ... vn r g b a,
     * the number of vertices, and the vertex indices for each face.
     */
    private fun parseFaces(lines: List<String>): Boolean {
        skipComment(lines)

        listFaces.clear()
        for(i in 0 until numFaces) {
            lines[pos].replace("\r", "").let {
                var list = it.split(" ")
                pos++

                val face = arrayListOf<Int>()
                for(j in 0 until list.size) {
                    face.add(list[j].toInt())
                }
                listFaces.add(OffFace(face))
            }
        }
        return true
    }
}

data class OffFace(val list: ArrayList<Int>)
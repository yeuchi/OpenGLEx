package com.ctyeung.openglex.offGL

import android.app.Activity
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.Log
import com.ctyeung.openglex.off.OffDecoder
import java.io.IOException

/*
 * Google developer Open GL lessons
 * https://developer.android.com/develop/ui/views/graphics/opengl
 */
class OFFOpenGLActivity : Activity() {

    private lateinit var gLView: GLSurfaceView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoadOFF()
    }

    /*
     * 1. load file
     * 2. decode
     * 3. render
     */
    fun LoadOFF() {
        // load file
        val filePath = "space_shuttle.off"
        val dataAscii = readAsciiFile(filePath)

        OffDecoder().let { off->
            if (off.loadFrom(dataAscii)) {
                if (off.numVertices > 0 &&
                    off.numFaces > 0
                ) {
                    // Create a GLSurfaceView instance and set it
                    // as the ContentView for this Activity.
                    gLView = MyOFFGLSurfaceView(this, off)
                    setContentView(gLView)
                }
            }
        }
    }

    fun readAsciiFile(filePath: String): String {
        return try {
            applicationContext.assets.open(filePath).bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            Log.e("FileError", "Error reading file", e)
            "" // Return an empty string or handle the error as needed
        }
    }
}
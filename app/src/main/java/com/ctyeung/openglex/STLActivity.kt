package com.ctyeung.openglex

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException

class STLActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stlactivity)
    }

    /*
     * 1. load file
     * 2. decode
     * 3. render
     */

    override fun onResume() {
        super.onResume()

        // load file
        val filePath = "monkey_ascii.stl"
        // val filePath = "file:///assets_stl/torus_ascii.stl"
        val ascii = readAsciiFile(filePath)
    }

    private fun readAsciiFile(filePath: String): String {
        return try {
            applicationContext.assets.open(filePath).bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            Log.e("FileError", "Error reading file", e)
            "" // Return an empty string or handle the error as needed
        }
    }
}
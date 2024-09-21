package com.ctyeung.openglex

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.ctyeung.openglex.databinding.ActivityStlactivityBinding
import java.io.IOException

class STLActivity : AppCompatActivity() {
    private lateinit var binding:ActivityStlactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView (this, R.layout.activity_stlactivity)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        when (itemId) {
            android.R.id.home -> {
                startActivity(Intent(this, MainActivity::class.java))
            }

            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}
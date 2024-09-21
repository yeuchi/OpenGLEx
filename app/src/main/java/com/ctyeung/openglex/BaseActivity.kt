package com.ctyeung.openglex

import android.content.Intent
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.io.IOException

open class BaseActivity : AppCompatActivity() {

    fun initActionBar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
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

    fun readAsciiFile(filePath: String): String {
        return try {
            applicationContext.assets.open(filePath).bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            Log.e("FileError", "Error reading file", e)
            "" // Return an empty string or handle the error as needed
        }
    }
}
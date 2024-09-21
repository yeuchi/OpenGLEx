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

class STLActivity : BaseActivity() {
    private lateinit var binding:ActivityStlactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView (this, R.layout.activity_stlactivity)
        initActionBar(binding.toolbar)
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
}
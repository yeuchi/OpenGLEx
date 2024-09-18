package com.ctyeung.openglex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.io.IOException

class OFFActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offactivity)
    }

    /*
     * 1. load file
     * 2. decode
     * 3. render
     */

    override fun onResume() {
        super.onResume()

        // load file
    }
}
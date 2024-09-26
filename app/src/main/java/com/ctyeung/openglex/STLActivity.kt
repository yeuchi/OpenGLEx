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
import com.ctyeung.openglex.stl.Ascii
import java.io.IOException

class STLActivity : BaseActivity() {

    private var dataType:String? = null
    private var listVertex = ArrayList<Float>()
    private var listNormal = ArrayList<Float>()

    private var stl: Ascii? = null
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
        val dataAscii = readAsciiFile(filePath)

        stl = Ascii.decode(dataAscii)
    }

}
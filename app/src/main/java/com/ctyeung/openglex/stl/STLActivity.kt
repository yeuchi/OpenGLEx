package com.ctyeung.openglex.stl

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ctyeung.openglex.BaseActivity
import com.ctyeung.openglex.R
import com.ctyeung.openglex.databinding.ActivityStlactivityBinding

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

        Ascii.decode(dataAscii)?.let {
            Renderer().render(it)
        }
    }
}
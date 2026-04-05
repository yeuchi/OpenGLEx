package com.ctyeung.openglex.off

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.ctyeung.openglex.BaseActivity
import com.ctyeung.openglex.R
import com.ctyeung.openglex.databinding.ActivityOffactivityBinding
import java.lang.Exception

class OFFActivity : BaseActivity() {
    private lateinit var binding: ActivityOffactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView (this, R.layout.activity_offactivity)
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

        OffDecoder().apply {
            if (loadFrom(dataAscii)) {
                if (numVertices > 0 &&
                    numFaces > 0
                ) {
                    render(this)
                }
            }
        }
    }

    private fun render(off: OffDecoder) {
        off.apply {
            try {

            } catch (ex: Exception) {
                Log.e("OffDecoder", ex.toString())
            }
        }
    }
}
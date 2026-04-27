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

/*
 * see ndk samples
 * https://github.com/android/ndk-samples/tree/main
 */

class OFFActivity : BaseActivity() {
    private lateinit var binding: ActivityOffactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView (this, R.layout.activity_offactivity)
        initActionBar(binding.toolbar)


        var responseTimes = arrayOf(100, 200, 150,300);
        var count:Int = 0;
        if(responseTimes.size>0) {
            for (i in 1 until responseTimes.size) {
                var sum: Int = 0;
                println("${i} ${responseTimes[i]}")

                // get average
                for (j in 0..i) {
                    sum += responseTimes[j];
                    //println("sum ${summ}")

                }

                var element = responseTimes[i];
                var mean = sum / i;
                //println("mean ${mean}")
                if (element > mean) {
                    count++;
                }
            }
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
        val filePath = "space_shuttle.off"
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
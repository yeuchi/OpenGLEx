package com.ctyeung.openglex.off

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.ctyeung.openglex.BaseActivity
import com.ctyeung.openglex.R
import com.ctyeung.openglex.databinding.ActivityOffactivityBinding
import com.ctyeung.openglex.geometry.PointF3D
import java.lang.Exception
import kotlin.concurrent.timer

/*
 * see ndk samples
 * https://github.com/android/ndk-samples/tree/main
 */


class OFFActivity : BaseActivity() {
    private lateinit var binding: ActivityOffactivityBinding
    private var rotateX: Float = 0F
    private var rotateY: Float = 0F
    private var rotateZ: Float = 0F

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

        OffDecoder().let { off->
            if (off.loadFrom(dataAscii)) {
                if (off.numVertices > 0 &&
                    off.numFaces > 0
                ) {
                    render(off)

                    val myTimer = timer(
                        name = "periodic-task",
                        initialDelay = 0,
                        period = 100 // 1 second
                    ) {
                        rotateX += 2;
                        if(rotateX>360)
                            rotateX -= 360;
                        rotate()
                    }
                }
            }
        }
    }

    private fun render(off: OffDecoder) {
        off.apply {
            try {

                /*
                 * TODO 1st step - render vertices only
                 */
                val view = findViewById<MyKnotsView>(R.id.myKnotsView)
                view.setData(listFaces, listVertices, meshBound)

                /*
                 * TODO 2nd step - render triangles + shading
                 */


            } catch (ex: Exception) {
                Log.e("OffDecoder", ex.toString())
            }
        }
    }

    private fun rotate() {
        val view = findViewById<MyKnotsView>(R.id.myKnotsView)
        view.setRotation(rotateX, rotateY, rotateZ)

    }
}
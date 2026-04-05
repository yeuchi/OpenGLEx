package com.ctyeung.openglex

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ctyeung.openglex.baseGL.BaseGLActivity
import com.ctyeung.openglex.basicGL.BasicGLActivity
import com.ctyeung.openglex.databinding.ActivityMainBinding
import com.ctyeung.openglex.demo.OpenGLES20Activity
import com.ctyeung.openglex.off.OFFActivity
import com.ctyeung.openglex.stl.STLActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        findViewById<Button>(R.id.btn_basic)?.apply {
            setOnClickListener {
                navigate2Activity(BasicGLActivity::class.java)
            }
        }

        findViewById<Button>(R.id.btn_base)?.apply {
            setOnClickListener {
                navigate2Activity(BaseGLActivity::class.java)
            }
        }

        findViewById<Button>(R.id.btn_demo)?.apply {
            setOnClickListener {
                navigate2Activity(OpenGLES20Activity::class.java)
            }
        }

        findViewById<Button>(R.id.btn_off)?.apply {
            setOnClickListener {
                navigate2Activity(OFFActivity::class.java)
            }
        }

        findViewById<Button>(R.id.btn_stl)?.apply {
            setOnClickListener {
                navigate2Activity(STLActivity::class.java)
            }
        }
    }

    fun navigate2Activity(classType: Class<*>) {
        val intent = Intent(this, classType)
        startActivity(intent)
    }
}
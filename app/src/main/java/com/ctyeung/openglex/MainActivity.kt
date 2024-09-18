package com.ctyeung.openglex

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ctyeung.openglex.base.BaseActivity
import com.ctyeung.openglex.basic.BasicActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_basic)?.apply {
            setOnClickListener {
                navigate2Activity(BasicActivity::class.java)
            }
        }

        findViewById<Button>(R.id.btn_base)?.apply {
            setOnClickListener {
                navigate2Activity(BaseActivity::class.java)
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
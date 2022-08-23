package com.ctyeung.openglex

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ctyeung.openglex.base.BaseActivity
import com.ctyeung.openglex.basic.BasicActivity
import com.ctyeung.openglex.off.OFFActivity

/*
 * OpenGL exercise - Bring up to date from demos in text.
 * Android Wireless Application Development by Lauren Darcey
 */
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
    }

    fun navigate2Activity(classType:Class<*>) {
        val intent = Intent(this, classType)
        startActivity(intent)
    }
}
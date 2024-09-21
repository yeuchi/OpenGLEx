package com.ctyeung.openglex.baseGL

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.SurfaceView
import androidx.databinding.DataBindingUtil
import com.ctyeung.openglex.BaseActivity
import com.ctyeung.openglex.MainActivity
import com.ctyeung.openglex.R
import com.ctyeung.openglex.databinding.ActivityBaseBinding

class BaseGLActivity : AppCompatActivity() {
    private var mAndroidSurface: SurfaceView? = null
//    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        mAndroidSurface = GLSurfaceView(this)
        setContentView(mAndroidSurface)
//        binding = DataBindingUtil.setContentView (this, R.layout.activity_base)
//        initActionBar(binding.toolbar)
    }
}
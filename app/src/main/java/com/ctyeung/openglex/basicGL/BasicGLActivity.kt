package com.ctyeung.openglex.basicGL

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.SurfaceView
import androidx.databinding.DataBindingUtil
import com.ctyeung.openglex.BaseActivity
import com.ctyeung.openglex.MainActivity
import com.ctyeung.openglex.R
import com.ctyeung.openglex.databinding.ActivityBasicBinding

class BasicGLActivity : BaseActivity() {
//    private var mAndroidSurface: SurfaceView? = null
    private lateinit var binding: ActivityBasicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_basic)
//        mAndroidSurface = BasicGLSurfaceView(this)
//        setContentView(mAndroidSurface)
        binding = DataBindingUtil.setContentView (this, R.layout.activity_basic)
        initActionBar(binding.toolbar)
    }
}
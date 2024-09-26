package com.ctyeung.openglex.baseGL

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ctyeung.openglex.BaseActivity
import com.ctyeung.openglex.R
import com.ctyeung.openglex.databinding.ActivityBaseBinding

class BaseGLActivity : BaseActivity() {
    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView (this, R.layout.activity_base)
        initActionBar(binding.toolbar)
    }
}
package com.ctyeung.openglex.basicGL

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ctyeung.openglex.BaseActivity
import com.ctyeung.openglex.R
import com.ctyeung.openglex.databinding.ActivityBasicBinding

class BasicGLActivity : BaseActivity() {
    private lateinit var binding: ActivityBasicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView (this, R.layout.activity_basic)
        initActionBar(binding.toolbar)
    }
}
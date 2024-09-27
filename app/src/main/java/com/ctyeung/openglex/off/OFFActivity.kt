package com.ctyeung.openglex.off

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ctyeung.openglex.BaseActivity
import com.ctyeung.openglex.R
import com.ctyeung.openglex.databinding.ActivityOffactivityBinding

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
    }
}
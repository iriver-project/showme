package com.sktelecom.showme.Main

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.sktelecom.showme.Main.Home.HomeBodyVM
import com.sktelecom.showme.R
import com.sktelecom.showme.base.view.PActivity
import com.sktelecom.showme.databinding.ActivityMainBinding
import org.ratpoisonfactory.base.util.CommonUtil
import org.ratpoisonfactory.base.util.Log

class MainActivity : PActivity() {

    override fun onAfaterCreate(savedInstanceState: Bundle?) {
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        Log.i(CommonUtil.with.now())
        binding.executePendingBindings()
        val bodyvm = ViewModelProviders.of(this).get(HomeBodyVM::class.java)

        pFragAdd(binding.frameBody.id, bodyvm.asFragCreate())
    }


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

}

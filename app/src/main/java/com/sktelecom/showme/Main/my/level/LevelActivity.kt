package com.sktelecom.showme.Main.my.level

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.sktelecom.showme.Main.my.level.LevelBodyVM
import com.sktelecom.showme.R
import com.sktelecom.showme.base.view.PActivity
import com.sktelecom.showme.databinding.ActivityLevelBinding

class LevelActivity : PActivity() {

    internal lateinit var binding: ActivityLevelBinding
    internal lateinit var mLevelBodyVM: LevelBodyVM

    override fun onAfaterCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_level)
        binding.executePendingBindings()

        mLevelBodyVM = ViewModelProviders.of(this).get(LevelBodyVM::class.java)
        mLevelBodyVM.asFragCreate()

        pFragAdd(binding.frameBody.id, mLevelBodyVM.asFragResume())
    }
}

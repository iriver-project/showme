package com.sktelecom.showme.Main.my.follower

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.sktelecom.showme.R
import com.sktelecom.showme.base.view.PActivity
import com.sktelecom.showme.databinding.ActivityFollowBinding

class FollowActivity : PActivity() {

    internal lateinit var binding: ActivityFollowBinding
    internal lateinit var mbodyVm: FollowBodyVM

    override fun onAfaterCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_follow)
        binding.executePendingBindings()

        mbodyVm = ViewModelProviders.of(this).get(FollowBodyVM::class.java)
        mbodyVm.asFragCreate()

        pFragAdd(binding.frameBody.id, mbodyVm.asFragResume())
    }
}

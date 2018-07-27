package com.sktelecom.showme.Main.my.wallet.reward

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.sktelecom.showme.R
import com.sktelecom.showme.base.view.PActivity
import com.sktelecom.showme.databinding.ActivityRewardBinding

class RewardActivity : PActivity() {

    internal lateinit var binding: ActivityRewardBinding
    internal lateinit var mbodyVm: RewardBodyVM

    override fun onAfaterCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reward)
        binding.executePendingBindings()

        mbodyVm = ViewModelProviders.of(this).get(RewardBodyVM::class.java)
        mbodyVm.asFragCreate()

        pFragAdd(binding.frameBody.id, mbodyVm.asFragResume())
    }
}

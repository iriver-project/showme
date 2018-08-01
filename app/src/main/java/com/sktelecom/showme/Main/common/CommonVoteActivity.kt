package com.sktelecom.showme.Main.common

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.sktelecom.showme.R
import com.sktelecom.showme.base.view.PActivity
import com.sktelecom.showme.databinding.ActivityVoteBinding


class CommonVoteActivity : PActivity() {
    internal lateinit var binding: ActivityVoteBinding
    internal lateinit var vm: CommonVoteBodyVM


    override fun onAfaterCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vote)
        binding.executePendingBindings()

        vm = ViewModelProviders.of(this).get(CommonVoteBodyVM::class.java)
        vm.asFragCreate()

        pFragAdd(binding.frameBody.id, vm.asFragResume())
    }

}
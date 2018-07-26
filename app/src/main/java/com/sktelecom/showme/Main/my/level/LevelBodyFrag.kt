package com.sktelecom.showme.Main.my.level

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sktelecom.showme.R
import com.sktelecom.showme.base.view.PFragment
import com.sktelecom.showme.base.view.animation.CircleAngleAnimation
import com.sktelecom.showme.databinding.LevelBodyFragBinding


class LevelBodyFrag : PFragment() {

    internal lateinit var mICallbackEvent: ICallbackEvent
    //    internal lateinit var mLinearLayoutManager: LinearLayoutManager
    internal lateinit var mGridLayoutManager: GridLayoutManager

    internal lateinit var vm: LevelBodyVM
    internal lateinit var binding: LevelBodyFragBinding


    fun setCallback(vm: LevelBodyVM, mICallbackEvent: ICallbackEvent) {
        this.mICallbackEvent = mICallbackEvent
        this.vm = vm;
    }

    override fun abCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.level_body_frag, container, false)
        binding.viewmodel = vm
        binding.viewmodel!!.getList().observe(this, Observer {

        })

        Glide.with(pCon).load("https://pbs.twimg.com/profile_images/547798344567779328/GRhJMA8t_400x400.png").apply(RequestOptions().circleCrop()).into(binding.ivMyImg)
//        binding.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//            }
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//            }
//        })
//        binded.pager.setPageTransformer(true, CubeTransformer())
        val animation = CircleAngleAnimation(binding.circle, 230)
        animation.duration = 2000
        binding.circle.startAnimation(animation)
        return binding.getRoot()
    }

    override fun onCreated() {
    }


    interface ICallbackEvent {
        fun getPage(page: Int)
    }


    companion object {

        fun with(vm: LevelBodyVM, mICallbackEvent: ICallbackEvent): LevelBodyFrag {
            val frag = LevelBodyFrag()
            frag.setCallback(vm, mICallbackEvent)
            return frag
        }

        fun withView(): Companion {
            return this
        }
    }
}
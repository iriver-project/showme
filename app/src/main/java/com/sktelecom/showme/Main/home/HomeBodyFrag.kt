package com.sktelecom.showme.Main.home

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sktelecom.showme.BR
import com.sktelecom.showme.R
import com.sktelecom.showme.base.Model.PBean
import com.sktelecom.showme.base.Model.VoContents
import com.sktelecom.showme.base.view.PFragment
import com.sktelecom.showme.databinding.ArtistItemBinding
import com.sktelecom.showme.databinding.HomeBodyFragBinding
import java.util.*

class HomeBodyFrag : PFragment() {

    internal lateinit var mICallbackEvent: ICallbackEvent
    internal lateinit var mGridAdapter: ArtistListAdapter
    internal var list: ArrayList<PBean> = ArrayList()

    internal lateinit var vm: HomeBodyVM
    internal lateinit var title: String
    internal lateinit var binding: HomeBodyFragBinding


    fun setCallback(title: String, vm: HomeBodyVM, mICallbackEvent: ICallbackEvent) {
        this.mICallbackEvent = mICallbackEvent
        this.title = title
        this.vm = vm
    }

    override fun abCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_body_frag, container, false)
        binding.viewmodel = vm
        mGridAdapter = ArtistListAdapter()

        binding.artistRv.layoutManager = GridLayoutManager(pCon, 3)
        binding.artistRv.setHasFixedSize(false)

        if (list.size <= 0) {
            binding.viewmodel!!.getList().observe(this, Observer {
                list = it as ArrayList<PBean>
                binding.artistRv.adapter = mGridAdapter
            })
        } else {
            binding.artistRv.adapter = mGridAdapter
        }
        binding.homeTitle.text = title

        var name = arrayOf("stage1", "stage2", "stage3")
        binding.stageSpn.adapter = ArrayAdapter<String>(pCon, android.R.layout.simple_spinner_item, name)
        binding.stageSpn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }

        }
        
        return binding.root
    }

    override fun onCreated() {

    }

    internal inner class ArtistListAdapter internal constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): RecyclerView.ViewHolder {
            val binding = ArtistItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            return ArtistViewHolder(binding)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = holder as ArtistViewHolder
            val model = list[position] as VoContents
            viewHolder.bind(model)
            Glide.with(pCon).load("https://post-phinf.pstatic.net/MjAxNzAxMTFfOTkg/MDAxNDg0MTMzMTMzNzcy.Gf1kb2nOHDXSEEEGsTCKHJwoWef1XQxTFOH09MBL6d0g.9-YbfPG9neKPHz1mX_Sj-Y-NgJxy0aPOfMCwFd7_ahEg.JPEG/mug_obj_201701112012145347.jpg?type=w1080").apply(RequestOptions().circleCrop()).into(viewHolder.ibinding.artistImg)
        }

        internal inner class ArtistViewHolder internal constructor(val ibinding: ArtistItemBinding) : RecyclerView.ViewHolder(ibinding.root) {

            internal fun bind(model: VoContents) {
                ibinding.setVariable(BR.item, model)
                ibinding.setVariable(BR.viewmodel, binding.viewmodel)
            }
        }
    }

    interface ICallbackEvent {
        fun getPage(page: Int)
    }

    companion object {

        fun with(title: String, vm: HomeBodyVM, mICallbackEvent: ICallbackEvent): HomeBodyFrag {
            val frag = HomeBodyFrag()
            frag.setCallback(title, vm, mICallbackEvent)
            return frag
        }

        fun withView(): Companion {
            return this
        }
    }
}
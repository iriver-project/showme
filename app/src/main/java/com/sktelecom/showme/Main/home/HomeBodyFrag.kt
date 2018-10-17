package com.sktelecom.showme.Main.home

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sktelecom.showme.BR
import com.sktelecom.showme.R
import com.sktelecom.showme.base.Model.PBean
import com.sktelecom.showme.base.Model.VoArtist
import com.sktelecom.showme.base.view.PFragment
import com.sktelecom.showme.databinding.HomeArtistItemBinding
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

        binding.rvArtist.layoutManager = GridLayoutManager(pCon, 3)
        binding.rvArtist.setHasFixedSize(false)

        if (list.size <= 0) {
            binding.viewmodel!!.getList().observe(this, Observer {
                list = it as ArrayList<PBean>
                binding.rvArtist.adapter = mGridAdapter
            })
        } else {
            binding.rvArtist.adapter = mGridAdapter
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
            val binding = HomeArtistItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            return ArtistViewHolder(binding)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = holder as ArtistViewHolder
            val model = list[position] as VoArtist
            viewHolder.bind(model)
            Glide.with(pCon).load(model.atstThumbnail).apply(RequestOptions().circleCrop()).into(viewHolder.ibinding.ivArtistCircle)
            Glide.with(pCon).load("https://beataejzenheart.files.wordpress.com/2017/09/zrzut-ekranu-2017-09-27-o-15-45-39.png").into(viewHolder.ibinding.ivArtistBack)
        }

        internal inner class ArtistViewHolder internal constructor(val ibinding: HomeArtistItemBinding) : RecyclerView.ViewHolder(ibinding.root) {

            internal fun bind(model: VoArtist) {
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
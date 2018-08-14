package com.sktelecom.showme.Main.common

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sktelecom.showme.BR
import com.sktelecom.showme.R
import com.sktelecom.showme.base.Model.PBean
import com.sktelecom.showme.base.Model.VoArtist
import com.sktelecom.showme.base.view.PFragment
import com.sktelecom.showme.databinding.VoteArtistItemBinding
import com.sktelecom.showme.databinding.VoteBodyFragBinding


class CommonVoteBodyFrag : PFragment() {

    internal lateinit var mICallbackEvent: CommonVoteBodyFrag.ICallbackEvent

    internal lateinit var vm: CommonVoteBodyVM
    internal lateinit var binding: VoteBodyFragBinding

    internal lateinit var mGridAdapter: ArtistListAdapter
    internal var list: ArrayList<PBean> = ArrayList()

    internal lateinit var mListAdapter: SelectListAdapter
    internal var selectList: ArrayList<VoArtist> = ArrayList()


    override fun onCreated() {

    }

    override fun abCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.vote_body_frag, container, false)
        binding.viewmodel = vm

        mGridAdapter = ArtistListAdapter()

        binding.artistRv.layoutManager = GridLayoutManager(pCon, 3)
        binding.artistRv.setHasFixedSize(false)

        mListAdapter = SelectListAdapter()

        binding.selectRv.layoutManager = LinearLayoutManager(pCon).apply { orientation = LinearLayoutManager.HORIZONTAL }
        binding.selectRv.setHasFixedSize(true)

        if (list.size <= 0) {
            binding.viewmodel!!.getList().observe(this, Observer {
                list = it as ArrayList<PBean>
                binding.artistRv.adapter = mGridAdapter
            })
        } else {
            binding.artistRv.adapter = mGridAdapter
        }

        return binding.root
    }


    internal inner class SelectListAdapter internal constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): RecyclerView.ViewHolder {
            val binding = VoteArtistItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            return SelectViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return selectList.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = holder as SelectViewHolder
            val model = selectList[position]
            viewHolder.bind(model)
            Glide.with(pCon).load(model.atstThumbnail).apply(RequestOptions().circleCrop()).into(viewHolder.ibinding.artistImg)
            viewHolder.ibinding.cancelImg.visibility = View.VISIBLE
            viewHolder.ibinding.cancelImg.setOnClickListener({
                selectList.remove(model)
                binding.selectRv.adapter = mListAdapter
            })
        }

        internal inner class SelectViewHolder internal constructor(val ibinding: VoteArtistItemBinding) : RecyclerView.ViewHolder(ibinding.root) {

            internal fun bind(model: VoArtist) {
                ibinding.setVariable(BR.item, model)
                ibinding.setVariable(BR.viewmodel, binding.viewmodel)
            }
        }
    }

    internal inner class ArtistListAdapter internal constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun getItemCount(): Int {
            return list.size
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): RecyclerView.ViewHolder {
            val binding = VoteArtistItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            return ArtistViewHolder(binding)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = holder as ArtistViewHolder
            val model = list[position] as VoArtist
            viewHolder.bind(model)
            Glide.with(pCon).load(model.atstThumbnail).apply(RequestOptions().circleCrop()).into(viewHolder.ibinding.artistImg)

            viewHolder.ibinding.artistImg.setOnClickListener({
                if (model !in selectList) {
                    selectList.add(0, model)
                    binding.selectRv.adapter = mListAdapter
                } else
                    Toast.makeText(pCon, "이미 선택한 아티스트", Toast.LENGTH_SHORT).show()
            })
        }

        internal inner class ArtistViewHolder internal constructor(val ibinding: VoteArtistItemBinding) : RecyclerView.ViewHolder(ibinding.root) {

            internal fun bind(model: VoArtist) {
                ibinding.setVariable(BR.item, model)
                ibinding.setVariable(BR.viewmodel, binding.viewmodel)
            }
        }
    }

    fun setCallback(vm: CommonVoteBodyVM, mICallbackEvent: CommonVoteBodyFrag.ICallbackEvent) {
        this.mICallbackEvent = mICallbackEvent
        this.vm = vm
    }

    interface ICallbackEvent {
        fun getPage(page: Int)
    }

    companion object {
        fun with(vm: CommonVoteBodyVM, mICallbackEvent: CommonVoteBodyFrag.ICallbackEvent): CommonVoteBodyFrag {
            val frag = CommonVoteBodyFrag()
            frag.setCallback(vm, mICallbackEvent)
            return frag
        }

        fun withView(): CommonVoteBodyFrag.Companion {
            return this
        }
    }

}
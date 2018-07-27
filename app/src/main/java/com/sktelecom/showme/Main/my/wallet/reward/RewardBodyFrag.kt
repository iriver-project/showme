package com.sktelecom.showme.Main.my.wallet.reward

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sktelecom.showme.BR
import com.sktelecom.showme.R
import com.sktelecom.showme.base.Model.EmptyVo
import com.sktelecom.showme.base.Model.PBean
import com.sktelecom.showme.base.Model.VoContents
import com.sktelecom.showme.base.view.PFragment
import com.sktelecom.showme.databinding.*
import java.util.ArrayList


class RewardBodyFrag : PFragment() {

    internal lateinit var mListAdapter: CommonListAdapter
    internal lateinit var mLinearLayoutManager: LinearLayoutManager
    internal var list: ArrayList<PBean> = ArrayList()
    internal var page = 0
    internal lateinit var mICallbackEvent: ICallbackEvent

    internal lateinit var vm: RewardBodyVM
    internal lateinit var binding: RewardBodyFragBinding


    fun setCallback(vm: RewardBodyVM, mICallbackEvent: ICallbackEvent) {
        this.mICallbackEvent = mICallbackEvent
        this.vm = vm;
    }

    override fun abCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.reward_body_frag, container, false)
        binding.viewmodel = vm
        binding.viewmodel!!.getList().observe(this, Observer {
            list = it as ArrayList<PBean>
            binding.rv.setAdapter(mListAdapter)
        })

        mLinearLayoutManager = LinearLayoutManager(pCon)
        mLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mLinearLayoutManager.scrollToPosition(0)

        binding.rv.setLayoutManager(mLinearLayoutManager)
        binding.rv.setHasFixedSize(true)
        binding.rv.setItemAnimator(DefaultItemAnimator())

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

        mListAdapter = CommonListAdapter()
        mListAdapter.addDataToBottom(EmptyVo())
        return binding.getRoot()
    }


    override fun onCreated() {
    }


    interface ICallbackEvent {
        fun getPage(page: Int)
    }


    internal inner class CommonListAdapter internal constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        internal val selectedItems: SparseBooleanArray

        init {
            selectedItems = SparseBooleanArray()
        }

        internal fun addDataToBottom(newModelData: PBean) {
            list.add(newModelData)
            notifyItemInserted(list.size - 1)
        }


        internal fun deleteitem(position: Int) {
            list.removeAt(position)
            notifyItemRemoved(position)
        }


        internal fun updateItem(position: Int) {
            notifyItemChanged(position)
        }

        override fun getItemViewType(position: Int): Int {
            return list[position].viewType
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            if (viewType == PBean.TYPE_CONTENTS) {
                val binding = RewardItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
                return ItemViewHolder(binding)
            } else if (viewType == PBean.TYPE_EMPTY) {
                val binding = CommonEmptyItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
                return EmptyViewHolder(binding)
            } else if (viewType == PBean.TYPE_LOADING) {
                val binding = CommonLoadingItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
                return ProgressViewHolder(binding)
            } else {
                val binding = CommonEmptyItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
                return EmptyViewHolder(binding)
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            if (holder is EmptyViewHolder) {
                onEmptyHolder(holder, position)
            } else if (holder is ItemViewHolder) {
                onItemHolder(holder, position)
            } else {
                onLoadingHolder(holder, position)
            }
        }

        internal fun onItemHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = holder as ItemViewHolder
            val model = list[position] as VoContents
            viewHolder.bind(model)


        }


        internal fun onLoadingHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = holder as ProgressViewHolder
            viewHolder.itemView.isActivated = selectedItems.get(position, false)
        }

        internal fun onEmptyHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = holder as EmptyViewHolder
            viewHolder.itemView.isActivated = selectedItems.get(position, false)
        }

        override fun getItemCount(): Int {
            return list.size
        }


        internal inner class ItemViewHolder internal constructor(val ibinding: RewardItemBinding) : RecyclerView.ViewHolder(ibinding.getRoot()) {

            internal fun bind(model: VoContents) {
                ibinding.setVariable(BR.item, model)
                ibinding.setVariable(BR.viewmodel, binding.viewmodel)
            }
        }

        internal inner class ProgressViewHolder internal constructor(internal val ibinding: CommonLoadingItemBinding) : RecyclerView.ViewHolder(ibinding.getRoot())

        internal inner class EmptyViewHolder internal constructor(internal val ibinding: CommonEmptyItemBinding) : RecyclerView.ViewHolder(ibinding.getRoot())
    }


    companion object {

        fun with(vm: RewardBodyVM, mICallbackEvent: ICallbackEvent): RewardBodyFrag {
            val frag = RewardBodyFrag()
            frag.setCallback(vm, mICallbackEvent)
            return frag
        }

        fun withView(): Companion {
            return this
        }
    }
}
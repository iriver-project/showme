package com.sktelecom.showme.Main.Home

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sktelecom.showme.BR
import com.sktelecom.showme.base.Model.EmptyVo
import com.sktelecom.showme.base.Model.PBean
import com.sktelecom.showme.base.Model.VoContents
import com.sktelecom.showme.R
import com.sktelecom.showme.base.view.PFragment
import com.sktelecom.showme.databinding.CommonEmptyItemBinding
import com.sktelecom.showme.databinding.CommonLoadingItemBinding
import com.sktelecom.showme.databinding.ContentItemBinding
import com.sktelecom.showme.databinding.HomeBodyFragBinding
import java.util.*

class HomeBodyFrag : PFragment() {

    internal lateinit var mICallbackEvent: ICallbackEvent
    internal lateinit var mListAdapter: CommonListAdapter
    internal lateinit var mLinearLayoutManager: LinearLayoutManager
    internal var list: ArrayList<PBean> = ArrayList()
    internal var page = 0

    internal lateinit var vm: HomeBodyVM
    internal lateinit var title: String
    internal lateinit var binding: HomeBodyFragBinding


    fun setCallback(title: String, vm: HomeBodyVM, mICallbackEvent: ICallbackEvent) {
        this.mICallbackEvent = mICallbackEvent
        this.title = title
        this.vm = vm;
    }

    override fun abCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_body_frag, container, false)
//        binding.viewmodel = HomeBodyVM()
        binding.viewmodel = vm
        binding.viewmodel!!.getList().observe(this, Observer {
            list = it as ArrayList<PBean>
            binding.rv.setAdapter(mListAdapter)
        });
        mLinearLayoutManager = LinearLayoutManager(pCon)
        mLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mLinearLayoutManager.scrollToPosition(0)

        binding.tvTitle.setText(title)
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

    protected fun setReflash() {
        page = 0
        list = ArrayList<PBean>()
        mListAdapter = CommonListAdapter()
        binding.rv.setAdapter(mListAdapter)
        mListAdapter.addDataToBottom(EmptyVo())
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
                val binding = ContentItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
                return ContentsViewHolder(binding)
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
            } else if (holder is ContentsViewHolder) {
                onContentsHolder(holder, position)
            } else {
                onLoadingHolder(holder, position)
            }
        }

        internal fun onContentsHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = holder as ContentsViewHolder
            val model = list[position] as VoContents
            viewHolder.bind(model)


            //            viewHolder.binding.rrPurchased.setOnClickListener(view -> {
            //                if (mICallbackEvent != null)
            //                    mICallbackEvent.onClick(model);
            //            });

            //            viewHolder.binding.row.setOnClickListener(view -> {
            //                if (mICallbackEvent != null)
            //                    mICallbackEvent.onClick(model);
            //            });
            //            viewHolder.itemView.setActivated(selectedItems.get(position, false));
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


        internal inner class ContentsViewHolder internal constructor(val ibinding: ContentItemBinding) : RecyclerView.ViewHolder(ibinding.getRoot()) {

            internal fun bind(model: VoContents) {
                ibinding.setVariable(BR.item, model)
                ibinding.setVariable(BR.viewmodel, binding.viewmodel)
            }
        }

        internal inner class ProgressViewHolder internal constructor(internal val ibinding: CommonLoadingItemBinding) : RecyclerView.ViewHolder(ibinding.getRoot())

        internal inner class EmptyViewHolder internal constructor(internal val ibinding: CommonEmptyItemBinding) : RecyclerView.ViewHolder(ibinding.getRoot())
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
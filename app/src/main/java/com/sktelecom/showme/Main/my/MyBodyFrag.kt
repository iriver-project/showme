package com.sktelecom.showme.Main.my

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
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
import com.sktelecom.showme.base.Model.VoUserInfo
import com.sktelecom.showme.base.Model.VoVideo
import com.sktelecom.showme.base.view.PFragment
import com.sktelecom.showme.databinding.*
import java.util.*


class MyBodyFrag : PFragment() {

    internal lateinit var mICallbackEvent: ICallbackEvent
    internal lateinit var mListAdapter: CommonListAdapter
    //    internal lateinit var mLinearLayoutManager: LinearLayoutManager
    internal lateinit var mGridLayoutManager: GridLayoutManager
    internal var list: ArrayList<PBean> = ArrayList()
    internal var page = 0

    internal lateinit var vm: MyBodyVM
    internal lateinit var binding: MyBodyFragBinding


    fun setCallback(vm: MyBodyVM, mICallbackEvent: ICallbackEvent) {
        this.mICallbackEvent = mICallbackEvent
        this.vm = vm;
    }

    override fun abCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.my_body_frag, container, false)
        binding.viewmodel = vm
        mListAdapter = CommonListAdapter()

        if (list.size <= 0) {
            binding.viewmodel!!.getList().observe(this, Observer {
                list = it as ArrayList<PBean>
                binding.rv.setAdapter(mListAdapter)
            })
        } else {
            binding.rv.setAdapter(mListAdapter)
        }

        mGridLayoutManager = GridLayoutManager(pCon, 2)
        mGridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                when (mListAdapter.getItemViewType(position)) {
                    PBean.TYPE_ITEM_VIDEO -> return 1
//                    PBean.TYPE_USER_INFO -> return 2
                    else -> return 2
                }
            }
        }

        binding.rv.setLayoutManager(mGridLayoutManager)
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
            if (viewType == PBean.TYPE_USER_INFO) {
                val binding = UserInfoItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
                return UserInfoViewHolder(binding)
            } else if (viewType == PBean.TYPE_ITEM_VIDEO) {
                val binding = VideoItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
                return VideoViewHolder(binding)
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
            } else if (holder is UserInfoViewHolder) {
                onUserInfoHolder(holder, position)
            } else if (holder is VideoViewHolder) {
                onVideoHolder(holder, position)
            } else {
                onLoadingHolder(holder, position)
            }
        }

        internal fun onUserInfoHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = holder as UserInfoViewHolder
            val model = list[position] as VoUserInfo
            viewHolder.bind(model)
            Glide.with(pCon).load(model.imgUrl).apply(RequestOptions().circleCrop()).into(viewHolder.ibinding.ivMyImg)
        }

        internal fun onVideoHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = holder as VideoViewHolder
            val model = list[position] as VoVideo
            viewHolder.bind(model)
            Glide.with(pCon).load(model.artistImgUrl).into(viewHolder.ibinding.ivArtist)
            Glide.with(pCon).load(model.imgUrl).into(viewHolder.ibinding.ivImage)
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

        internal inner class UserInfoViewHolder internal constructor(val ibinding: UserInfoItemBinding) : RecyclerView.ViewHolder(ibinding.getRoot()) {

            internal fun bind(model: VoUserInfo) {
                ibinding.setVariable(BR.item, model)
                ibinding.setVariable(BR.viewmodel, binding.viewmodel)
            }
        }

        internal inner class VideoViewHolder internal constructor(val ibinding: VideoItemBinding) : RecyclerView.ViewHolder(ibinding.getRoot()) {

            internal fun bind(model: VoVideo) {
                ibinding.setVariable(BR.item, model)
                ibinding.setVariable(BR.viewmodel, binding.viewmodel)
            }
        }

        internal inner class ProgressViewHolder internal constructor(internal val ibinding: CommonLoadingItemBinding) : RecyclerView.ViewHolder(ibinding.getRoot())

        internal inner class EmptyViewHolder internal constructor(internal val ibinding: CommonEmptyItemBinding) : RecyclerView.ViewHolder(ibinding.getRoot())
    }

    companion object {

        fun with(vm: MyBodyVM, mICallbackEvent: ICallbackEvent): MyBodyFrag {
            val frag = MyBodyFrag()
            frag.setCallback(vm, mICallbackEvent)
            return frag
        }

        fun withView(): Companion {
            return this
        }
    }
}
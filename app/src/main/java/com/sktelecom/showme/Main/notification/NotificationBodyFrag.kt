package com.sktelecom.showme.Main.notification

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
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
import com.sktelecom.showme.base.Model.EmptyVo
import com.sktelecom.showme.base.Model.PBean
import com.sktelecom.showme.base.Model.VoContents
import com.sktelecom.showme.R
import com.sktelecom.showme.base.view.PFragment
import com.sktelecom.showme.databinding.*
import java.util.*
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Gravity
import android.widget.FrameLayout
import com.sktelecom.showme.base.util.CommonUtil
import com.sktelecom.showme.base.util.Log


class NotificationBodyFrag : PFragment() {

    internal lateinit var mICallbackEvent: ICallbackEvent
    internal lateinit var mListAdapter: CommonListAdapter
    internal lateinit var mLinearLayoutManager: LinearLayoutManager
    internal var list: ArrayList<PBean> = ArrayList()
    internal var page = 0

    internal lateinit var vm: NotificationBodyVM
    internal lateinit var title: String
    internal lateinit var binding: NotificationBodyFragBinding


    fun setCallback(title: String, vm: NotificationBodyVM, mICallbackEvent: ICallbackEvent) {
        this.mICallbackEvent = mICallbackEvent
        this.title = title
        this.vm = vm;
    }

    override fun abCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.notification_body_frag, container, false)
//        binding.viewmodel = NotificationBodyVM()
        binding.viewmodel = vm
        if (list.size <= 0) {
            binding.viewmodel!!.getList().observe(this, Observer {
                list = it as ArrayList<PBean>
                mListAdapter.addDataToBottom(EmptyVo())
                binding.rv.setAdapter(mListAdapter)
            })
        } else {
            binding.rv.setAdapter(mListAdapter)
        }
        mLinearLayoutManager = LinearLayoutManager(pCon)
        mLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mLinearLayoutManager.scrollToPosition(0)

        binding.tvTitle.setText(title)
        binding.rv.setLayoutManager(mLinearLayoutManager)
        binding.rv.setHasFixedSize(true)
        binding.rv.setItemAnimator(DefaultItemAnimator())

        binding.rv.addItemDecoration(DividerItemDecoration(pCon, DividerItemDecoration.VERTICAL))


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
        setUpItemTouchHelper()
        mListAdapter = CommonListAdapter()
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

        internal fun restoreItem(item: PBean, position: Int) {
            list.add(position, item);
            notifyItemInserted(position);
        }

        override fun getItemViewType(position: Int): Int {
            return list[position].viewType
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            if (viewType == PBean.TYPE_CONTENTS) {
                val binding = NotificationItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
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
            Glide.with(pCon).load(model.CONTENTS_URL).apply(RequestOptions().circleCrop()).into(viewHolder.ibinding.iv)

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


        internal inner class ContentsViewHolder internal constructor(val ibinding: NotificationItemBinding) : RecyclerView.ViewHolder(ibinding.getRoot()) {

            internal fun bind(model: VoContents) {
                ibinding.setVariable(BR.item, model)
                ibinding.setVariable(BR.viewmodel, binding.viewmodel)
            }
        }

        internal inner class ProgressViewHolder internal constructor(internal val ibinding: CommonLoadingItemBinding) : RecyclerView.ViewHolder(ibinding.getRoot())

        internal inner class EmptyViewHolder internal constructor(internal val ibinding: CommonEmptyItemBinding) : RecyclerView.ViewHolder(ibinding.getRoot())


//        fun isUndoOn(): Boolean {
//            return undoOn
//        }
//
//        fun pendingRemoval(position: Int) {
//            val item = items.get(position)
//            if (!itemsPendingR1234
//  emoval.contains(item)) {
//                itemsPendingRemoval.add(item)
//                // this will redraw row in "undo" state
//                notifyItemChanged(position)
//                // let's create, store and post a runnable to remove the item
//                val pendingRemovalRunnable = Runnable { remove(items.indexOf(item)) }
//                handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT.toLong())
//                pendingRunnables.put(item, pendingRemovalRunnable)
//            }
//        }
//
//        fun remove(position: Int) {
//            val item = items.get(position)
//            if (itemsPendingRemoval.contains(item)) {
//                itemsPendingRemoval.remove(item)
//            }
//            if (items.contains(item)) {
//                items.removeAt(position)
//                notifyItemRemoved(position)
//            }
//        }
//
//        fun isPendingRemoval(position: Int): Boolean {
//            val item = items.get(position)
//            return itemsPendingRemoval.contains(item)
//        }
    }


    private fun setUpItemTouchHelper() {

        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            lateinit var background: Drawable
            internal var xMark: Drawable? = null
            internal var xMarkMargin: Int = 0
            internal var initiated: Boolean = false

            private fun init() {
                background = ColorDrawable(Color.LTGRAY)
                xMark = ContextCompat.getDrawable(pCon, R.drawable.abc_ab_share_pack_mtrl_alpha)
                xMark!!.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                xMarkMargin = CommonUtil.with.dpTopx(pCon, 500)
                initiated = true
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                val position = viewHolder.adapterPosition
                val testAdapter = recyclerView.adapter as CommonListAdapter?
                return super.getSwipeDirs(recyclerView, viewHolder)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val swipedPosition = viewHolder.adapterPosition
                val adapter = binding.rv.getAdapter() as CommonListAdapter

                adapter.deleteitem(swipedPosition)


                var snackbar = Snackbar.make(binding.rrMain, " 정말 지울까?", Snackbar.LENGTH_LONG)
                snackbar.setAction("취소") {
                    //                    adapter!!.restoreItem(deletedItem, deletedIndex);
                }
                var view = snackbar.getView()
                var params = view.getLayoutParams() as CoordinatorLayout.LayoutParams
                params.bottomMargin = CommonUtil.with.dpTopx(pCon, 50)
                view.setLayoutParams(params);
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                val itemView = viewHolder.itemView

                if (viewHolder.adapterPosition == -1) {
                    return
                }

                if (!initiated) {
                    init()
                }

                // draw gray background
//                background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
//                background.draw(c)

                Log.i("dx", dX)

                // draw x mark
                val itemHeight = itemView.bottom - itemView.top
                val intrinsicWidth = xMark!!.intrinsicWidth
                val intrinsicHeight = xMark!!.intrinsicWidth

                val xMarkLeft = itemView.right - xMarkMargin - intrinsicWidth
                val xMarkRight = itemView.right - xMarkMargin
                val xMarkTop = itemView.top + (itemHeight - intrinsicHeight) / 2
                val xMarkBottom = xMarkTop + intrinsicHeight
                xMark!!.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom)

                xMark!!.draw(c)

//                if (dX != 0.0f && dX > -500f)
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

        }
        val mItemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        mItemTouchHelper.attachToRecyclerView(binding.rv)
    }


    companion object {

        fun with(title: String, vm: NotificationBodyVM, mICallbackEvent: ICallbackEvent): NotificationBodyFrag {
            val frag = NotificationBodyFrag()
            frag.setCallback(title, vm, mICallbackEvent)
            return frag
        }

        fun withView(): Companion {
            return this
        }
    }
}
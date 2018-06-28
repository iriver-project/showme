package com.sktelecom.showme.Wallet.CommonView;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sktelecom.showme.BR;
import com.sktelecom.showme.base.Model.EmptyVo;
import com.sktelecom.showme.base.Model.PBean;
import com.sktelecom.showme.base.Model.VoContents;
import com.sktelecom.showme.R;
import com.sktelecom.showme.base.view.PFragment;
import com.sktelecom.showme.databinding.CommonEmptyItemBinding;
import com.sktelecom.showme.databinding.CommonLoadingItemBinding;
import com.sktelecom.showme.databinding.WalletCommonFragBinding;
import com.sktelecom.showme.databinding.WalletItemBinding;

import java.util.ArrayList;
import java.util.List;

public class WalletCommonFrag extends PFragment {
    private ICallbackEvent mICallbackEvent;
    private CommonListAdapter mListAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List<PBean> list = new ArrayList<>();

    private String title;
    private WalletCommonFragBinding binded;
    private WalletCommonVM vm;

    protected static WalletCommonFrag with(String title, WalletCommonVM vm, ICallbackEvent mICallbackEvent) {
        WalletCommonFrag frag = new WalletCommonFrag();
        frag.setCallback(title, vm, mICallbackEvent);
        return frag;
    }


    protected WalletCommonFrag withView() {
        return this;
    }

    public void setCallback(String title, WalletCommonVM vm, ICallbackEvent mICallbackEvent) {
        this.mICallbackEvent = mICallbackEvent;
        this.vm = vm;
        this.title = title;
    }

    @Override
    public View abCreateView(LayoutInflater inflater, ViewGroup container) {
        binded = DataBindingUtil.inflate(inflater, R.layout.wallet_common_frag, container, false);
        binded.setViewmodel(vm);

        binded.getViewmodel().getList().observe(this, pBeans -> {
            list = pBeans;
            binded.rv.setAdapter(mListAdapter);
        });

        mLinearLayoutManager = new LinearLayoutManager(pCon);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLinearLayoutManager.scrollToPosition(0);

        binded.tvTitle.setText(title);
        binded.rv.setLayoutManager(mLinearLayoutManager);
        binded.rv.setHasFixedSize(true);
        binded.rv.setItemAnimator(new DefaultItemAnimator());


        mListAdapter = new CommonListAdapter();
        binded.rv.setAdapter(mListAdapter);
        mListAdapter.addDataToBottom(new EmptyVo());
        return binded.getRoot();
    }


    protected void setReflash() {
        list = new ArrayList<>();
        mListAdapter = new CommonListAdapter();
        binded.rv.setAdapter(mListAdapter);
        mListAdapter.addDataToBottom(new EmptyVo());
    }


    @Override
    public void onCreated() {

    }

    protected interface ICallbackEvent {
        void onClick(VoContents vo);

        void getPage(int page);
    }


    private class CommonListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private SparseBooleanArray selectedItems;

        private CommonListAdapter() {
            selectedItems = new SparseBooleanArray();
        }

        private void addDataToBottom(PBean newModelData) {
            list.add(newModelData);
            notifyItemInserted(list.size() - 1);
        }

        private void deleteLoading() {
            if (list != null && list.size() > 0) {
                list.remove(list.size() - 1);
                notifyItemRemoved(list.size() - 1);
                addDataToBottom(new EmptyVo());
            }
        }

        private void deleteitem(int position) {
            list.remove(position);
            notifyItemRemoved(position);
        }


        private void updateItem(int position) {
            notifyItemChanged(position);
        }

        @Override
        public int getItemViewType(int position) {
            return list.get(position).getViewType();
        }

        @Override
        public long getItemId(int position) {
            return list.get(position).hashCode();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            if (viewType == PBean.Companion.getTYPE_CONTENTS()) {
                WalletItemBinding binding = WalletItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
                return new ContentsViewHolder(binding);
            } else if (viewType == PBean.Companion.getTYPE_EMPTY()) {
                CommonEmptyItemBinding binding = CommonEmptyItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
                return new EmptyViewHolder(binding);
            } else {
                CommonLoadingItemBinding binding = CommonLoadingItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
                return new ProgressViewHolder(binding);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            if (list.get(position) == null) return;
            if (holder instanceof EmptyViewHolder) {
                onEmptyHolder(holder, position);
            } else if (holder instanceof ContentsViewHolder) {
                onContentsHolder(holder, position);
            } else {
                onLoadingHolder(holder, position);
            }
        }

        private void onContentsHolder(RecyclerView.ViewHolder holder, final int position) {
            final ContentsViewHolder viewHolder = (ContentsViewHolder) holder;
            final VoContents model = (VoContents) list.get(position);
            viewHolder.bind(model);

        }


        private void onLoadingHolder(RecyclerView.ViewHolder holder, int position) {
            final ProgressViewHolder viewHolder = (ProgressViewHolder) holder;
            viewHolder.itemView.setActivated(selectedItems.get(position, false));
        }

        private void onEmptyHolder(RecyclerView.ViewHolder holder, int position) {
            final EmptyViewHolder viewHolder = (EmptyViewHolder) holder;
            viewHolder.itemView.setActivated(selectedItems.get(position, false));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }


        private final class ContentsViewHolder extends RecyclerView.ViewHolder {
            private WalletItemBinding binding;

            private ContentsViewHolder(WalletItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            private void bind(VoContents model) {
                binding.setVariable(BR.item, model);
                binding.setVariable(BR.viewmodel, vm);
            }
        }

        private final class ProgressViewHolder extends RecyclerView.ViewHolder {

            private ProgressViewHolder(CommonLoadingItemBinding binding) {
                super(binding.getRoot());
            }
        }

        private final class EmptyViewHolder extends RecyclerView.ViewHolder {
            private EmptyViewHolder(CommonEmptyItemBinding binding) {
                super(binding.getRoot());
            }
        }
    }
}
package com.sktelecom.showme.Wallet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.sktelecom.showme.Main.Model.VoContents;
import com.sktelecom.showme.base.Network.SmartNetWork;
import com.sktelecom.showme.base.view.PController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ratpoisonfactory.base.util.Log;

import java.util.ArrayList;
import java.util.Objects;


public class WalletLeftCont extends PController {
    private WalletBodyFrag motherfrag;
    private ICallbackToAct mICallbackToAct = null;
    private WalletCommonVM vm;
    private Activity pAct;


    protected WalletLeftCont(Activity pAct, WalletBodyFrag motherfrag, ICallbackToAct mICallbackToAct) {
        super(pAct);
        this.pAct = pAct;
        this.motherfrag = motherfrag;
        this.mICallbackToAct = mICallbackToAct;
    }

    protected void asViewModelCreate() {
        vm = ViewModelProviders.of(motherfrag).get("LEFT", WalletCommonVM.class);
        vm.setCallback(this::callList);
    }


    private void callList() {
        final JSONObject param = new JSONObject();
        try {
            param.put("vocaType", "GT");
            param.put("id", "aaaa@aaaa");
            param.put("startRow", "0");
            param.put("numberOfRow", 50);
            param.put("contentsType", "115");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = SmartNetWork.Companion.getURL() + "getSellingContentsList_2";

        Log.INSTANCE.i("DUER", url);
        new SmartNetWork().getCommonDataPostParam(Objects.requireNonNull(motherfrag.getActivity()), url, param, new SmartNetWork.SmartNetWorkListener() {
            @Override
            public void onResponse(int Tag, @NonNull JSONObject response) {
                try {
                    Log.INSTANCE.i(" 결과 getSellingContentsList_2=" + response.toString());
                    Message msg = updateHandler.obtainMessage();
                    String isReturn = response.optString("return");
                    if (isReturn != null && isReturn.equals("true")) {
                        JSONArray array = response.optJSONArray("result");
                        JSONObject row;
                        ArrayList<VoContents> fruitsStringList = new ArrayList<>();

                        for (int i = 0; i < array.length(); ++i) {
                            row = array.optJSONObject(i);
                            fruitsStringList.add(new VoContents(row.optString("TITLE"), "type", "desc", row.optString("CONTENTS_ID")));
                            msg.obj = fruitsStringList;
                        }
                        msg.what = 0;
                    } else {
                        msg.what = 0;
                    }
                    updateHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(int Tag, @NonNull VolleyError error) {
                Log.INSTANCE.i("DUER...............................>>ERROR_Tag", Tag);
                Message msg = updateHandler.obtainMessage();
                msg.what = 100;
                updateHandler.sendMessageDelayed(msg, 10);
            }
        });
    }


    @SuppressLint("HandlerLeak")
    private Handler updateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (motherfrag != null) {
                if (msg.what == 100) {
                    Toast.makeText(frag.getPActivity(), "인터넷에 연결할 수 없습니다.", Toast.LENGTH_SHORT).show();
//                    frag.getActivity().finish();
                } else if (msg.what == 0) {
                    vm.fruitList.setValue(msg.obj);

                }
            }
        }
    };

    protected WalletCommonVM asViewModelResume() {
        if (vm == null)
            asViewModelCreate();
        return vm;
    }


    protected interface ICallbackToAct {
        void selected(int position);

        void scrolled(int position, float positionOffset, int positionOffsetPixels);
    }

}

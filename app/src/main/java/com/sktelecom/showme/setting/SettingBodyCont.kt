package com.sktelecom.showme.setting

import android.content.Context
import android.content.Intent
import android.view.View
import com.sktelecom.showme.Main.my.level.LevelActivity
import com.sktelecom.showme.Main.my.profile.ProfileActivity
import com.sktelecom.showme.R
import com.sktelecom.showme.base.view.PController
import com.sktelecom.showme.base.view.PFragment
import kotlinx.android.synthetic.main.setting_body_frag.view.*


class SettingBodyCont constructor(context: Context, mICallbackToAct: ICallbackToAct) : PController(context) {

    private val cb = CallbackEvent()
    internal lateinit var mICallbackToAct: ICallbackToAct


    init {
        this.mICallbackToAct = mICallbackToAct
    }

    fun asFragCreate(): PFragment {
        frag = SettingBodyFrag.with(this, cb)

        return frag
    }


    fun onClickProfile() {
        val intent = Intent(frag.pActivity, LevelActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        frag.pActivity.startActivityForResult(intent, 1)
    }

    fun onClick(v: View) {
        var intent: Intent
        when (v.id) {

            R.id.tvProfile -> {
                intent = Intent(frag.pActivity, ProfileActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                frag.pActivity.startActivityForResult(intent, 1)
            }
            R.id.tvChangePassword -> {
                val intent = Intent(frag.pActivity, ProfileActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                frag.pActivity.startActivityForResult(intent, 1)
            }
            R.id.tvActivyGroup -> {
                val intent = Intent(frag.pActivity, ProfileActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                frag.pActivity.startActivityForResult(intent, 1)
            }
            R.id.tvVerifiy -> {
                val intent = Intent(frag.pActivity, ProfileActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                frag.pActivity.startActivityForResult(intent, 1)
            }

            R.id.tvPushSet -> {
                val intent = Intent(frag.pActivity, ProfileActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                frag.pActivity.startActivityForResult(intent, 1)
            }
            R.id.tvRewordRate -> {
                val intent = Intent(frag.pActivity, ProfileActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                frag.pActivity.startActivityForResult(intent, 1)
            }
            R.id.tvCusCare -> {
                val intent = Intent(frag.pActivity, ProfileActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                frag.pActivity.startActivityForResult(intent, 1)
            }

            R.id.tvLogOut -> {
                val intent = Intent(frag.pActivity, ProfileActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                frag.pActivity.startActivityForResult(intent, 1)
            }
        }

    }

    protected inner class CallbackEvent : SettingBodyFrag.ICallbackEvent {

    }


    interface ICallbackToAct {

    }

}

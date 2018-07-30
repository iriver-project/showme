package com.sktelecom.showme.Main

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.sktelecom.showme.R
import com.sktelecom.showme.base.util.CommonUtil
import com.sktelecom.showme.base.util.Log
import com.sktelecom.showme.base.view.PActivity
import com.sktelecom.showme.databinding.ActivityMainBinding
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import com.sktelecom.showme.Main.common.CommonProfileActivity
import com.sktelecom.showme.Main.feed.FeedBodyVM
import com.sktelecom.showme.Main.home.HomeBodyVM
import com.sktelecom.showme.Main.my.MyBodyVM
import com.sktelecom.showme.Main.my.level.LevelActivity
import com.sktelecom.showme.Main.notification.NotificationBodyVM
import com.sktelecom.showme.Main.tv.TvBodyVM
import com.sktelecom.showme.Main.upload.VideoPlayerActivity
import com.sktelecom.showme.selectlogin.SelectLoginActivity


class MainActivity : PActivity() {

    internal lateinit var binding: ActivityMainBinding
    internal lateinit var prevBottomNavigation: MenuItem
//    internal lateinit var mainBody: MainBodyCont

    internal lateinit var homeVm: HomeBodyVM
    internal lateinit var tvVm: TvBodyVM
    internal lateinit var feedVm: FeedBodyVM
    internal lateinit var notiVm: NotificationBodyVM
    internal lateinit var myVm: MyBodyVM

    override fun onAfaterCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        Log.i(CommonUtil.with.now())
        binding.executePendingBindings()

        binding.bottomNavigation.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.getItemId()) {
                    R.id.action_home -> {
                        pFragReplace(binding.frameBody.id, homeVm.frag)
                    }
                    R.id.action_tv -> {
                        pFragReplace(binding.frameBody.id, tvVm.frag)
                    }
                    R.id.action_feed -> {
//                        pFragReplace(binding.frameBody.id, feedVm.frag)
                        startActivityForResult(Intent.createChooser(Intent(Intent.ACTION_GET_CONTENT).setType("video/*"), "Select A 비됴"), REQUEST_SELECT_VIDEO)
                    }
                    R.id.action_notice -> {
                        pFragReplace(binding.frameBody.id, notiVm.frag)
                    }
                    R.id.action_my -> {
                        pFragReplace(binding.frameBody.id, myVm.frag)
                    }
                }
                return true
            }
        })
        prevBottomNavigation = binding.bottomNavigation.getMenu().getItem(1);
        selectedPage(1)
//        val layoutParams = binding.bottomNavigation.getLayoutParams() as CoordinatorLayout.LayoutParams
//        layoutParams.behavior = BottomNavigationViewBehavior()

//        val bodyvm = ViewModelProviders.of(this).get(FeedBodyVM::class.java)
//        mainBody = MainBodyCont(this, object : MainBodyCont.ICallbackToAct {
//            override fun selected(position: Int) {
//                selectedPage(position)
//            }
//
//            override fun scrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//
//            }
//
//        })
//        pFragAdd(binding.frameBody.id, mainBody.asFragCreate())

        homeVm = ViewModelProviders.of(this).get(HomeBodyVM::class.java)
        tvVm = ViewModelProviders.of(this).get(TvBodyVM::class.java)
        feedVm = ViewModelProviders.of(this).get(FeedBodyVM::class.java)
        notiVm = ViewModelProviders.of(this).get(NotificationBodyVM::class.java)
        myVm = ViewModelProviders.of(this).get(MyBodyVM::class.java)


        homeVm.asFragCreate()
        tvVm.asFragCreate()
        feedVm.asFragCreate()
        notiVm.asFragCreate()
        myVm.asFragCreate()

        pFragAdd(binding.frameBody.id, tvVm.asFragResume())

        startReg()

//        val intent = Intent(this, LevelActivity::class.java)
////        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
//        startActivityForResult(intent, 1)
    }


    internal fun startReg() {
        val intent = Intent(pCon, SelectLoginActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
    }

    internal fun selectedPage(position: Int) {
        prevBottomNavigation.setChecked(false);
        prevBottomNavigation = binding.bottomNavigation.getMenu().getItem(position);
        prevBottomNavigation.setChecked(true);
    }


    internal lateinit var picAddr: String

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data == null) return

        if (resultCode == RESULT_OK) {  //게시판 게시물 엑티비티에서 넘어온결과.
            if (requestCode == REQUEST_SELECT_VIDEO) {
                val imageUri = data.data

                try {
                    Log.d("image selected path", imageUri!!.path)
                    Log.i("DUER", CommonUtil.with.getPathFromUri(this, imageUri))
                    picAddr = CommonUtil.with.getPathFromUri(this, imageUri)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("image selected path", imageUri!!.path)
                    Log.i("DUER", CommonUtil.with.getPath(this, imageUri)!!)
                    picAddr = CommonUtil.with.getPath(this, imageUri)!!
                    saveProfileAccount()
                    return
                }

                if (picAddr == null)
                    picAddr = CommonUtil.with.getPath(this, imageUri)!!
                saveProfileAccount()

            }
        }
    }

    fun saveProfileAccount() {
        val intent = Intent(this, VideoPlayerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.putExtra(VideoPlayerActivity.ACT_TO_ACT_INNER_MOV_ADDR, picAddr)
        startActivityForResult(intent, 5)
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.i("onDestroy:")
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

}

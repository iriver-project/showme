package com.sktelecom.showme.Main

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import com.sktelecom.showme.Main.feed.FeedBodyVM
import com.sktelecom.showme.Main.home.HomeBodyVM
import com.sktelecom.showme.Main.my.MyBodyVM
import com.sktelecom.showme.Main.notification.NotificationBodyVM
import com.sktelecom.showme.Main.tv.TvBodyVM
import com.sktelecom.showme.Main.upload.VideoPlayerActivity
import com.sktelecom.showme.R
import com.sktelecom.showme.base.util.CommonUtil
import com.sktelecom.showme.base.util.Log
import com.sktelecom.showme.base.view.PActivity
import com.sktelecom.showme.databinding.ActivityMainBinding
import com.sktelecom.showme.selectlogin.SelectLoginActivity


class MainActivity : PActivity() {

    internal lateinit var binding: ActivityMainBinding
    internal lateinit var prevBottomNavigation: MenuItem
//    internal lateinit var mainBody: MainBodyCont

    internal lateinit var tvVm: TvBodyVM
    internal lateinit var stageVm: HomeBodyVM
    internal lateinit var uploadVm: FeedBodyVM
    internal lateinit var newsVm: NotificationBodyVM
    internal lateinit var myVm: MyBodyVM

    private var singleMarginLeftSize = 0f

    override fun onAfaterCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        Log.i(CommonUtil.with.now())
        binding.executePendingBindings()

        val display = pCon.resources.displayMetrics
        singleMarginLeftSize = display.widthPixels.toFloat() / 5

        binding.bottomNavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_tv -> {
                    pFragReplace(binding.frameBody.id, tvVm.frag)
                }
                R.id.action_stage -> {
                    pFragReplace(binding.frameBody.id, stageVm.frag)
                }
                R.id.action_upload -> {
//                        pFragReplace(binding.frameBody.id, feedVm.frag)
                    if (!checkReadExternalStoragePermission()) {
                        return@OnNavigationItemSelectedListener false
                    }
                    startActivityForResult(Intent.createChooser(Intent(Intent.ACTION_GET_CONTENT).setType("video/*"), "Select A 비됴"), REQUEST_SELECT_VIDEO)
                }
                R.id.action_news -> {
                    pFragReplace(binding.frameBody.id, newsVm.frag)
                }
                R.id.action_my -> {
                    pFragReplace(binding.frameBody.id, myVm.frag)
                }
            }
//                var x:Int = singleMarginLeftSize.roundToInt()
//                var y:Int = binding.bottomNavigation.y.roundToInt()
//
//                UAnimation.with().toggleInformationViewToBeVisibleOnly(applicationContext, x, y, binding.frameBody)
            true
        })
        prevBottomNavigation = binding.bottomNavigation.menu.getItem(1)
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

        tvVm = ViewModelProviders.of(this).get(TvBodyVM::class.java)
        stageVm = ViewModelProviders.of(this).get(HomeBodyVM::class.java)
        uploadVm = ViewModelProviders.of(this).get(FeedBodyVM::class.java)
        newsVm = ViewModelProviders.of(this).get(NotificationBodyVM::class.java)
        myVm = ViewModelProviders.of(this).get(MyBodyVM::class.java)


        tvVm.asFragCreate()
        stageVm.asFragCreate()
        uploadVm.asFragCreate()
        newsVm.asFragCreate()
        myVm.asFragCreate()

        pFragAdd(binding.frameBody.id, stageVm.asFragResume())

//        startReg()

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
        prevBottomNavigation.isChecked = false
        prevBottomNavigation = binding.bottomNavigation.menu.getItem(position)
        prevBottomNavigation.isChecked = true
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

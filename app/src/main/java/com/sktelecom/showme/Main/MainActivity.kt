package com.sktelecom.showme.Main

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.sktelecom.showme.R
import com.sktelecom.showme.base.util.CommonUtil
import com.sktelecom.showme.base.util.Log
import com.sktelecom.showme.base.view.PActivity
import com.sktelecom.showme.databinding.ActivityMainBinding
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import com.sktelecom.showme.Main.feed.FeedBodyVM
import com.sktelecom.showme.Main.home.HomeBodyVM
import com.sktelecom.showme.Main.my.MyBodyVM
import com.sktelecom.showme.Main.notification.NotificationBodyVM


class MainActivity : PActivity() {

    internal lateinit var binding: ActivityMainBinding
    internal lateinit var prevBottomNavigation: MenuItem
//    internal lateinit var mainBody: MainBodyCont

    internal lateinit var homeVm: HomeBodyVM
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
                        pFragReplace(binding.frameBody.id, homeVm.frag)
                    }
                    R.id.action_feed -> {
                        pFragReplace(binding.frameBody.id, feedVm.frag)
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
        feedVm = ViewModelProviders.of(this).get(FeedBodyVM::class.java)
        notiVm = ViewModelProviders.of(this).get(NotificationBodyVM::class.java)
        myVm = ViewModelProviders.of(this).get(MyBodyVM::class.java)

        homeVm.asFragCreate()
        feedVm.asFragCreate()
        notiVm.asFragCreate()
        myVm.asFragCreate()

        pFragAdd(binding.frameBody.id, feedVm.asFragResume())
    }


    internal fun selectedPage(position: Int) {
        prevBottomNavigation.setChecked(false);
        prevBottomNavigation = binding.bottomNavigation.getMenu().getItem(position);
        prevBottomNavigation.setChecked(true);
    }


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

}

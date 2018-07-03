package com.sktelecom.showme.Main

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.sktelecom.showme.R
import com.sktelecom.showme.base.util.CommonUtil
import com.sktelecom.showme.base.util.Log
import com.sktelecom.showme.base.view.PActivity
import com.sktelecom.showme.databinding.ActivityMainBinding
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import android.support.design.widget.CoordinatorLayout




class MainActivity : PActivity() {

    internal lateinit var binding: ActivityMainBinding
    internal lateinit var prevBottomNavigation: MenuItem
    internal lateinit var mainBody: MainBodyCont

    override fun onAfaterCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        Log.i(CommonUtil.with.now())
        binding.executePendingBindings()


        binding.bottomNavigation.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.getItemId()) {
                    R.id.action_home -> {
                        mainBody.selected(0)
                    }
                    R.id.action_feed -> {
                        mainBody.selected(1)
                    }
                    R.id.action_notice -> {
                        mainBody.selected(2)
                    }
                    R.id.action_my -> {
                        mainBody.selected(3)
                    }
                }
                return true
            }
        })
        prevBottomNavigation = binding.bottomNavigation.getMenu().getItem(0);

        val layoutParams = binding.bottomNavigation.getLayoutParams() as CoordinatorLayout.LayoutParams
        layoutParams.behavior = BottomNavigationViewBehavior()

//        val bodyvm = ViewModelProviders.of(this).get(FeedBodyVM::class.java)
        mainBody = MainBodyCont(this, object : MainBodyCont.ICallbackToAct {
            override fun selected(position: Int) {
                selectedPage(position)
            }

            override fun scrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

        })
        pFragAdd(binding.frameBody.id, mainBody.asFragCreate())
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

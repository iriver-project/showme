package com.sktelecom.showme.base.view.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator

import com.sktelecom.showme.R


class UAnimation private constructor() {


    inner class WAnimation internal constructor() {
        private val isFirst = true
        private var callback: UAnimationCallback? = null

        fun setCalbackEndOfAnimation(callback: UAnimationCallback): WAnimation {
            this.callback = callback
            return this
        }

        fun toggleInformationView(pCon: Context, view: View, infoContainer: View): Int {

            val cx = (view.left + view.right) / 2
            val cy = (view.top + view.bottom) / 2
            return toggleInformationView(pCon, cx, cy, infoContainer)
        }

        fun toggleInformationView(pCon: Context, cx: Int, cy: Int, infoContainer: View): Int {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //            final View infoContainer = root.findViewById(R.id.information_container);
                val radius = Math.max(infoContainer.width, infoContainer.height) * 2.0f

                val reveal: Animator
                if (infoContainer.visibility == View.GONE) {
                    infoContainer.visibility = View.VISIBLE
                    reveal = ViewAnimationUtils.createCircularReveal(infoContainer, cx, cy, 0f, radius)
                    reveal.interpolator = AccelerateInterpolator(2.0f)
                } else {
                    reveal = ViewAnimationUtils.createCircularReveal(infoContainer, cx, cy, radius, 0f)
                    reveal.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            infoContainer.visibility = View.GONE
                            if (callback != null)
                                callback!!.onAnimationEnd()
                        }
                    })
                    reveal.interpolator = DecelerateInterpolator(2.0f)
                }
                reveal.duration = 600
                reveal.start()
            } else {
                if (infoContainer.visibility == View.GONE) {
                    infoContainer.visibility = View.VISIBLE
                    val fadeInAnimation = AnimationUtils.loadAnimation(pCon, R.anim.fade_in)
                } else {
                    val fadeOutAnimation = AnimationUtils.loadAnimation(pCon, R.anim.fade_out)
                    fadeOutAnimation.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(animation: Animation) {}

                        override fun onAnimationRepeat(animation: Animation) {}

                        override fun onAnimationEnd(animation: Animation) {
                            if (callback != null)
                                callback!!.onAnimationEnd()
                        }
                    })
                    infoContainer.startAnimation(fadeOutAnimation)
                    infoContainer.visibility = View.GONE
                }

            }
            return infoContainer.visibility
        }


        fun toggleInformationViewToBeVisibleOnly(pCon: Context, cx: Int, cy: Int, infoContainer: View): Int {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //            final View infoContainer = root.findViewById(R.id.information_container);
                val radius = Math.max(infoContainer.width, infoContainer.height) * 2.0f

                val reveal: Animator
                infoContainer.visibility = View.VISIBLE
                reveal = ViewAnimationUtils.createCircularReveal(infoContainer, cx, cy, 0f, radius)
                reveal.interpolator = AccelerateInterpolator(2.0f)
                reveal.duration = 600
                reveal.start()
            } else {
                infoContainer.visibility = View.VISIBLE
                val fadeInAnimation = AnimationUtils.loadAnimation(pCon, R.anim.fade_in)

            }
            return infoContainer.visibility
        }
    }

    interface UAnimationCallback {
        fun onAnimationEnd()

    }

    private var SINGLE_W: WAnimation = WAnimation()
    fun get(): WAnimation {
        return SINGLE_W
    }

    companion object {
        private lateinit var SINGLE_U: UAnimation


        fun with(): WAnimation {
            SINGLE_U = UAnimation()

            return SINGLE_U.get()
        }
    }
}

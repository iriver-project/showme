package com.sktelecom.showme.base.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class PFragment : Fragment() {
    lateinit var pActivity: Activity
    lateinit var pCon: Context


    override fun onCreate(savedInstanceState: Bundle?) {
        // Activity도 아직 생성되기 전이다. 이후  onCrateView 불림
        pActivity = activity as Activity
        pCon = activity as Context
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Activity는 아직 생성되기 전이다. layout에 관련된 사항만 정의한다. getView()로 자체의 뷰를 부를 수
        // 있다.
        return abCreateView(inflater, container)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        // Activity가 초기화 되고 난 이후에 생성된다. 실제 운영은 여기서 시작된다.
        super.onActivityCreated(savedInstanceState)
        onCreated()
    }

    abstract fun onCreated()
    abstract fun abCreateView(inflater: LayoutInflater, container: ViewGroup?): View

}
package com.sktelecom.showme.base.Model

data class VoUserInfo(val id: String, val nickName: String, val level: String, val follower: String, val following: String,
                      val desc: String, val imgUrl: String, val userType: String) : PBean() {

    init {

        viewType = TYPE_USER_INFO
    }

}

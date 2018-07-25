package com.sktelecom.showme.base.Model

data class VoVideo(val contentsId: String, val heart: String, val title: String, val artistName: String, val artistId: String, val artistImgUrl: String, val imgUrl: String, val videoUrl: String) : PBean() {

    init {
        viewType = TYPE_ITEM_VIDEO
    }

}

package com.sktelecom.showme.base.Model

data class VoArtist(val atstId: String, val atstNm: String, val atstThumbnail: String, val atstStagRank: Int) : PBean() {

    init {

        viewType = TYPE_ARTIST
    }

}

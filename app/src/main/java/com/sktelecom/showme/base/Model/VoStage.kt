package com.sktelecom.showme.base.Model

data class VoStage(val stagNo: Long, val stagSq: Int, val stagNm: String, val stagStDt: String, val stagEnDt: String,
                   val stagPlayCnt: Long, val stagAtstCnt: Long, val stagVoteCnt: Long, val stagRwrdTot: Double,
                   val activeYn: String, val noticeNo: Long, val noticeNm: String, val noticeIcon: String) : PBean() {

    init {

        viewType = TYPE_STAGE
    }

}

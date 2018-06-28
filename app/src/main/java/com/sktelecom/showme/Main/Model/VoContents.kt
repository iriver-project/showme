package com.sktelecom.showme.Main.Model

data class VoContents(val CONTENTS_ID: String, val CONTENTS_TYPE: String, val DESC1: String, val CONTENTS_URL: String) : PBean() {

    init {

        viewType = PBean.TYPE_CONTENTS
    }

}

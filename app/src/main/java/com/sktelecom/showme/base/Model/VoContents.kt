package com.sktelecom.showme.base.Model

data class VoContents(val CONTENTS_ID: String, val CONTENTS_TYPE: String, val DESC1: String, val CONTENTS_URL: String) : PBean() {

    init {

        viewType = TYPE_CONTENTS
    }

}

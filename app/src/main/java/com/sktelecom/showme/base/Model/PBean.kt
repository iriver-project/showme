package com.sktelecom.showme.base.Model

abstract class PBean {
    var viewType = 0

    companion object {
        var TYPE_EMPTY = 0
        var TYPE_LOADING = 202
        var TYPE_CONTENTS = 1

        var TYPE_USER_INFO = 101;
        var TYPE_ITEM_VIDEO = 102;
    }

}

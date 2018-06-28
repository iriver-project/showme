package com.sktelecom.showme.Main.Model

abstract class PBean {
    var viewType = 0

    companion object {
        var TYPE_EMPTY = 0
        var TYPE_LOADING = 202
        var TYPE_CONTENTS = 1
    }

}

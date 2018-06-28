package com.sktelecom.showme.base.view

import android.app.Activity
import android.arch.lifecycle.ViewModel

abstract class PViewModel : ViewModel() {
    lateinit var frag: PFragment

    abstract fun asFragCreate(): PFragment

}

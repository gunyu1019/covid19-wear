package kr.yhs.covid.activity

import android.app.Activity
import android.view.View
import kr.yhs.covid.adapter.viewPage.ViewData

abstract class MainResourceActivity {
    var id = 0
    lateinit var view: View
    lateinit var context: Activity

    fun loadData(data: ViewData) {
        if (data.activity != this)
            return
        this.id = data.id
        this.view = data.view!!
        this.context = data.context
    }

    abstract fun onCreate()
}
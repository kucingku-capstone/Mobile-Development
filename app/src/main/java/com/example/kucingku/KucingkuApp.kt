package com.example.kucingku

import android.app.Application
import com.example.kucingku.utils.PrefsHelper

class KucingkuApp : Application() {

    override fun onCreate() {
        super.onCreate()
        PrefsHelper.init(this)
    }
}

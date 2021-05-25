package com.example.y.simplememo

import android.app.Application
import android.graphics.Color
import android.view.View
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        //データベースを初期化
        Realm.init(this)

        //デフォルト設定を適用
        val config = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(config)



    }
}
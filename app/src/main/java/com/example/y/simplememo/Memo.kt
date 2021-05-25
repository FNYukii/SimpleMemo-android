package com.example.y.simplememo

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Memo : RealmObject(){
    @PrimaryKey
    var id: Long = 0L
    var content: String = ""
}
package com.example.y.simplememo

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.one_memo.view.*

//ViewHolderのプロパティとone_memoのViewを対応させる。
class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var contentText: TextView? = null
    init {
        contentText = itemView.contentText
    }
}
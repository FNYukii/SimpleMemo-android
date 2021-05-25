package com.example.y.simplememo

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmResults
import java.text.FieldPosition

class CustomRecyclerViewAdapter(realmResults: RealmResults<Memo>): RecyclerView.Adapter<ViewHolder>(){

    private val rResults: RealmResults<Memo> = realmResults
    private var isFirstView = true


    //新しいViewを生成するonCreateViewHolderメソッド
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {

        if (isFirstView){
            //parentがRecyclerViewのfirst_memoを生成。
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.first_memo, parent, false)
            isFirstView = false
            //できたViewを引数にViewHolderオブジェクトを生成。
            return ViewHolder(view)
        } else {
            //parentがRecyclerViewのone_memoを生成。
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.one_memo, parent, false)
            //できたViewを引数にViewHolderオブジェクトを生成。
            return ViewHolder(view)
        }






    }


    //データセット(rResults)のサイズを返すgetItemCountメソッド
    override fun getItemCount(): Int {
        return rResults.size
    }


    //生成したView内の設定をするonBindViewHolderメソッド
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //レコードを取得
        val memo = rResults[position]
        //生成されたitemViewのcontentTextに、Realmからのデータをセット
        holder.contentText?.text = memo?.content.toString()

        //itemViewがクリックされたら、EditActivityへ遷移
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, EditActivity::class.java)
            intent.putExtra("id", memo?.id)
            it.context.startActivity(intent)
        }

    }


}
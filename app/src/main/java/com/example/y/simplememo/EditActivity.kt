package com.example.y.simplememo

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {


    //Realmのインスタンス宣言
    private lateinit var realm: Realm

    //よく使うidを宣言
    private var id : Long = 0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        //statusBarとnavigationBarのテーマ切り替え
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && resources.configuration.isNightModeActive) {
            //dark theme
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        } else {
            //light theme
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }




        //get Realm instance
        realm = Realm.getDefaultInstance()

        //get intent extra data
        id = intent.getLongExtra("id", 0L)

        //set values to EditTextViews
        if(id != 0L){
            //id列の値が変数idと一致するレコードを検索して、変数memoに取得。
            val memo = realm.where<Memo>().equalTo("id", id).findFirst()
            //contentEditのtextに、レコードのcontentをセット。
            contentEdit.setText(memo?.content)
            //削除を可能にする。
            deleteBtn.visibility = View.VISIBLE
        } else {
            deleteBtn.visibility = View.INVISIBLE
        }

        //click save button
        backBtn.setOnClickListener {
            if(!contentEdit.text.isNullOrEmpty()){
                saveMemo()
            } else {
                deleteMemo()
            }
        }

        //click delete button
        deleteBtn.setOnClickListener {
            deleteMemo()
        }

        //contentEditが編集された
        contentEdit.addTextChangedListener {
            if(contentEdit.text.isNullOrEmpty()){
                deleteBtn.visibility = View.INVISIBLE
            } else {
                deleteBtn.visibility = View.VISIBLE
            }
        }




    }


    private fun saveMemo(){
        //reset variables
        var content: String = ""

        //contentEditのtextがnullでも空でも空白でもないなら、変数contentに格納
        if(!contentEdit.text.isNullOrEmpty()){
            content = contentEdit.text.toString()
        }

        //レコードを追加
        if(id == 0L){
            //Realm transaction
            realm.executeTransaction {
                //where()メソッドでid列最大の値を検索し、それを変数latestIdに格納
                var latestId = realm.where<Memo>().max("id")
                //latestIdがnull以外ならそのまま、nullなら0Lにする。そして型変換。
                latestId = (latestId ?: 0L).toLong()
                //新しいidを決定
                val newId = latestId + 1
                //変数newIdを主キーをして、レコードを作成
                val memo = realm.createObject<Memo>(newId)
                //レコードのcontent列に変数contentをセット
                memo.content = content
            }
            //Toast
//            Toast.makeText(applicationContext, "新しいメモを保存しました。", Toast.LENGTH_SHORT).show()
        }

        //レコードを更新
        if(id != 0L){
            //Realm transaction
            realm.executeTransaction {
                //id列の値が変数idと一致するレコードを検索して、変数memoに取得。
                val memo = realm.where<Memo>().equalTo("id",id)?.findFirst()
                //レコードのcontent列に変数contentをセット
                memo?.content = content
            }
            //Toast
//            Toast.makeText(applicationContext, "メモを更新しました。", Toast.LENGTH_SHORT).show()
        }

        finish()
    }


    private fun deleteMemo(){
        //レコードを削除
        realm.executeTransaction {
            //id列の値が変数idと一致するレコードを検索して、変数memoに取得。
            val memo = realm.where<Memo>().equalTo("id", id)?.findFirst()
            //レコードを削除する。
            memo?.deleteFromRealm()
        }
        //Toast
//        Toast.makeText(applicationContext, "メモを削除しました。", Toast.LENGTH_SHORT).show()
        finish()
    }


    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

























}
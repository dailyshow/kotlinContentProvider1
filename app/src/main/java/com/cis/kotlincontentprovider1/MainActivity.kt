package com.cis.kotlincontentprovider1

import android.content.Context
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        insertBtn.setOnClickListener { view ->
            val helper = DBHelper(this)
            val db = helper.writableDatabase

            val sql = "insert into TestTable (textData, intData, floatData, dateData) values (?, ?, ?, ?)"

            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = sdf.format(Date())

            val arg1 = arrayOf("문자열1", "100", "11.11", date)
            val arg2 = arrayOf("문자열2", "200", "22.22", date)

            db.execSQL(sql, arg1)
            db.execSQL(sql, arg2)

            db.close()

            tv.text = "저장 완료"
        }

        selectBtn.setOnClickListener { view ->
            selectData(this)
        }

        updateBtn.setOnClickListener { view ->
            val helper = DBHelper(this)
            val db = helper.writableDatabase

            val sql = "update TestTable set textData=? where idx=?"
            val args = arrayOf("문자열3", "1")

            db.execSQL(sql, args)

            db.close()

            tv.text = "수정 완료"
        }

        deleteBtn.setOnClickListener { view ->

            var dialog = DialogFragment()
            dialog.isCancelable = false
            dialog.show(supportFragmentManager, "tag")
        }
    }

    fun selectData(context: Context) {
        val helper = DBHelper(context)
        val db = helper.writableDatabase

        val sql = "select * from TestTable"

        val cursor: Cursor = db.rawQuery(sql, null)

        tv.text = ""

        while (cursor.moveToNext()) {
            val idxPos = cursor.getColumnIndex("idx")
            val textDataPos = cursor.getColumnIndex("textData")
            val intDataPos = cursor.getColumnIndex("intData")
            val floatDataPos = cursor.getColumnIndex("floatData")
            val dateDataPos = cursor.getColumnIndex("dateData")

            val idx = cursor.getInt(idxPos)
            val textData = cursor.getString(textDataPos)
            val intData = cursor.getInt(intDataPos)
            val floatData = cursor.getDouble(floatDataPos)
            val dateData = cursor.getString(dateDataPos)

            tv.append("idx : ${idx}\n")
            tv.append("textData : ${textData}\n")
            tv.append("intData : ${intData}\n")
            tv.append("floatData : ${floatData}\n")
            tv.append("dateData : ${dateData}\n\n")
        }

        db.close()
    }
}

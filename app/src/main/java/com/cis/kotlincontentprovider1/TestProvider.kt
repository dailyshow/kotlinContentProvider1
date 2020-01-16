package com.cis.kotlincontentprovider1

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

// content provider는 애플리케이션이 저장한 데이터를 다른 애플리케이션이 사용할 수 있도록 제공하는 개념
// content provider를 이용하여 다른 앱에서 사용할 수 있는 메소드들
class TestProvider : ContentProvider() {

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val helper = DBHelper(context!!)
        val db = helper.writableDatabase

        return db.delete("TestTable", selection, selectionArgs)
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        var helper = DBHelper(context!!)
        var db = helper.writableDatabase

        db.insert("TestTable", null, values)

        return uri
    }

    override fun onCreate(): Boolean {
        return false
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val helper = DBHelper(context!!)
        val db = helper.writableDatabase

        return db.query("TestTable", projection, selection, selectionArgs, null, null, sortOrder)
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        val helper = DBHelper(context!!)
        val db = helper.writableDatabase

        return db.update("TestTable", values, selection, selectionArgs)
    }
}

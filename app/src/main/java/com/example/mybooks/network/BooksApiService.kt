package com.example.mybooks.network

import android.util.Log
import com.example.mybooks.model.BooksViewModel
import com.example.mybooks.model.WriterItem
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import org.json.JSONObject
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response


fun getRequest(url: String, bvm: BooksViewModel) {
    val client = OkHttpClient()
    val tag = "MyBook2"

    val request = Request.Builder()
        .url(url)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: java.io.IOException) {
            Log.v(tag, e.toString())
        }

        override fun onResponse(call: Call, response: Response) {
            // Handle success
            val json = Json { ignoreUnknownKeys = true }
            val result = response.body.string()

            val resultJson = JSONObject(result)
            val bookDate = resultJson["date"].toString()
            bvm.setBookDate(bookDate)
            val bookData = resultJson["data"].toString()
            val jsonData = json.parseToJsonElement(bookData).jsonArray

            for ( w in jsonData.iterator() ) {
                val wi: WriterItem = bvm.createWriter(w.jsonObject)
                bvm.addWriter(wi)
            }
        }
    })
}

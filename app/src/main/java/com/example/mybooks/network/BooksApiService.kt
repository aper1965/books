package com.example.mybooks.network

import android.util.Log
import com.example.mybooks.model.BooksData
import com.example.mybooks.model.WriterItem
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Callback
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlin.reflect.typeOf


fun getRequest(url: String, bd: BooksData) {
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
            val jsonData = json.parseToJsonElement(result).jsonArray
            Log.v(tag, result)

            for ( w in jsonData.iterator() ) {
                val wi: WriterItem = bd.createWriter(w.jsonObject)
                bd.addWriter(wi)
            }
            Log.v(tag, "Ready")

        }
    })
}

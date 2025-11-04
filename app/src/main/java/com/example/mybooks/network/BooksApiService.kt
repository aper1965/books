package com.example.mybooks.network

import android.util.Log
import com.example.mybooks.model.BooksViewModel
import com.example.mybooks.model.WriterItem
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject


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

fun postRequest(url: String, name: String, title: String, date: String) {
    val client = OkHttpClient()
    val tag = "MyBook10"

    val mediaType = "application/json; charset=utf-8".toMediaType()

    val jsonObject = JSONObject()
    jsonObject.put("name", name)
    jsonObject.put("title", title)
    jsonObject.put("date", date)

    val body = jsonObject.toString().toRequestBody(mediaType)

    val request = Request.Builder()
        .url(url)
        .post(body)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: java.io.IOException) {
            Log.v(tag, e.toString())
        }

        override fun onResponse(call: Call, response: Response) {
            // Handle success
        }
    })
}
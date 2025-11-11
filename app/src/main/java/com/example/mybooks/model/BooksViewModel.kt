package com.example.mybooks.model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mybooks.network.getRequest
import com.example.mybooks.network.postRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

@Serializable
data class BookItem(
    val title: String,
    val date: String
)
@Serializable
data class WriterItem(
    val writer: String,
    val id: Int,
    val books: ArrayList<BookItem>
)

@Serializable
data class BookDate(
    var date: String
)

@Serializable
data class BookDb(
    var db: String
)

@Serializable
data class WriterSimple(
    val writer: String,
    val id: Int
)

class BooksViewModel : ViewModel() {

    val dbType = listOf("proddb", "testdb")
    var chooseId: Int? = null
    val writerArray = ArrayList<WriterItem>()
    val bookDate: BookDate = BookDate("")
    val bookDb: BookDb = BookDb("proddb")

    init {
        getBooks()
    }

    fun deleteData() {
        writerArray.clear()
    }

    fun addWriter(writerItem: WriterItem) {
        writerArray.add(writerItem)
    }

    fun setBookDate(bDate: String) {
          bookDate.date = bDate
    }

    fun getBookDate(): String {
        return bookDate.date
    }
    fun setBookDb(bD: String) {
        bookDb.db = bD
    }

    fun getBookDb(): String {
        return bookDb.db
    }

    fun createWriter(writerObj: JsonObject): WriterItem {
        val writerItem: WriterItem = Json.decodeFromString(writerObj.toString())
        return writerItem
    }

    fun getWriters(): ArrayList<WriterSimple > {
        val tempArray: ArrayList<WriterSimple> = arrayListOf()
        val sortArr = writerArray.sortedWith(compareBy { it.writer })
        for(w in sortArr) {
            val tempW  = WriterSimple(w.writer, w.id)
            tempArray.add(tempW)
        }
        return tempArray
    }

    fun addChooseId(id: Int) {
        chooseId = id
    }

    fun getWriterBooks(): ArrayList<BookItem>? {
        for(wr in writerArray) {
            if(wr.id == chooseId) {
                return wr.books
            }
        }
        return null
    }

    fun getDbTypes(): List<String> {
        return dbType
    }

    fun getBooks() {
        val db = getBookDb()

        val thread = CoroutineScope(Dispatchers.IO).launch{
            getRequest("https://granlof.hopto.org/books/writers/books",
                this@BooksViewModel, db)
        }
        runBlocking {
            thread.join()
            delay(1000)
            Log.v("MyBooks", writerArray.size.toString())
        }
    }

    fun postBooks(name: String, title: String, date: String) {
        val year = ""
        val db = getBookDb()
        val thread = CoroutineScope(Dispatchers.IO).launch{
            postRequest("https://granlof.hopto.org/books/book", name, title, year, date, db)
        }
        runBlocking {
            thread.join()
            delay(1000)
        }
    }
}
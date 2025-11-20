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
import kotlin.collections.plus

private var year: String = ""

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

data class ChosenWriter(
    var writer: String,
)

class BooksViewModel : ViewModel() {

    var baseUrl: String = "https://granlof.hopto.org/books"
    val dbType = listOf("proddb", "testdb")
    var chooseId: Int? = null
    val writerArray = ArrayList<WriterItem>()
    val bookDate: BookDate = BookDate("")
    val bookDb: BookDb = BookDb("proddb")

    val chosenWriter: ChosenWriter = ChosenWriter("")

    init {
        getBooks()
    }

    fun setChosenWriter(writer: String) {
        chosenWriter.writer = writer
    }

    fun getChosenWriter(): String {
        return chosenWriter.writer
    }

    fun setChosenYear(y: String) {
        year = y
    }

    fun getChosenYear(): String {
        return year
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

    fun getBooksWriter(year: String): MutableList<MutableList<String>> {
        val bookTotList: MutableList<MutableList<String>> = mutableListOf<MutableList<String>>()

        for(wr in writerArray) {
            for(b in wr.books) {
                val y = "20" + b.date.take(2)

                if (b.date == year) {
                    bookTotList.add(mutableListOf(b.title, wr.writer, year))
                } else if (y == year){
                    bookTotList.add(mutableListOf(b.title, wr.writer, b.date))
                }
            }
        }

        return bookTotList.sortedWith(compareBy { it[2] }).toMutableList()
    }

    fun getUrl(): String {
        return baseUrl
    }

//    fun setUrl(url: String) {
//        baseUrl = url
//    }

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
                return wr.books.sortedWith(compareBy { it.date }).
                toCollection(ArrayList())
            }
        }
        return null
    }

    fun getDbTypes(): List<String> {
        return dbType
    }

    fun getYears(): List<String> {
        var year: List<String> = emptyList()
        for(wr in writerArray) {
            for(b in wr.books) {
                if(b.date == "2023" || b.date == "2024") {
                    year += b.date
                }
                else {
                    year += "20" + b.date.substring(0,2)
                }
            }
        }
        return year.distinct().sorted()
    }

    fun getBooks() {
        val db = getBookDb()
        val url = getUrl()
        Log.v("MyBooks url", url)

        val thread = CoroutineScope(Dispatchers.IO).launch{
            getRequest(
                "$url/writers/books",
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
        val url = getUrl()

        val thread = CoroutineScope(Dispatchers.IO).launch{
            postRequest("$url/book", name, title, year, date, db)
        }
        runBlocking {
            thread.join()
            delay(1000)
        }
    }
}


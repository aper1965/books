package com.example.mybooks.model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mybooks.network.getRequest
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

data class WriterSimple(
    val writer: String,
    val id: Int
)

class BooksViewModel : ViewModel() {

    var choosen_id: Int = 0
    val writerArray = ArrayList<WriterItem>()

    init {
        getBooks()
    }

    fun addWriter(writerItem: WriterItem) {
        writerArray.add(writerItem)
    }

    fun createBook(title: String, date: String, id: Int): BookItem {
        val book = BookItem(title = title, date = date)
        return book
    }

    fun createWriter(writerObj: JsonObject): WriterItem {
        val writerItem: WriterItem = Json.decodeFromString(writerObj.toString())
        return writerItem
    }
    /*
    fun createWriter(writer: String, id: Int, books: ArrayList<BookItem>): WriterItem {
        val writer = WriterItem(writer = writer, id = id, books = books)
        return writer
    }
*/

    fun getWriters(): ArrayList<WriterSimple > {
        val tempArray: ArrayList<WriterSimple> = arrayListOf()
        val sortArr = writerArray.sortedWith(compareBy({it.writer}))
        for(w in sortArr) {
            val tempW  = WriterSimple(w.writer, w.id)
            tempArray.add(tempW)
        }
        return tempArray
    }

    fun setChoosenId(id: Int) {
        choosen_id = id
    }

    fun getWriterBooks(): ArrayList<BookItem>? {
        for(wr in writerArray) {
            if(wr.id == choosen_id) {
                return wr.books
            }
        }
        return null
    }

    fun getSize(): Int {
        return writerArray.size
    }

    fun getBooks() {
        val thread = CoroutineScope(Dispatchers.IO).launch{
            getRequest("https://granlof.hopto.org/books/writers/books", this@BooksViewModel)
        }
        runBlocking {
            thread.join()
            delay(1000)
            Log.v("MyBooks", writerArray.size.toString())
        }
    }

}
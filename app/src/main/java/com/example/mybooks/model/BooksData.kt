package com.example.mybooks.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.json.JSONObject

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

class BooksData {

    val writerArray = ArrayList<WriterItem>()

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

    fun getWriterBooks(writer: String): ArrayList<BookItem>? {
        for(wr in writerArray) {
            if(wr.writer == writer) {
                return wr.books
            }
        }
        return null
    }

    fun getSize(): Int {
        return writerArray.size
    }
}
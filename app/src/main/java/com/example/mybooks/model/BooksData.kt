package com.example.mybooks.model

data class BooksItem(
    val id: Int,
    val title: String,
    val date: String
)

data class WriterItem(
    val writer: String,
    val id: Int
)

class BooksData {

    val writerArray = ArrayList<WriterItem>()
    val bookArray = ArrayList<BooksItem>()

    fun addWriter(writerItem: WriterItem) {
        writerArray.add(writerItem)
    }

    fun getAllWriter(): ArrayList<WriterItem> {
        return writerArray
    }

    fun addBook(booksItem: BooksItem) {
        bookArray.add(booksItem)
    }

    fun getWriterBook(index: Int): ArrayList<BooksItem> {
        val ba = ArrayList<BooksItem>()

        val id = writerArray[index].id

        for(i in bookArray)
            if ( id == i.id ) {
                ba.add(i)
            }

        return ba
    }
}
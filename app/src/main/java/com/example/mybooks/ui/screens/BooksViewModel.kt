package com.example.mybooks.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybooks.network.BooksApi

class BooksViewModel : ViewModel() {

    init {
        getBooks()
    }
}

fun getBooks() {
    val list = BooksApi.retrofitService.getBooks()
    println(list)
}
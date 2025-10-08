package com.example.mybooks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mybooks.ui.screens.BooksViewModel
import com.example.mybooks.ui.theme.MyBooksTheme
import com.example.mybooks.model.BooksData
import com.example.mybooks.network.BooksApi

class MainActivity : ComponentActivity() {
    init {
        val bd = BooksData()
        getBooks(bd)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyBooksTheme {
                Surface (modifier = Modifier.fillMaxSize()) {
                    BookList(
                        name = "Android",
                    )
                }
            }
        }
    }
}

fun getBooks(bd: BooksData) {
    val list = BooksApi.retrofitService.getBooks()
    println(list)
}
@Composable
fun BookList(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hejsan")
}

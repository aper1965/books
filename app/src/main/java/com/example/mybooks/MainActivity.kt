package com.example.mybooks

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mybooks.model.BooksData
import com.example.mybooks.network.getRequest
import com.example.mybooks.ui.theme.MyBooksTheme

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
    val thread = Thread {
        getRequest("https://granlof.hopto.org/books/writers/books", bd)
    }
    thread.start()

    thread.join()
    Log.v("MyBooks", bd.getSize().toString())
}
@Composable
fun BookList(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hejsan")
}

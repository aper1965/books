package com.example.mybooks

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mybooks.model.BooksData
import com.example.mybooks.network.getRequest
import com.example.mybooks.ui.theme.MyBooksTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    val bd = BooksData()

    init {
        getBooks(bd)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyBooksTheme {
                    BookList(name = "Android", bd)
            }
        }
    }
}

fun getBooks(bd: BooksData) {
    val thread = CoroutineScope(Dispatchers.IO).launch{
        getRequest("https://granlof.hopto.org/books/writers/books", bd)
    }
    runBlocking {
        thread.join()
        delay(500)
        Log.v("MyBooks", bd.getSize().toString())
    }
}
@Composable
fun BookList(name: String, bd: BooksData) {
    val writer = bd.getWriters()
    Column {
        Text(text = "Writers")
        LazyColumn {
//            stickyHeader {
//                Text(text = "Writers")
//           }
            item {
                Box(modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                )
            }

            items(
                writer,
                key = { item -> item.id })
            { item ->
                Text(item.writer)
            }
            item {
                Box(modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                )
            }
        }
    }
}


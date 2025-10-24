package com.example.mybooks

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fitInside
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.WindowInsetsRulers
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.mybooks.model.BookItem
import com.example.mybooks.model.BooksViewModel
import com.example.mybooks.ui.theme.MyBooksTheme

class BookActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val myIntent = intent
        val books = myIntent.getStringArrayListExtra("vm")
        setContent {
            MyBooksTheme {
                BooksList()
            }
        }

    }
}
@Composable
fun BooksList() {
    //fun BooksList(books: ArrayList<BookItem>) {
    val modifier = Modifier.padding(horizontal = 10.dp)
    val activity = LocalActivity.current as Activity

    Column(modifier = Modifier
        .fitInside(WindowInsetsRulers.SafeDrawing.current)
    ) {
        Row {
            Text(text = "Books", modifier = modifier)
            Text(text = "Date", Modifier.padding(horizontal = 120.dp))
        }
        HorizontalDivider(thickness = 4.dp)
        LazyColumn {
//            items(count = (books?.size ?: Int) as Int, key = null)
            items(count = 2, key = null)
            { item ->
                Row {
//                    Text(text = books?.get(item)?.title ?: String(), modifier = modifier)
//                    Text(text = books?.get(item)?.date ?: String(), Modifier.padding(horizontal = 120.dp))
                    Text(text = "Hejsan", modifier = modifier)
                    Text(text = "Provar", Modifier.padding(horizontal = 120.dp))
                }

                }
        }
        Row {
            Button(
                onClick = {
                    val intent: Intent = Intent(activity, MainActivity::class.java)
                    activity.startActivity(intent)
                }) {
                Text(text = "Back")
            }
        }

    }
}

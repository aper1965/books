package com.example.mybooks.ui.screens

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.LocalActivity
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
import androidx.navigation.NavController
import com.example.mybooks.MainActivity
import com.example.mybooks.model.BookItem
import com.example.mybooks.model.BooksViewModel
import kotlin.collections.ArrayList

@Composable
fun BookList(navController: NavController, vm: BooksViewModel) {
    val modifier = Modifier.padding(horizontal = 10.dp)
    val activity = LocalActivity.current as Activity
    val books = vm.getWriterBooks()

    Column(
        modifier = Modifier
            .fitInside(WindowInsetsRulers.SafeDrawing.current)
    ) {
        Row {
            Text(text = "Books", modifier = modifier)
            Text(text = "Date", Modifier.padding(horizontal = 120.dp))
        }
        HorizontalDivider(thickness = 4.dp)
        LazyColumn {
            items(count = (books?.size ?: Int) as Int, key = null)
            { item ->
                Row {
                    if (books != null) {
                        Text(text = books?.get(item)?.title ?: String(), modifier = modifier)
                        Text(
                            text = books?.get(item)?.date ?: String(),
                            Modifier.padding(horizontal = 120.dp)
                        )
                    }
                }
            }

        }
        Row {
            Button(
                onClick = {
                    navController.navigate(route = "writers")
                }) {
                Text(text = "Back")
            }
        }

    }
}


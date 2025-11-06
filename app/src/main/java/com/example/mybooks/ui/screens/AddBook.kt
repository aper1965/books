package com.example.mybooks.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fitInside
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.WindowInsetsRulers
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mybooks.model.BooksViewModel

@Composable
fun AddBook(navController: NavController, vm: BooksViewModel) {
    val writers = vm.getWriters()
    val modifier = Modifier.padding(horizontal = 10.dp)
    val mContext = LocalContext.current
    val textDate = remember { mutableStateOf("") }
    val textTitle = remember { mutableStateOf("") }
    val textWriter = remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fitInside(WindowInsetsRulers.SafeDrawing.current)
    ) {
        Row {
            Text(text = "Add a book", fontSize = 30.sp, modifier = modifier)
        }
        HorizontalDivider(thickness = 4.dp)
        Row {
            Button(
                onClick = {
                    navController.navigate(route = "writers")
                }) {
                Text(text = "Cancel")
            }
            Button(
                onClick = {
                    textWriter.value = ""
                    textTitle.value = ""
                    textDate.value = ""
                }) {
                Text(text = "Clear")
            }
            Button(
                onClick = {
                    if (textDate.value.isEmpty() or textTitle.value.isEmpty() or
                        textWriter.value.isEmpty()
                    ) {
                        Toast.makeText(mContext, "Info is missing", Toast.LENGTH_LONG)
                            .show()
                    }
                    else {
                        vm.postBooks(textWriter.value, textTitle.value, textDate.value)
                        vm.deleteData()
                        vm.getBooks()
                        navController.navigate(route = "writers")
                    }
                }) {
                Text(text = "Save")
            }
        }
        HorizontalDivider(thickness = 4.dp)
        Row {
            TextField(
                value = textTitle.value,
                onValueChange = { textTitle.value = it },
                label = { Text("Add title") },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Row {
            TextField(
                value = textDate.value,
                onValueChange = { textDate.value = it },
                label = { Text("Add date") },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Row {
            TextField(
                value = textWriter.value,
                onValueChange = {
                    textWriter.value = it },
                label = { Text("Pick a writer or add new one") },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        HorizontalDivider(thickness = 4.dp)
        Row {
        LazyColumn {
            items(
                writers,
                key = { item -> item.id })
            { item ->
                Text(item.writer, modifier = modifier.clickable {
                    textWriter.value = item.writer
                })
            }
        }
        }
    }
}


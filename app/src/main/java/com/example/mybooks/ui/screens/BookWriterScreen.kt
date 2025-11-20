package com.example.mybooks.ui.screens

import android.util.Log
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mybooks.model.BooksViewModel

@Composable
fun BookWriterList(navController: NavController, vm: BooksViewModel) {
    val modifier = Modifier.padding(horizontal = 10.dp)
    val year = vm.getChosenYear()
    val booksList = vm.getBooksWriter(year)
    Log.v("MyBooks list", year)
    Log.v("MyBooks list", booksList.toString())

    Column(
        modifier = Modifier
            .fitInside(WindowInsetsRulers.SafeDrawing.current)) {
        Row {
            Text(text = "Books red $year", modifier = modifier, fontSize = 30.sp)
        }
        HorizontalDivider(thickness = 4.dp)
        Row {
            Text(text = year, modifier = modifier, fontSize = 20.sp)
        }
        HorizontalDivider(thickness = 4.dp)
        Row {
            Button(
                onClick = {
                    vm.setChosenYear("")
                    navController.navigateUp()
                }) {
                Text(text = "Back")
            }
        }
        HorizontalDivider(thickness = 4.dp)
        LazyColumn {
            items(booksList.size, key = null)
            { item ->
                Text(text = booksList[item][0], modifier = modifier)
                Text(
                    text = booksList[item][1],
                    Modifier.padding(horizontal = 50.dp)
                )
                Text(
                    text = booksList[item][2],
                    Modifier.padding(horizontal = 50.dp)
                )
            }
        }
    }
}




package com.example.mybooks.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fitInside
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.WindowInsetsRulers
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mybooks.model.BooksViewModel

@Composable
fun WriterList(navController: NavController, vm: BooksViewModel) {
    val writers = vm.getWriters()
    val modifier = Modifier.padding(horizontal = 10.dp)
    val bookDate = vm.getBookDate()

    Column(modifier = Modifier
        .fitInside(WindowInsetsRulers.SafeDrawing.current)
    ) {
        Row {
            Text(text = "Writers", modifier = modifier)
            Text(text = "Date $bookDate", Modifier.padding(horizontal = 40.dp))
            Button(
                onClick = {
                    navController.navigate(route = "add")
                }) {
                Text(text = "Add book")
            }

        }
        HorizontalDivider(thickness = 4.dp)
        LazyColumn {
            items(
                writers,
                key = { item -> item.id })
            { item ->
                Text(item.writer, modifier = modifier.
                    clickable {vm.setChoosenId(item.id)
                    navController.navigate(route = "books") })
            }
        }
    }
}

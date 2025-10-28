package com.example.mybooks.ui.screens

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fitInside
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.WindowInsetsRulers
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mybooks.model.BooksViewModel

@Composable
fun WriterList(navController: NavController, vm: BooksViewModel) {
    val writers = vm.getWriters()
    val modifier = Modifier.padding(horizontal = 10.dp)
    val navController = rememberNavController()
    val activity = LocalActivity.current as Activity

    Column(modifier = Modifier
        .fitInside(WindowInsetsRulers.SafeDrawing.current)
    ) {
        Row {
            Text(text = "Writers", modifier = modifier)
        }
        HorizontalDivider(thickness = 4.dp)
        LazyColumn {
            items(
                writers,
                key = { item -> item.id })
            { item ->
                Text(item.writer, modifier = modifier.
                clickable {navController.navigate(route = "books")
                })
            }
        }
    }
}

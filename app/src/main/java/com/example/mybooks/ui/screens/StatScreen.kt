package com.example.mybooks.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fitInside
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.WindowInsetsRulers
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mybooks.model.BooksViewModel

@Composable
fun StatBook(navController: NavController, vm: BooksViewModel) {
    val modifier = Modifier.padding(horizontal = 10.dp)
    val mContext = LocalContext.current
    val years = vm.getYears()

    Column(
        modifier = Modifier
            .fitInside(WindowInsetsRulers.SafeDrawing.current)
    ) {
        Row {
            Text(text = "Statistics", modifier = modifier, fontSize = 30.sp)
        }
        HorizontalDivider(thickness = 4.dp)
        Button(
            onClick = {
                navController.navigateUp()
            }) {
            Text(text = "Back")
        }
        HorizontalDivider(thickness = 4.dp)
        var expanded by remember { mutableStateOf(false) }
        var chosenYear by remember { mutableStateOf("") }
        Box (modifier = Modifier.padding(horizontal = 10.dp)) {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More options")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                for (year in years) {
                    DropdownMenuItem(
                        text = { Text(year) },
                        onClick = {  vm.setChosenYear(year)
                            navController.navigate(route = "bookWriter")
                        }
                    )
                }
            }
        }
        HorizontalDivider(thickness = 4.dp)
//        val books = vm.getBooksYear(chosenYear)

//        LazyColumn {
//            items(
//                books,
//                key = { item -> item.id })
//            { item ->
//                Text(item.books, modifier = modifier.
//                clickable {vm.addChooseId(item.id)
//                    vm.setChoosenWriter(item.writer)
//                    navController.navigate(route = "books") })
//            }
//        }

    }
}
package com.example.mybooks.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fitInside
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mybooks.model.BooksViewModel

@Composable
fun WriterList(navController: NavController, vm: BooksViewModel) {
    val writers = vm.getWriters()
    val modifier = Modifier.padding(horizontal = 10.dp)
    val bookDate = vm.getBookDate()
    val bookDb = vm.getBookDb()

    Column(modifier = Modifier
        .fitInside(WindowInsetsRulers.SafeDrawing.current)
    ) {
        Row {
            Text(text = "Writers", modifier = modifier, fontSize = 30.sp)
        }
        HorizontalDivider(thickness = 4.dp)
        Row {
            Text(text = "Date $bookDate", Modifier.padding(horizontal = 22.dp, vertical = 10.dp),
                fontSize = 20.sp)
            Text(text = bookDb, Modifier.padding(vertical = 10.dp),fontSize = 20.sp)
            Spacer(Modifier.weight(1f))

            var expanded by remember { mutableStateOf(false) }

            Box (modifier = Modifier.padding(horizontal = 10.dp)) {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More options")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Add book") },
                        onClick = {  navController.navigate(route = "add") }
                    )
                    DropdownMenuItem(
                        text = { Text("Update") },
                        onClick = {
                            expanded = false
                            vm.deleteData()
                            vm.getBooks()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Settings") },
                        onClick = {  navController.navigate(route = "settings") }
                    )
                }
            }
        }
        HorizontalDivider(thickness = 4.dp)
        LazyColumn {
            items(
                writers,
                key = { item -> item.id })
            { item ->
                Text(item.writer, modifier = modifier.
                    clickable {vm.addChooseId(item.id)
                        vm.setChoosenWriter(item.writer)
                    navController.navigate(route = "books") })
            }
        }
    }
}

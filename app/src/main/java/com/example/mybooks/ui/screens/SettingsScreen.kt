package com.example.mybooks.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fitInside
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.WindowInsetsRulers
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.mybooks.model.BooksViewModel


@Composable
fun ChangeSettings(navController: NavController, vm: BooksViewModel) {
    val modifier = Modifier.padding(horizontal = 10.dp)
    val startDb = vm.getBookDb()
    var mSelectedText by remember { mutableStateOf(startDb) }
    var newUrl = remember { mutableStateOf("") }
    val url = vm.baseUrl


    Column(
        modifier = Modifier
            .fitInside(WindowInsetsRulers.SafeDrawing.current)
    ) {
        Row {
            Text(text = "Settings", modifier = modifier, fontSize = 30.sp)
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
                    Log.v("MyBooks newUrl", newUrl.value)

                    if (startDb != mSelectedText) {
                        vm.setBookDb(mSelectedText)
                        vm.deleteData()
                        vm.getBooks()
                    }
//                    if (newUrl.value != url) {
//                        vm.setUrl(newUrl.value)
//                    }
                    vm.getWriters()
                    navController.navigate(route = "writers")
                }
            ) {
                Text(text = "Save")
            }
        }
        HorizontalDivider(thickness = 4.dp)

//        Log.v("MyBooks startDb", startDb)
        Box() {
            var mExpanded by remember { mutableStateOf(false) }
            var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
            val dbTypes = vm.getDbTypes()
            val icon = if (mExpanded)
                Icons.Filled.KeyboardArrowUp
            else
                Icons.Filled.KeyboardArrowDown

            OutlinedTextField(
                value = mSelectedText,
                onValueChange = { mSelectedText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        // This value is used to assign to
                        // the DropDown the same width
                        mTextFieldSize = coordinates.size.toSize()
                    },
                label = { Text("Label") },
                trailingIcon = {
                    Icon(
                        icon, "contentDescription",
                        Modifier.clickable { mExpanded = !mExpanded })
                }
            )

            DropdownMenu(
                expanded = mExpanded,
                onDismissRequest = { mExpanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
            ) {
                dbTypes.forEach { label ->
                    DropdownMenuItem(
                        text = { Text(text = label) },
                        onClick = {
                            mSelectedText = label
                            mExpanded = false
                        }
                    )
                }
            }
        }
        HorizontalDivider(thickness = 4.dp)

        Row {
                TextField(
                    value = newUrl.value,
                    onValueChange = {
                        newUrl.value = it
                    },
                    label = { Text("Change the url") },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }

    }





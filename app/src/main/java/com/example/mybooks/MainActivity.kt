package com.example.mybooks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mybooks.model.BooksViewModel
import com.example.mybooks.ui.screens.AddBook
import com.example.mybooks.ui.screens.BookList
import com.example.mybooks.ui.screens.ChangeSettings
import com.example.mybooks.ui.screens.WriterList
import com.example.mybooks.ui.theme.MyBooksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyBooksTheme {
                val vm = ViewModelProvider(this)[BooksViewModel::class.java]
                StartBook(vm)
            }
        }
    }
}

@Composable
fun StartBook(vm: BooksViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "writers") {
            composable("writers") {
            WriterList(navController, vm)
        }
        composable("books") {
            BookList(navController, vm)
        }
        composable("add") {
            AddBook(navController, vm)
        }
        composable("setting") {
            ChangeSettings(navController, vm)
        }
    }
}
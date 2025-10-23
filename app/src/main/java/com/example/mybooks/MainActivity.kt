package com.example.mybooks

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fitInside
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.WindowInsetsRulers
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mybooks.model.BooksViewModel
import com.example.mybooks.model.WriterSimple
import com.example.mybooks.network.getRequest
import com.example.mybooks.ui.theme.MyBooksTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyBooksTheme {
                val vm = ViewModelProvider(this).get(BooksViewModel::class.java)
                val writers = vm.getWriters()
                WritersList(name = "Android", writers)
//                BooksList(1, bd)

            }
        }
    }
}

@Composable
fun WritersList(name: String, writers: ArrayList<WriterSimple>) {
    val modifier = Modifier.padding(horizontal = 10.dp)
    val navController = rememberNavController()

//    NavHost(
//        navController = navController,
//    ) {
//        composable("Writers") { WritersList(name, bd) }
//        composable("Books") { BooksList(id, bd, navController) }
//    }
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
                        selectable(true, onClick = { Click(item.id, vm)}))
            }
        }
    }
}
@Composable
public fun Click(id: Int, vm: BooksViewModel) {
    vm.setChoosenId(id)
    Log.v("MyBooks ID", bd.getWriterBooks(id).toString())
}

//@Composable
//fun BooksList(id: Int, bd: BooksData, navController: NavController) {
//    val books = bd.getWriterBooks(id)
//    val modifier = Modifier.padding(horizontal = 10.dp)
//    Column(modifier = Modifier
//        .fitInside(WindowInsetsRulers.SafeDrawing.current)
//    ) {
//        Row {
//            Text(text = "Books", modifier = modifier)
//            Text(text = "Date", Modifier.padding(horizontal = 120.dp))
//        }
//        HorizontalDivider(thickness = 4.dp)
//        LazyColumn {
//            items(count = (books?.size ?: Int) as Int, key = null)
//            { item ->
//                Row {
//                    Text(text = books?.get(item)?.title ?: String(), modifier = modifier)
//                    Text(text = books?.get(item)?.date ?: String(), Modifier.padding(horizontal = 120.dp))
//                }
//            }
//        }
//    }
//}

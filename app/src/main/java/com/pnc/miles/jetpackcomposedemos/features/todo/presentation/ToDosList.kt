package com.pnc.miles.jetpackcomposedemos.features.todo.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pnc.miles.jetpackcomposedemos.features.todo.domain.ToDo

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ToDosList(
    toDos: List<ToDo>,
    onAdd: (String) -> Unit
) {
//    // Doesn't remember that counter was incremented:
//    var counter = 0
//    counter++
//    // While saying the variable is provided by a mutableState does remember
    var showDrawer by remember {
        mutableStateOf(false)
    }
    var newText by remember {
        mutableStateOf("")
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDrawer = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add a new to-do item"
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(
                items = toDos,
                key = { toDo: ToDo -> toDo.id }
            ) { toDo: ToDo ->
                Text(toDo.title)
            }
        }

        if (showDrawer) {
            ModalBottomSheet(
                onDismissRequest = {
                    showDrawer = false
                }
            ) {
                TextField(
                    value = newText,
                    onValueChange = { newText = it },
                    label = { Text("New to-do") }
                )
                Button(
                    onClick = {
                        onAdd(newText)
                        showDrawer = false
                        newText = ""
                    }
                ) {
                    Text("Add")
                }
            }
        }
    }
}
package com.pnc.miles.jetpackcomposedemos.features.todo.presentation

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pnc.miles.jetpackcomposedemos.core.LoadableState

@Composable
fun ToDosScreen(
    useTwoPaneLayout: Boolean,
    viewModel: ToDosViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // LaunchedEffect renders what's in the body during initial composition
    // It monitors the parameter value for when to recompose
    // Then it renders / rerenders what's in the body
    LaunchedEffect(Unit) {
        viewModel.loadToDos()
    }

    when (uiState) {
        is LoadableState.Loading -> CircularProgressIndicator()
        is LoadableState.Empty -> Text("No to-dos")
        is LoadableState.Error -> {
            val errorMessage = (uiState as LoadableState.Error).message
            Text(
                text = "Error: $errorMessage",
                color = MaterialTheme.colorScheme.error
            )
        }
        is LoadableState.Success -> {
            val toDos = (uiState as LoadableState.Success).data
            ToDosList(
                toDos = toDos,
                useTwoPaneLayout = useTwoPaneLayout,
                onAdd = { item: String ->
                    viewModel.createToDo(item)
                },
                onToggleCompleted = {
                    viewModel.toggleCompleted(it)
                },
                onDelete = viewModel::deleteToDo,
                onUndoDelete = viewModel::restoreToDo,
                onReorder = viewModel::reorder
            )
        }
    }
}
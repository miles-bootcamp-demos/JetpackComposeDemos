package com.pnc.miles.jetpackcomposedemos.core.presentation

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun TextFilter(
    label: String,
    filter: String,
    onFilterChange: (String) -> Unit
) {

    TextField(
        value = filter,
        onValueChange = onFilterChange,
        label = {
            Text("$label:")
        }
    )

}

package com.ab.an.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorDialog(message: String, onClose: () -> Unit = {}) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Error") },
        text = { Text(message) },
        confirmButton = {
            Button(onClick = onClose) {
                Text("OK")
            }
        }
    )
}
package com.ab.an.presentation.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.ab.an.presentation.components.ErrorIcon
import com.ab.an.presentation.components.PrimaryOutlinedTextField

@Composable
fun RegisterScreen(authState: AuthState, authViewModel: AuthViewModel) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PrimaryOutlinedTextField(
            value = authState.user.fullName,
            onValueChange = { authViewModel.onIntent(AuthIntent.UsernameChanged(it)) },
            label = "Username",
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Username Icon") },
            trailingIcon = {
                if (authState.usernameErrorMessage.isNotEmpty()) {
                    ErrorIcon()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !authState.isLoading,
            errorMessage = authState.usernameErrorMessage
        )
        Spacer(modifier = Modifier.height(8.dp))
        PrimaryOutlinedTextField(
            value = authState.user.email,
            onValueChange = { authViewModel.onIntent(AuthIntent.EmailChanged(it)) },
            label = "Email",
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email Icon") },
            trailingIcon = {
                if (authState.emailErrorMessage.isNotEmpty()) {
                    ErrorIcon()
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            enabled = !authState.isLoading,
            errorMessage = authState.emailErrorMessage
        )
        Spacer(modifier = Modifier.height(8.dp))
        PrimaryOutlinedTextField(
            value = authState.user.password,
            onValueChange = {
                authViewModel.onIntent(AuthIntent.PasswordChanged(it))
            },
            label = "Password",
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password Icon") },
            trailingIcon = {
                if (authState.passwordErrorMessage.isNotEmpty()) {
                    ErrorIcon()
                } else {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    val description = if (passwordVisible) "Hide password" else "Show password"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            enabled = !authState.isLoading,
            errorMessage = authState.passwordErrorMessage
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}
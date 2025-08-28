package com.ab.an.presentation.changePassword

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ab.an.presentation.components.ErrorDialog
import com.ab.an.presentation.components.LoadingIndicatorScreen
import com.ab.an.presentation.components.PrimaryButton
import com.ab.an.presentation.components.PrimaryOutlinedTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    changePasswordViewModel: ChangePasswordViewModel = hiltViewModel(),
    navBack: () -> Unit
) {
    val context = LocalContext.current
    val state by changePasswordViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.success) {
        if(state.success) {
            Toast.makeText(context, "Password changed successfully", Toast.LENGTH_SHORT).show()
            navBack()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Change Password")
                },
                navigationIcon = {
                    IconButton(
                        onClick = navBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(16.dp)
                ) {
                    PrimaryOutlinedTextField(
                        value = state.currentPassword,
                        onValueChange = {
                            changePasswordViewModel.onIntent(
                                ChangePasswordIntent.CurrentPasswordChanged(
                                    it
                                )
                            )
                        },
                        label = "Current Password",
                        modifier = Modifier.fillMaxWidth(),
                        errorMessage = state.currentPasswordError,
                        enabled = !state.isLoading
                    )
                    PrimaryOutlinedTextField(
                        value = state.newPassword,
                        onValueChange = {
                            changePasswordViewModel.onIntent(
                                ChangePasswordIntent.NewPasswordChanged(
                                    it
                                )
                            )
                        },
                        label = "New Password",
                        modifier = Modifier.fillMaxWidth(),
                        errorMessage = state.newPasswordError,
                        enabled = !state.isLoading
                    )
                    PrimaryOutlinedTextField(
                        value = state.confirmNewPassword,
                        onValueChange = {
                            changePasswordViewModel.onIntent(
                                ChangePasswordIntent.ConfirmNewPasswordChanged(
                                    it
                                )
                            )
                        },
                        label = "Confirm New Password",
                        modifier = Modifier.fillMaxWidth(),
                        errorMessage = state.confirmNewPasswordError,
                        enabled = !state.isLoading
                    )
                }
                PrimaryButton(
                    onClick = {
                        changePasswordViewModel.onIntent(ChangePasswordIntent.Submit)
                    },
                    label = "Submit",
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    enabled = !state.isLoading
                )
            }
            if (!state.errorMessage.isNullOrBlank()) {
                state.errorMessage?.let { ErrorDialog(it, onClose = {
                    changePasswordViewModel.onIntent(ChangePasswordIntent.OnDismiss)
                }) }
            }
            if (state.isLoading) {
                LoadingIndicatorScreen()
            }
        }
    }
}
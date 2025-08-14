package com.ab.an.presentation.addOrEditPassword

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ab.an.core.R
import com.ab.an.presentation.components.DynamicSelectTextField
import com.ab.an.presentation.components.PrimaryButton
import com.ab.an.presentation.components.PrimaryOutlinedTextField

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddOrEditPasswordScreen(
    isEditMode: Boolean,
    navToHome: () -> Unit,
    addOrEditPasswordViewModel: AddOrEditPasswordViewModel = hiltViewModel(),
    id: String? = null
) {
    val state by addOrEditPasswordViewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        if (!id.isNullOrBlank()) {
            addOrEditPasswordViewModel.onIntent(AddOrEditPasswordIntent.FetchPassword(id))
        }
    }
    LaunchedEffect(state.success) {
        if (state.success) {
            val msg = if(isEditMode) "Password updated successfully" else "Password added successfully"
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            navToHome()
        }
    }
    LaunchedEffect(state.errorMessage) {
        if (!state.errorMessage.isNullOrBlank()) {
            Toast.makeText(context, state.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        containerColor = MaterialTheme.colorScheme.secondary,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = if (isEditMode) "Edit record" else "New record")
                },
                navigationIcon = {
                    IconButton(
                        onClick = navToHome
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.outline_arrow_back_24),
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
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
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    PrimaryOutlinedTextField(
                        value = state.passwordEntity.name,
                        onValueChange = {
                            addOrEditPasswordViewModel.onIntent(
                                AddOrEditPasswordIntent.NameChanged(
                                    it
                                )
                            )
                        },
                        label = "Name",
                        modifier = Modifier.fillMaxWidth(),
                        errorMessage = state.nameError,
                        enabled = !state.isLoading
                    )
                    PrimaryOutlinedTextField(
                        value = state.passwordEntity.username,
                        onValueChange = {
                            addOrEditPasswordViewModel.onIntent(
                                AddOrEditPasswordIntent.UserNameChanged(
                                    it
                                )
                            )
                        },
                        label = "User Name",
                        modifier = Modifier.fillMaxWidth(),
                        errorMessage = state.userNameError,
                        enabled = !state.isLoading
                    )
                    PrimaryOutlinedTextField(
                        value = state.passwordEntity.link,
                        onValueChange = {
                            addOrEditPasswordViewModel.onIntent(
                                AddOrEditPasswordIntent.LinkChanged(
                                    it
                                )
                            )
                        },
                        label = "Link",
                        modifier = Modifier.fillMaxWidth(),
                        errorMessage = state.linkError,
                        enabled = !state.isLoading
                    )
                    PrimaryOutlinedTextField(
                        value = state.passwordEntity.password,
                        onValueChange = {
                            addOrEditPasswordViewModel.onIntent(
                                AddOrEditPasswordIntent.PasswordChanged(
                                    it
                                )
                            )
                        },
                        label = "Password",
                        modifier = Modifier.fillMaxWidth(),
                        errorMessage = state.passwordError,
                        enabled = !state.isLoading
                    )

                    DynamicSelectTextField(
                        selectedValue = state.passwordEntity.tag,
                        options = tags,
                        label = "Tag",
                        onValueChangeEvent = {
                            addOrEditPasswordViewModel.onIntent(
                                AddOrEditPasswordIntent.TagChanged(
                                    it
                                )
                            )
                        },
                        enabled = !state.isLoading
                    )
                }

                PrimaryButton(
                    onClick = {
                        addOrEditPasswordViewModel.onIntent(
                            AddOrEditPasswordIntent.Submit(
                                isEditMode
                            )
                        )
                    },
                    label = if (isEditMode) "Edit" else "Add",
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    enabled = !state.isLoading
                )

            }
            if (!state.fetchPasswordError.isNullOrBlank()) {
                AlertDialog(
                    onDismissRequest = {

                    },
                    text = {
                        state.fetchPasswordError?.let { Text(text = it) }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = navToHome
                        ) {
                            Text(text = "Close")
                        }
                    }
                )
            }
            if (state.isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            MaterialTheme.colorScheme.secondary.copy(
                                alpha = 0.5f
                            )
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }

}
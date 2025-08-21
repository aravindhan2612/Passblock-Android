package com.ab.an.presentation.updateContactInfo

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ab.an.presentation.components.DatePickerFieldToModal
import com.ab.an.presentation.components.LoadingIndicatorScreen
import com.ab.an.presentation.components.PrimaryButton
import com.ab.an.presentation.components.PrimaryOutlinedTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateContactInfoScreen(
    navBack: () -> Unit,
    navToAddOrEditProfilePicture: () -> Unit,
    viewModel: UpdateContactInfoViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(state.success) {
        if (state.success) {
            Toast.makeText(context, "Contact info updated successfully", Toast.LENGTH_SHORT).show()
            navBack()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.secondary
    ) {
        Scaffold(
            modifier = Modifier.windowInsetsPadding(
                WindowInsets.safeDrawing
            ),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "Edit Contact Info")
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

                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .background(
                            color = MaterialTheme.colorScheme.secondary
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(20.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        ProfilePictureEditor(
                            fullName = state.user.fullName,
                            profilePicture = state.user.profilePicture,
                            onClick = {
                               navToAddOrEditProfilePicture()
                            })
                        PrimaryOutlinedTextField(
                            value = state.user.fullName,
                            onValueChange = {
                                viewModel.onIntent(UpdateContactInfoIntent.FullNameChange(it))
                            },
                            label = "Full Name",
                            modifier = Modifier.fillMaxWidth(),
                            errorMessage = state.fullNameError,
                            enabled = !state.isLoading
                        )
                        PrimaryOutlinedTextField(
                            value = state.user.email,
                            onValueChange = {
                                viewModel.onIntent(UpdateContactInfoIntent.EmailChange(it))
                            },
                            label = "Email",
                            modifier = Modifier.fillMaxWidth(),
                            errorMessage = state.emailError,
                            enabled = !state.isLoading
                        )
                        PrimaryOutlinedTextField(
                            value = state.user.phoneNumber,
                            onValueChange = {
                                viewModel.onIntent(UpdateContactInfoIntent.PhoneNumberChange(it))
                            },
                            label = "Phone Number",
                            modifier = Modifier.fillMaxWidth(),
                            errorMessage = state.phoneNumberError,
                            enabled = !state.isLoading
                        )
                        DatePickerFieldToModal(
                            modifier = Modifier.fillMaxWidth(),
                            dateValue = state.user.dob,
                            onDateValueChange = {
                                viewModel.onIntent(UpdateContactInfoIntent.DateChange(it))
                            }
                        )
                    }
                    PrimaryButton(
                        onClick = {
                            viewModel.onIntent(
                                UpdateContactInfoIntent.Submit(
                                    context
                                )
                            )
                        },
                        label = "Submit",
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        enabled = !state.isLoading
                    )
                }
                if (state.isLoading) {
                    LoadingIndicatorScreen()
                }
            }
        }
    }
}
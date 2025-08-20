package com.ab.an.presentation.viewPassword

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ab.an.core.R
import com.ab.an.presentation.components.FavIconAsyncImage
import com.ab.an.presentation.components.LoadingIndicatorScreen
import com.ab.an.presentation.components.OnPrimaryText
import com.ab.an.presentation.components.PrimaryOutlinedButton
import com.ab.an.presentation.components.PrimaryText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewPasswordScreen(
    id: String,
    navToHome: () -> Unit,
    navToAddOrEditPassword: () -> Unit,
    viewPasswordViewModel: ViewPasswordViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewPasswordViewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewPasswordViewModel.onIntent(ViewPasswordIntent.FetchPassword(id))
    }
    LaunchedEffect(state.isPasswordDeleteSuccess, state.deleteError) {
        if (state.isPasswordDeleteSuccess) {
            Toast.makeText(context, "Password deleted successfully", Toast.LENGTH_SHORT).show()
            navToHome()
        } else if (!state.deleteError.isNullOrBlank()) {
            Toast.makeText(context, state.deleteError, Toast.LENGTH_SHORT).show()
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    PrimaryText(text = "Details")
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
                actions = {
                    if (state.error.isNullOrBlank()) {
                        IconButton(
                            onClick = {
                                viewPasswordViewModel.onIntent(ViewPasswordIntent.OpenDeleteDialog)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.DeleteOutline,
                                contentDescription = null,
                                tint = Color.Red
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when {
                state.isLoading -> {
                    LoadingIndicatorScreen()
                }

                !state.error.isNullOrBlank() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                MaterialTheme.colorScheme.secondary
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        state.error?.let { error ->
                            Text(
                                text = error,
                                color = MaterialTheme.colorScheme.error,
                            )
                        }
                    }

                }

                else -> {
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .background(
                                color = MaterialTheme.colorScheme.secondary
                            )
                            .padding(20.dp),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            FavIconAsyncImage(
                                model = state.password.faviconUrl,
                                iconSize = 80.dp
                            )
                            Spacer(
                                modifier = Modifier.width(20.dp)
                            )
                            Column(
                                modifier = Modifier.padding(vertical = 12.dp),
                                verticalArrangement = Arrangement.Center,
                            ) {
                                PrimaryText(
                                    text = state.password.name,
                                    fontSize = 20.sp
                                )
                                OnPrimaryText(
                                    text = state.password.username,
                                    fontSize = 16.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                        ) {
                            PrimaryText(
                                modifier = Modifier.weight(0.5f),
                                text = "Link",
                                fontSize = 16.sp
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                text = buildAnnotatedString {
                                    withLink(
                                        LinkAnnotation.Url(
                                            state.password.link,
                                            TextLinkStyles(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary))
                                        )
                                    ) {
                                        append(state.password.link)
                                    }
                                },
                                fontSize = 16.sp
                            )
                        }
                        InfoRow(
                            label = "User id",
                            value = state.password.username
                        )
                        InfoRow(
                            label = "Password",
                            value = state.password.password
                        )
                        InfoRow(
                            label = "Category",
                            value = state.password.tag
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            PrimaryOutlinedButton(
                                label = "Copy password",
                                onClick = {},
                                labelFontSize = 12.sp
                            )
                            PrimaryOutlinedButton(
                                label = "Update Details",
                                onClick = navToAddOrEditPassword,
                                labelFontSize = 12.sp
                            )
                        }
                    }
                    if (state.showDeleteDialog) {
                        AlertDialog(
                            properties = DialogProperties(
                                dismissOnClickOutside = false
                            ),
                            onDismissRequest = {
                                //viewPasswordViewModel.onIntent(ViewPasswordIntent.CloseDeleteDialog)
                            },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        viewPasswordViewModel.onIntent(ViewPasswordIntent.DeletePassword)
                                    }
                                ) {
                                    Text(text = "Delete")
                                }
                            },
                            dismissButton = {
                                TextButton(
                                    onClick = {
                                        viewPasswordViewModel.onIntent(ViewPasswordIntent.CloseDeleteDialog)
                                    }
                                ) {
                                    Text(text = "Cancel")
                                }
                            },
                            title = {
                                Text(text = "Delete?")
                            },
                            text = {
                                Text(text = "Are you sure want to delete ${state.password.username} record?")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
    ) {
        PrimaryText(
            modifier = Modifier.weight(0.5f),
            text = label,
            fontSize = 16.sp
        )
        OnPrimaryText(
            modifier = Modifier.weight(1f),
            text = value,
            fontSize = 16.sp
        )
    }
}
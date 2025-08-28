package com.ab.an.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.AssignmentInd
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.CameraEnhance
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ab.an.core.utils.Constants
import com.ab.an.core.utils.DateUtils
import com.ab.an.presentation.components.CustomAsyncImage
import com.ab.an.presentation.components.DetailRow
import com.ab.an.presentation.components.EditOptionRow
import com.ab.an.presentation.components.ErrorDialog
import com.ab.an.presentation.components.LoadingIndicatorScreen
import com.ab.an.presentation.components.PrimaryOutlinedButton
import com.ab.an.presentation.components.PrimaryText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewProfileScreen(
    navToAuth: () -> Unit,
    navBack: () -> Unit,
    navToUpdateContact: () -> Unit,
    navToAddOrEditProfilePicture: () -> Unit,
    changePassword:() -> Unit,
    viewModel: ViewProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isLoggedOut) {
        if (state.isLoggedOut) {
            navToAuth()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.secondary
    ) {
        Scaffold(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "Profile")
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    ),
                    navigationIcon = {
                        IconButton(
                            onClick = navBack
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = null,
                            )
                        }
                    },
                    actions = {
                        TooltipBox(
                           positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
                            tooltip =  {
                                PlainTooltip {
                                    Text(text = "Sync")
                                }
                            },
                            state = rememberTooltipState()
                        ) {
                            IconButton(
                                onClick = {
                                    viewModel.fetchUserFromApi()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Sync,
                                    contentDescription = "Sync",
                                )
                            }
                        }

                    }
                )
            },
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colorScheme.secondary
                    )
            ) {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .size(80.dp)
                                .padding(),
                            shape = CircleShape
                        ) {
                            CustomAsyncImage(
                                label = state.user.fullName,
                                url = "${Constants.E_BASE_URL}uploads/${state.user.profilePicture}",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Column(
                            modifier = Modifier.padding(vertical = 12.dp),
                            verticalArrangement = Arrangement.Center,
                        ) {
                            PrimaryText(
                                text = state.user.fullName,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 24.sp
                            )
                            Text(
                                text = state.user.email,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 16.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        HorizontalDivider()
                        DetailRow(
                            imageVector = Icons.Outlined.AssignmentInd,
                            label = "Name",
                            value = state.user.fullName
                        )
                        HorizontalDivider()
                        DetailRow(
                            imageVector = Icons.Outlined.Mail,
                            label = "Email",
                            value = state.user.email
                        )
                        HorizontalDivider()
                        DetailRow(
                            imageVector = Icons.Outlined.Phone,
                            label = "Phone number",
                            value = state.user.phoneNumber.ifBlank { "Not available" }
                        )
                        HorizontalDivider()
                        DetailRow(
                            imageVector = Icons.Outlined.Cake,
                            label = "Birthday",
                            value = if (state.user.dob.isNotBlank()) DateUtils.formatDateString(
                                state.user.dob, DateUtils.DOB_PATTERN,
                                DateUtils.BIRTH_DAY_PATTERN
                            ) else "Not available"
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        EditOptionRow (
                            leadingIcon = Icons.Outlined.CameraEnhance,
                            label = "Profile picture",
                            value = if (state.user.profilePicture.isNotBlank()) "Change or delete profile picture" else "Add a profile picture to your account",
                            trailingIcon = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                            onClick = {
                                navToAddOrEditProfilePicture()
                            }
                        )
                        HorizontalDivider()
                        EditOptionRow(
                            leadingIcon = Icons.Outlined.Password,
                            label = "Change password",
                            trailingIcon = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                            onClick = changePassword
                        )
                        HorizontalDivider()
                        EditOptionRow(
                            leadingIcon = Icons.Outlined.Mail,
                            label = "Update contact information",
                            trailingIcon = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                            value = "Edit profile details ",
                            onClick = {
                                navToUpdateContact()
                            }
                        )
                    }


                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        PrimaryOutlinedButton(
                            onClick = {
                                viewModel.logout()
                            },
                            label = "Log out"
                        )
                    }
                }
                if (state.isLoading) {
                    LoadingIndicatorScreen()
                }
                if (!state.error.isNullOrBlank()) {
                    state.error?.let {
                        ErrorDialog(it) {
                            navBack()
                        }
                    }
                }
            }
        }
    }
}




package com.ab.an.presentation.profile

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
import androidx.compose.material.icons.outlined.Contacts
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ab.an.core.utils.Constants
import com.ab.an.core.utils.DateUtils
import com.ab.an.presentation.components.CustomAsyncImage
import com.ab.an.presentation.components.DetailRow
import com.ab.an.presentation.components.EditOptionRow
import com.ab.an.presentation.components.ErrorDialog
import com.ab.an.presentation.components.LoadingIndicatorScreen
import com.ab.an.presentation.components.SecondaryButton
import com.ab.an.presentation.components.TopBarIcon
import com.ab.an.presentation.components.TopBarText
import com.ab.an.presentation.theme.AppTypography


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
        modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        TopBarText(
                            text = "Profile"
                        )
                    },
                    navigationIcon = {
                        TopBarIcon(
                            onClick = navBack,
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack
                        )
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
                            TopBarIcon(
                                onClick = {
                                    viewModel.fetchUserFromApi()
                                },
                                imageVector = Icons.Outlined.Sync
                            )
                        }

                    }
                )
            },
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
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
                            Text(
                                text = state.user.fullName,
                                color = MaterialTheme.colorScheme.primary,
                                style = AppTypography.headlineMedium
                            )
                            Text(
                                text = state.user.email,
                                color = MaterialTheme.colorScheme.outline,
                                style = AppTypography.titleMedium
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
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
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
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
                            leadingIcon = Icons.Outlined.Contacts,
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
                        SecondaryButton(
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




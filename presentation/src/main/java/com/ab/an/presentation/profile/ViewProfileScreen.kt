package com.ab.an.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material3.Card
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.ab.an.core.R
import com.ab.an.core.utils.CommonUtils
import com.ab.an.presentation.components.LoadingIndicatorScreen
import com.ab.an.presentation.components.PrimaryOutlinedButton
import com.ab.an.presentation.components.PrimaryText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewProfileScreen(
    navToAuth: () -> Unit,
    navBack: () -> Unit,
    navToUpdateContact: (String) -> Unit,
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
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Card(
                            modifier = Modifier.size(120.dp),
                            shape = CircleShape
                        ) {
                            if (state.user.profilePicture.isNotBlank()) {
                                AsyncImage(
                                    model = CommonUtils.decodeBase64ToByteArray(state.user.profilePicture),
                                    contentDescription = "Profile Picture",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Image(
                                    painter = painterResource(R.drawable.baseline_person_24),
                                    contentDescription = "Profile Picture",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                        PrimaryText(
                            text = state.user.fullName,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp
                        )
                        PrimaryText(
                            text = state.user.email
                        )
                        Text(
                            text = state.user.phoneNumber,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    AccountRow(
                        leadingIcon = Icons.Outlined.Password,
                        label = "Change Password",
                        trailingIcon = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                        onClick = {

                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    AccountRow(
                        leadingIcon = Icons.Outlined.Mail,
                        label = "Update Contact Information",
                        trailingIcon = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                        onClick = {
                            navToUpdateContact(state.user.id)
                        }
                    )
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
            }
        }
    }
}

@Composable
fun AccountRow(
    leadingIcon: ImageVector,
    label: String,
    trailingIcon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = label,
                modifier = Modifier.padding(5.dp)
            )
            Spacer(modifier = Modifier.size(20.dp))
            PrimaryText(
                text = label
            )
        }
        Icon(
            imageVector = trailingIcon,
            contentDescription = label,
            modifier = Modifier.size(16.dp)
        )
    }
}
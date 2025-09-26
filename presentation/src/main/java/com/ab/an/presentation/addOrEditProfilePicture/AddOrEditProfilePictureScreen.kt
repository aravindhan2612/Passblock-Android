package com.ab.an.presentation.addOrEditProfilePicture

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.AddPhotoAlternate
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ab.an.core.utils.Constants
import com.ab.an.presentation.components.CustomAsyncImage
import com.ab.an.presentation.components.ErrorDialog
import com.ab.an.presentation.components.LeadingIconButton
import com.ab.an.presentation.components.LoadingIndicatorScreen
import com.ab.an.presentation.components.TopBarIcon
import com.ab.an.presentation.components.TopBarText
import com.ab.an.presentation.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrEditPictureScreen(
    navBack: () -> Unit,
    addOrEditProfilePictureViewModel: AddOrEditProfilePictureViewModel = hiltViewModel()
) {
    val state by addOrEditProfilePictureViewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { validUri ->
            addOrEditProfilePictureViewModel.onIntent(AddOrEditProfilePictureIntent.UploadProfilePicture(context, validUri))
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    TopBarText(
                        text = "Profile picture"
                    )
                },
                navigationIcon = {
                    TopBarIcon(
                        onClick = navBack,
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack
                    )
                }
            )
        }
    ) { innerPadding ->
        when {
            !state.error.isNullOrBlank() -> {
                ErrorDialog(state.error!!, onClose = {
                    navBack()
                })
            }

            state.isLoading -> {
                LoadingIndicatorScreen()
            }

            else -> {
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                    ) {
                        Text(
                            text = "A picture help to recognize you and lets you know when you're signed in to account",
                            style = AppTypography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Card(
                                modifier = Modifier
                                    .size(240.dp)
                                    .padding(),
                                shape = CircleShape
                            ) {
                                CustomAsyncImage(
                                    label = state.fullName,
                                    url =  "${Constants.E_BASE_URL}uploads/${state.profilePicture}",
                                    modifier = Modifier.fillMaxSize(),
                                    fontSize = 40.sp
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                        }
                        if(state.profilePicture.isNotBlank()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround,
                            ) {
                                LeadingIconButton(
                                    onClick = {
                                        galleryLauncher.launch("image/*")
                                    },
                                    label = "Change",
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Filled.Edit,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                )
                                LeadingIconButton(
                                    onClick = {
                                        addOrEditProfilePictureViewModel.onIntent(AddOrEditProfilePictureIntent.DeleteProfilePicture)
                                    },
                                    label = "Delete",
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                )
                            }
                        } else {
                            LeadingIconButton(
                                onClick = {
                                    galleryLauncher.launch("image/*")
                                },
                                modifier = Modifier
                                    .padding(top = 20.dp)
                                    .fillMaxWidth(),
                                label = "Add profile picture",
                                icon = {
                                    Icon(
                                        imageVector = Icons.Outlined.AddPhotoAlternate,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
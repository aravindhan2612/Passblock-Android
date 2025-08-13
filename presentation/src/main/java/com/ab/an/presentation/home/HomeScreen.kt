package com.ab.an.presentation.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.ab.an.core.R
import com.ab.an.presentation.components.PrimaryButton
import com.ab.an.presentation.components.PrimaryText
import androidx.compose.runtime.collectAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    navToAddOrEditPassword: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val refreshState = rememberPullToRefreshState()
    val state by homeViewModel.state.collectAsStateWithLifecycle()
    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = {
            homeViewModel.fetchPasswords()
        },
        state = refreshState,
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        indicator = {
            Indicator(
                state = refreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = state.isLoading,
                containerColor = MaterialTheme.colorScheme.tertiary,
                color = MaterialTheme.colorScheme.secondary
            )
        }

    ) {
        if ((!state.errorMessage.isNullOrBlank() || state.passwords.isEmpty()) && !state.isLoading) {
            val isError = !state.errorMessage.isNullOrBlank()
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PrimaryText(
                    text = if (isError) state.errorMessage ?: "" else "No passwords found",
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                PrimaryButton(
                    label = if (isError) "Retry" else "Add new record",
                    onClick = {
                        if (isError) {
                            homeViewModel.fetchPasswords()
                        } else {
                            navToAddOrEditPassword()
                        }
                    }
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(state.passwords) { sectionListItem ->
                    when (sectionListItem) {
                        is PasswordSectionListItem.Header -> {
                            SectionHeader(title = sectionListItem.title)
                        }

                        is PasswordSectionListItem.Item -> {
                            ListItem(
                                item = sectionListItem
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    PrimaryText(
        text = title, modifier = Modifier
            .fillMaxWidth(),
        fontWeight = FontWeight.W600
    )
}

@Composable
fun ListItem(item: PasswordSectionListItem.Item) {
    val context = LocalContext.current
    val painter = rememberAsyncImagePainter(item.password.faviconUrl)
    val painterValue by painter.state.collectAsStateWithLifecycle()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Image(
            painter = if(painterValue.painter != null) painter else painterResource(R.drawable.outline_broken_image_24),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .weight(1f)
        ) {
            PrimaryText(text = item.password.name, fontWeight = FontWeight.Medium, fontSize = 18.sp)
            Text(
                text = item.password.username,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        IconButton(
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.secondary,
                containerColor = MaterialTheme.colorScheme.primary
            ),
            onClick = {
                Toast.makeText(context, "Copied ${item.password.username}", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.size(50.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ContentCopy,
                contentDescription = "Copy",
            )
        }
    }

}



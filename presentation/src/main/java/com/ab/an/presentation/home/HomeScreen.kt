package com.ab.an.presentation.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ab.an.domain.model.Password
import com.ab.an.presentation.addOrEditPassword.categories
import com.ab.an.presentation.components.CustomAsyncImage
import com.ab.an.presentation.components.FilterChip
import com.ab.an.presentation.components.PrimaryButton
import com.ab.an.presentation.components.PrimaryText
import com.ab.an.presentation.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    navToAddOrEditPassword: () -> Unit,
    navToViewPassword: (String) -> Unit,
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
                containerColor = MaterialTheme.colorScheme.secondary,
                color = MaterialTheme.colorScheme.onPrimary
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
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                TextField(
                    value = state.searchText,
                    onValueChange = {
                        homeViewModel.onIntent(HomeIntent.OnSearchTextChanged(it))
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .fillMaxWidth()
                        .clip(
                            shape = RoundedCornerShape(28.dp)
                        ),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,

                        ),
                    placeholder = {
                        Text(
                            text = "Search",
                            style = AppTypography.bodyLarge,
                            color = MaterialTheme.colorScheme.outline
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search",
                            modifier = Modifier.padding(16.dp)
                        )
                    },
                    maxLines = 1,
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    items(categories) { category ->
                        FilterChip(
                            text = category.name,
                            selected = state.selectedCategory == category,
                            onClick = {
                                homeViewModel.onIntent(
                                    HomeIntent.OnCategoryChanged(
                                        it,
                                        category
                                    )
                                )
                            }
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    items(state.filteredPasswords) { password ->
                        PasswordItem(
                            password = password,
                            onItemClick = {
                                navToViewPassword(password.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PasswordItem(
    password: Password,
    onItemClick: () -> Unit
) {
    val context = LocalContext.current
    ElevatedCard(
        modifier = Modifier.padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onItemClick()
                }
                .padding(12.dp)
        ) {
            CustomAsyncImage(
                label = password.name,
                url = password.faviconUrl,
                modifier = Modifier.size(50.dp)
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .weight(1f)
            ) {
                Text(
                    text = password.name,
                    style = AppTypography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = password.username,
                    style = AppTypography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
            IconButton(
                onClick = {
                    Toast.makeText(context, "Copied ${password.username}", Toast.LENGTH_SHORT)
                        .show()
                },
                modifier = Modifier.size(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ContentCopy,
                    contentDescription = "Copy",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }

}



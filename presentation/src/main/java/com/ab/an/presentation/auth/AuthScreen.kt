package com.ab.an.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ab.an.core.R
import com.ab.an.core.utils.Constants
import com.ab.an.presentation.components.LoadingIndicatorScreen
import com.ab.an.presentation.components.PrimaryButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    isRegister: Boolean,
    onComplete: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val state by authViewModel.state.collectAsStateWithLifecycle()
    val titles = listOf(Constants.REGISTER, Constants.LOGIN)
    val pagerState =
        rememberPagerState(initialPage = if (isRegister) 0 else 1, pageCount = { titles.size })
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(state.authSuccess) {
        if (state.authSuccess) {
            onComplete()
        }
    }

    LaunchedEffect(state.errorMessage) {
        if (!state.errorMessage.isNullOrBlank()) {
            Toast.makeText(context, state.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.secondary
    ) {

        Box(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painterResource(R.drawable.auth_header_logo),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = 36.dp
                            ),
                        border = BorderStroke(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        colors = CardDefaults.outlinedCardColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        SecondaryTabRow(
                            selectedTabIndex = pagerState.currentPage,
                            containerColor = Color.Transparent,
                            divider = {},
                            indicator = {
                                TabRowDefaults.SecondaryIndicator(
                                    Modifier
                                        .zIndex(-1f)
                                        .tabIndicatorOffset(pagerState.currentPage)
                                        .fillMaxSize() // Make the indicator fill the tab space
                                        .clip(RoundedCornerShape(8.dp)) // Apply rounded corners
                                        .background(MaterialTheme.colorScheme.primary) // Filled button color
                                )
                            },
                            modifier = Modifier
                                .padding(12.dp)
                                .clip(
                                    RoundedCornerShape(8.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(4.dp)
                        ) {
                            titles.forEachIndexed { index, title ->
                                val selected = pagerState.currentPage == index
                                Tab(
                                    modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                                    selected = selected,
                                    onClick = {
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(index)
                                            authViewModel.onIntent(AuthIntent.TabSwitched)
                                        }
                                    },
                                    text = {
                                        Text(
                                            text = title,
                                            fontSize = 16.sp,
                                            color = if (selected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    enabled = !state.isLoading
                                )
                            }
                        }
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp
                                ),
                            userScrollEnabled = false,
                        ) { page ->
                            when (page) {
                                0 -> RegisterScreen(state, authViewModel)
                                1 -> LoginScreen(state, authViewModel)
                            }
                        }
                    }
                }
                PrimaryButton(
                    onClick = {
                        authViewModel.onIntent(AuthIntent.Auth(titles[pagerState.currentPage]))
                    },
                    label = titles[pagerState.currentPage],
                    modifier = Modifier.fillMaxWidth(),
                    labelFontSize = 18.sp,
                    enabled = !state.isLoading,
                    isLoading = state.isLoading
                )
            }

            if (state.isLoading) {
                LoadingIndicatorScreen()
            }
        }
    }
}


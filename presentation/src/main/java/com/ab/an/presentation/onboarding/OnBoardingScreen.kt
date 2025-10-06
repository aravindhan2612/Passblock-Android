package com.ab.an.presentation.onboarding


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ab.an.presentation.R
import com.ab.an.presentation.components.PrimaryText
import com.ab.an.presentation.components.SecondaryButton
import com.ab.an.presentation.components.SecondaryOutlinedButton
import com.ab.an.presentation.theme.AppTypography
import kotlinx.coroutines.delay

@Composable
fun OnboardingScreen(
    navToAuth: (isRegister: Boolean) -> Unit,
    onboardViewModel: OnboardViewModel = hiltViewModel()
) {
    val onBoardState by onboardViewModel.state.collectAsStateWithLifecycle()
    val pagerState =
        rememberPagerState(
            initialPage = 0,
            pageCount = {
                onboardViewModel.onBoardDetails.size
            })

    LaunchedEffect(Unit) {
        while (pagerState.currentPage < pagerState.pageCount - 1) {
            delay(2000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.animateScrollToPage(nextPage)
        }
    }
    LaunchedEffect(onBoardState.isComplete) {
        if (onBoardState.isComplete) {
            navToAuth(onBoardState.isRegister)
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .fillMaxSize()
        ) {
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.outlineVariant
                    Box(
                        modifier = Modifier
                            .padding(20.dp)
                            .weight(0.5f)
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .border(
                                width = 1.dp,
                                color = Color.Transparent,
                                shape = RoundedCornerShape(5.dp)
                            )
                            .background(color)
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) { page ->
                OnBoardItem(onboardViewModel.onBoardDetails[page])
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                SecondaryButton(
                    onClick = {
                        onboardViewModel.setOnBoardShown(true)
                    },
                    label = stringResource(R.string.register),
                    modifier = Modifier.fillMaxWidth(),
                )
                SecondaryOutlinedButton(
                    onClick = {
                        onboardViewModel.setOnBoardShown(false)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(R.string.already_have_an_account),
                )
            }
        }
    }
}


@Composable
fun OnBoardItem(onBoardModel: OnBoardDetail) {
    if (onBoardModel.headLine.isNotBlank()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
        ) {
            Image(
                painter = painterResource(id = onBoardModel.imageRes),
                contentDescription = "",
                modifier = Modifier
                    .padding(
                        vertical = 50.dp
                    )
                    .fillMaxWidth(),
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Spacer(
                modifier = Modifier.height(20.dp)
            )
            PrimaryText(
                text = onBoardModel.headLine,
                fontSize = 20.sp
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.primary,
                thickness = 2.dp,
                modifier = Modifier.width(40.dp)
            )
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            Text(
                text = onBoardModel.title,
                color = MaterialTheme.colorScheme.onSurface,
                style = AppTypography.titleLarge
            )
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            Text(
                text = onBoardModel.description,
                color = MaterialTheme.colorScheme.outline,
                style = AppTypography.bodyMedium
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier.height(20.dp)
            )
            Image(
                painter = painterResource(id = onBoardModel.imageRes),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth(),
            )
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            PrimaryText(
                text = onBoardModel.title,
                fontSize = 40.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = onBoardModel.description,
                color = MaterialTheme.colorScheme.onSurface,
                style = AppTypography.titleLarge
            )
        }
    }
}
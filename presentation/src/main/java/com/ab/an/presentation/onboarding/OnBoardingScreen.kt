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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ab.an.core.utils.Constants
import com.ab.an.presentation.components.PrimaryBoldText
import com.ab.an.presentation.components.PrimaryButton
import com.ab.an.presentation.components.PrimaryNormalText
import com.ab.an.presentation.components.PrimaryOutlinedButton
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
            pageCount = { onboardViewModel.onBoardDetails.size })

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
                    if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .weight(0.5f)
                        .height(10.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
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
            PrimaryButton(
                onClick = {
                    onboardViewModel.setOnBoardShown(true)
                },
                label = Constants.REGISTER,
                modifier = Modifier.fillMaxWidth(),
                labelFontSize = 20.sp
            )
            PrimaryOutlinedButton(
                onClick = {
                    onboardViewModel.setOnBoardShown(false)
                },
                modifier = Modifier.fillMaxWidth(),
                label = Constants.ALREADY_HAVE_ACCOUNT,
                labelFontSize = 18.sp
            )
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
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(
                modifier = Modifier.height(20.dp)
            )
            PrimaryNormalText(
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
            PrimaryBoldText(
                text = onBoardModel.title,
                fontSize = 24.sp
            )
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            PrimaryNormalText(
                text = onBoardModel.description,
                fontSize = 16.sp,
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
            PrimaryBoldText(
                text = onBoardModel.title,
                fontSize = 40.sp,
                textAlign = TextAlign.Center
            )
            PrimaryNormalText(
                text = onBoardModel.description,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
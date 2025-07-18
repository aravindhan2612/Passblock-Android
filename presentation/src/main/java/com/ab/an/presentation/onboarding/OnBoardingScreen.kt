package com.ab.an.presentation.onboarding


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun OnboardingScreen(innerPadding: PaddingValues, navToAuth: (isRegister: Boolean) -> Unit) {
    val onBoardModels = onBoardModels
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { onBoardModels.size })
    LaunchedEffect(Unit) {
        while (pagerState.currentPage < pagerState.pageCount - 1) {
            delay(2000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.animateScrollToPage(nextPage)
        }
    }
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
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
            OnBoardItem(onBoardModels[page])
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Button(
                onClick = {
                    navToAuth(true)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Register",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    navToAuth(false)
                },
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Already have an account",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }

        }
    }
}


@Composable
fun OnBoardItem(onBoardModel: OnBoardModel) {
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
                    .fillMaxWidth()
            )
            Spacer(
                modifier = Modifier.height(20.dp)
            )
            Text(
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
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            Text(
                text = onBoardModel.description,
                fontSize = 16.sp
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
                    .fillMaxWidth()
            )
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            Text(
                text = onBoardModel.title,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = onBoardModel.description,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
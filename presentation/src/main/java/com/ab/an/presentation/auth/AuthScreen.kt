package com.ab.an.presentation.auth

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabIndicatorScope
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.ab.an.core.R
import com.ab.an.core.ui.components.PrimaryBoldText
import com.ab.an.core.ui.components.PrimaryNormalText
import com.ab.an.core.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    innerPadding: PaddingValues,
    isRegister: Boolean,
    onClick: () -> Unit
) {

    var state by remember { mutableIntStateOf(if (isRegister) 0 else 1) }
    val titles = listOf(Constants.REGISTER, Constants.LOGIN)

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
                selectedTabIndex = state,
                containerColor = Color.Transparent,
                divider = {},
                indicator = {
                    TabRowDefaults.SecondaryIndicator(
                        Modifier
                            .zIndex(-1f)
                            .tabIndicatorOffset(state)
                            .fillMaxSize() // Make the indicator fill the tab space
                            .padding(3.dp)
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
                    val selected = state == index
                    Tab(
                        selected = selected,
                        onClick = { state = index },
                        text = {
                            Text(
                                text = title,
                                fontSize = 16.sp,
                                color = if (selected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
                            )
                        },
                    )
                }
            }
            PrimaryNormalText(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Secondary tab ${state + 1} selected",
            )

        }
    }
}


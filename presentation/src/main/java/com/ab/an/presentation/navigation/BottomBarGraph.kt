package com.ab.an.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.util.fastForEachIndexed
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.ab.an.core.R
import com.ab.an.presentation.analysis.AnalysisScreen
import com.ab.an.presentation.components.PrimaryNormalText
import com.ab.an.presentation.home.HomeScreen
import com.ab.an.presentation.search.SearchScreen
import com.ab.an.presentation.setting.SettingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NestedGraph(navToAddOrEditPassword: () -> Unit) {
    val backStack = rememberNavBackStack(BottomBarRoute.Home)
    var currentRoute: BottomBarRoute by rememberSaveable(
        stateSaver = BottomBarScreenSaver
    ) { mutableStateOf(BottomBarRoute.Home) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.secondary,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    PrimaryNormalText(
                        text = currentRoute.title
                    )
                },
                navigationIcon = {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.profile),
                                contentDescription = null
                            )
                        }
                },
                actions = {
                        IconButton(
                            onClick = navToAddOrEditPassword
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.add),
                                contentDescription = null
                            )
                        }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                windowInsets = NavigationBarDefaults.windowInsets,
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                bottomNavigationRoutes.fastForEachIndexed { index, item ->
                    val isSelected = currentRoute == item.route
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            if(backStack.lastOrNull() != item.route) {
                                if(backStack.lastOrNull() in bottomNavigationRoutes.map { it.route }) {
                                    backStack.removeAt(backStack.lastIndex)
                                }
                                backStack.add(item.route)
                                currentRoute = item.route
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) item.filledIcon else item.outlineIcon,
                                contentDescription = null,
                            )
                        },
                        label = {
                            Text(
                                text = item.label
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent,
                        )
                    )
                }
            }
        }
    ) {  innerPadding ->

        NavDisplay(
            backStack = backStack,
            entryDecorators = listOf(
                rememberSavedStateNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<BottomBarRoute.Home> {
                    HomeScreen(innerPadding)
                }
                entry<BottomBarRoute.Analysis> {
                    AnalysisScreen(innerPadding)
                }
                entry<BottomBarRoute.Search> {
                    SearchScreen(innerPadding)
                }
                entry<BottomBarRoute.Setting> {
                    SettingScreen(innerPadding)
                }
            }
        )
    }
}
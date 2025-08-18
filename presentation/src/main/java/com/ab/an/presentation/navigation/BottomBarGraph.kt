package com.ab.an.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Add
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.util.fastForEachIndexed
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ab.an.presentation.home.HomeScreen
import com.ab.an.presentation.search.SearchScreen
import com.ab.an.presentation.setting.SettingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarGraph(
    rootNavController: NavHostController,
) {
    val navController = rememberNavController()
    var currentRoute: BottomBarRoute by rememberSaveable(
        stateSaver = BottomBarScreenSaver
    ) { mutableStateOf(BottomBarRoute.Home) }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.secondary
    ) {
        Scaffold(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Blue, Color.Cyan)
                    )
                ),
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    ),
                    title = {
                        Text(
                            text = currentRoute.title
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                rootNavController.navigate(RootRoute.Profile)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                rootNavController.navigate(RootRoute.AddOrEditPassword(false))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Add,
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
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                currentRoute = item.route
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
        ) { innerPadding ->

            NavHost(
                navController = navController,
                startDestination = BottomBarRoute.Home
            ) {
                composable<BottomBarRoute.Home> {
                    HomeScreen(
                        innerPadding = innerPadding,
                        navToAddOrEditPassword = {
                            rootNavController.navigate(RootRoute.AddOrEditPassword(false))
                        },
                        navToViewPassword = { id ->
                            rootNavController.navigate(RootRoute.ViewPassword(id = id))
                        }
                    )
                }
                composable<BottomBarRoute.Search> {
                    SearchScreen(innerPadding)
                }
                composable<BottomBarRoute.Setting> {
                    SettingScreen(innerPadding, navToProfile = {
                        rootNavController.navigate(RootRoute.Profile)
                    })
                }
            }
        }
    }
}
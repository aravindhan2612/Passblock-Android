package com.ab.an.presentation.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.util.fastForEachIndexed
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ab.an.presentation.components.TopBarIcon
import com.ab.an.presentation.components.TopBarText
import com.ab.an.presentation.home.HomeScreen
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
    ) {
        Scaffold(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .fillMaxSize(),
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
                    title = {
                        TopBarText(
                            text = currentRoute.title,
                        )
                    },
                    navigationIcon = {
                        if (currentRoute.showNavigationIcon) {
                            TopBarIcon(
                                onClick = {
                                    rootNavController.navigate(RootRoute.ViewProfile)
                                },
                                imageVector = Icons.Default.Person
                            )
                        }
                    },
                    actions = {
                        if (currentRoute.showActionIcon) {
                            TopBarIcon(
                                onClick = {
                                    rootNavController.navigate(RootRoute.AddOrEditPassword(false))
                                },
                                imageVector = Icons.Default.Add
                            )
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar(
                    windowInsets = NavigationBarDefaults.windowInsets
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
                                indicatorColor = MaterialTheme.colorScheme.secondary,
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
                composable<BottomBarRoute.Setting> {
                    SettingScreen(innerPadding, navToProfile = {
                        rootNavController.navigate(RootRoute.ViewProfile)
                    })
                }
            }
        }
    }
}
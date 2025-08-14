package com.ab.an.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FindInPage
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FindInPage
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.ab.an.core.utils.Constants
import kotlinx.serialization.Serializable

@Serializable
sealed class BottomBarRoute(val title: String): NavKey {
    @Serializable
    data object Home: BottomBarRoute(
        title = Constants.PASSWORDS,
    )
    @Serializable
    data object Profile: BottomBarRoute(
        title = Constants.PROFILE,
    )
    @Serializable
    data object Search: BottomBarRoute(
        title = Constants.SEARCH,
    )
    @Serializable
    data object Setting: BottomBarRoute(
        title = Constants.SETTING,
    )
}

data class NavigationBarItemDetail(
    val route: BottomBarRoute,
    val label: String,
    val outlineIcon: ImageVector,
    val filledIcon: ImageVector,
)

val bottomNavigationRoutes = listOf(
    NavigationBarItemDetail(
        BottomBarRoute.Home,
        "Home",
        outlineIcon = Icons.Outlined.Home,
        filledIcon = Icons.Filled.Home
    ),
    NavigationBarItemDetail(
        BottomBarRoute.Search,
        "Search",
        outlineIcon = Icons.Outlined.FindInPage,
        filledIcon = Icons.Filled.FindInPage
    ),
    NavigationBarItemDetail(
        BottomBarRoute.Setting,
        "Setting",
        outlineIcon = Icons.Outlined.Settings,
        filledIcon = Icons.Filled.Settings
    ),
    NavigationBarItemDetail(
        BottomBarRoute.Profile,

        "Profile",
        outlineIcon = Icons.Outlined.PersonOutline,
        filledIcon = Icons.Filled.Person
    ),
)

val BottomBarScreenSaver = Saver<BottomBarRoute, String>(
    save = { it::class.simpleName ?: "Unknown" },
    restore = {
        when (it) {
            BottomBarRoute.Home::class.simpleName -> BottomBarRoute.Home
            BottomBarRoute.Search::class.simpleName -> BottomBarRoute.Search
            BottomBarRoute.Setting::class.simpleName -> BottomBarRoute.Setting
            BottomBarRoute.Profile::class.simpleName -> BottomBarRoute.Profile
            else -> BottomBarRoute.Home

        }
    }
)
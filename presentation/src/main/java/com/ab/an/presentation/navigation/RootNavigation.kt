package com.ab.an.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.ab.an.presentation.auth.AuthScreen
import com.ab.an.presentation.onboarding.OnboardingScreen


@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun RootNavigation() {
    val backStack = rememberNavBackStack(Route.Onboarding)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            onBack = {
                backStack.removeLastOrNull()
            },
            entryProvider = entryProvider {
                entry<Route.Onboarding> {
                    OnboardingScreen(innerPadding) {
                        backStack.add(Route.Auth("Aravindhan"))
                        backStack.removeFirstOrNull()
                    }
                }
                entry<Route.Auth> {
                    AuthScreen(it.name) {
                        backStack.removeLastOrNull()
                    }
                }
            }
        )
    }
}
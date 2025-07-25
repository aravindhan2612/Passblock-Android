package com.ab.an.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.ab.an.presentation.auth.AuthScreen
import com.ab.an.presentation.onboarding.OnboardingScreen
import com.ab.an.presentation.password.AddOrEditPasswordScreen
import com.ab.an.presentation.splash.SplashScreen


@Composable
fun RootGraph(
    rootViewModel: RootViewModel = hiltViewModel()
) {
    val backStack = rememberNavBackStack(RootRoute.Splash)

    NavDisplay(
        backStack = backStack,
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        onBack = {
            backStack.removeLastOrNull()
        },
        entryProvider = entryProvider {
            entry<RootRoute.Splash> {
                SplashScreen(onComplete = {
                    backStack.removeLastOrNull()
                    backStack.add(it)

                })
            }
            entry<RootRoute.Onboarding> {
                OnboardingScreen(
                    navToAuth = {
                        backStack.removeLastOrNull()
                        backStack.add(RootRoute.Auth(it))
                    })
            }
            entry<RootRoute.Auth> {
                AuthScreen(
                    isRegister = it.isRegister,
                    onComplete = {
                        backStack.removeLastOrNull()
                        backStack.add(RootRoute.NestedGraph)
                    }
                )
            }
            entry<RootRoute.NestedGraph>{
                NestedGraph(navToAddOrEditPassword = {
                    backStack.add(RootRoute.AddOrEditPassword)
                })
            }

            entry<RootRoute.AddOrEditPassword> {
                AddOrEditPasswordScreen(navToHome = {

                })
            }
        }
    )
}
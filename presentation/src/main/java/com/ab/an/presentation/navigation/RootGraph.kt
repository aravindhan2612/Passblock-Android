package com.ab.an.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ab.an.presentation.addOrEditPassword.AddOrEditPasswordScreen
import com.ab.an.presentation.addOrEditProfilePicture.AddOrEditPictureScreen
import com.ab.an.presentation.auth.AuthScreen
import com.ab.an.presentation.changePassword.ChangePasswordScreen
import com.ab.an.presentation.onboarding.OnboardingScreen
import com.ab.an.presentation.profile.ViewProfileScreen
import com.ab.an.presentation.updateContactInfo.UpdateContactInfoScreen
import com.ab.an.presentation.viewPassword.ViewPasswordScreen


@Composable
fun RootGraph(
    rootViewModel: RootViewModel = hiltViewModel(),
    startDestination: RootRoute
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<RootRoute.Onboarding> {
            OnboardingScreen(
                navToAuth = {
                    navController.navigate(RootRoute.Auth(it)) {
                        popUpTo(RootRoute.Onboarding) {
                            inclusive = true
                        }
                    }
                })
        }
        composable<RootRoute.Auth> { backStackEntry ->
            val auth: RootRoute.Auth = backStackEntry.toRoute()
            AuthScreen(
                isRegister = auth.isRegister,
                onComplete = {
                    navController.navigate(RootRoute.BottomBarGraph) {
                        popUpTo<RootRoute.Auth> {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<RootRoute.BottomBarGraph> {
            BottomBarGraph(
                rootNavController = navController
            )
        }
        composable<RootRoute.AddOrEditPassword> { backStackEntry ->
            val addOrEditPassword: RootRoute.AddOrEditPassword = backStackEntry.toRoute()
            AddOrEditPasswordScreen(
                isEditMode = addOrEditPassword.isEditMode,
                navToHome = {
                    navController.popBackStack()
                },
                id = addOrEditPassword.id
            )
        }
        composable<RootRoute.ViewPassword> {
            val viewPassword: RootRoute.ViewPassword = it.toRoute()
            ViewPasswordScreen(
                id = viewPassword.id,
                navToHome = {
                    navController.popBackStack()
                },
                navToAddOrEditPassword = {
                    navController.navigate(RootRoute.AddOrEditPassword(true, viewPassword.id))
                }
            )
        }
        composable<RootRoute.ViewProfile> {
            ViewProfileScreen(
                navToAuth = {
                    navController.navigate(RootRoute.Auth(false)) {
                        popUpTo<RootRoute.BottomBarGraph> {
                            inclusive = true
                        }
                    }
                }, navBack = {
                    navController.popBackStack()
                },
                navToUpdateContact = {
                    navController.navigate(RootRoute.UpdateContactInfo)
                },
                navToAddOrEditProfilePicture = {
                    navController.navigate(RootRoute.AddOrEditProfilePicture)
                },
                changePassword = {
                    navController.navigate(RootRoute.ChangePassword)
                }
            )
        }
        composable<RootRoute.UpdateContactInfo> {
            UpdateContactInfoScreen(
                navBack = {
                    navController.popBackStack()
                },
                navToAddOrEditProfilePicture = {
                    navController.navigate(RootRoute.AddOrEditProfilePicture)
                }
            )
        }
        composable<RootRoute.AddOrEditProfilePicture> {
            AddOrEditPictureScreen(
                navBack = {
                    navController.popBackStack()
                }
            )
        }
        composable<RootRoute.ChangePassword> {
            ChangePasswordScreen(navBack = {
                navController.popBackStack()
            })
        }
    }
}
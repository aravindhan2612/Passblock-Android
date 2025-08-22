package com.ab.an.presentation.onboarding

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OnBoardingScreenTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()


    @Test
    fun testContent() {
        composeTestRule.setContent {
            OnboardingScreen(navToAuth = {})
        }
        composeTestRule.onNodeWithText("Register").assertIsDisplayed()
        composeTestRule.onNodeWithText("Already have an account?").assertIsDisplayed()
    }

}
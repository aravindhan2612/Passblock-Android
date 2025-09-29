package com.ab.an.presentation.addOrEditPassword

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.vector.ImageVector
import com.ab.an.domain.model.Password

data class AddOrEditPasswordState(
    val passwordEntity: Password = Password(),
    val nameError: String = "",
    val userNameError: String = "",
    val linkError: String = "",
    val passwordError: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val fetchPasswordError: String? = null
)

internal val categories = listOf<Category>(
    Category("Work", Icons.Filled.Work),
    Category("Personal", Icons.Filled.Person),
    Category("Finance", Icons.Filled.AccountBalanceWallet),
    Category("Other", Icons.Filled.MoreHoriz)
)

data class Category(
    val name: String,
    val icon: ImageVector
)

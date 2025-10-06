package com.ab.an.presentation.setting

data class SettingState(
    val checked: Boolean = false,
    val showBottomSheet: Boolean = false,
    val themeValue: String = ThemeMode.SYSTEM.value
)

enum class ThemeMode(val value: String) {
    SYSTEM("System"),
    LIGHT("Light"),
    DARK("Dark")
}

package com.ab.an.presentation.setting

sealed class SettingStateIntent {
    data class OnCheckedChange(val checked: Boolean) : SettingStateIntent()
    data class OnSheetChange(val isSheetOpen: Boolean) : SettingStateIntent()
    data class OnThemeModeChange(val themeValue: String) : SettingStateIntent()
}
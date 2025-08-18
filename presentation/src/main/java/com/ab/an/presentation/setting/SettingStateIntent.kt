package com.ab.an.presentation.setting

sealed class SettingStateIntent {
    data class OnCheckedChange(val checked: Boolean) : SettingStateIntent()
}
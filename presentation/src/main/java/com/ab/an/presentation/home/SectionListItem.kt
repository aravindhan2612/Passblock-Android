package com.ab.an.presentation.home

import com.ab.an.domain.model.Password

sealed class PasswordSectionListItem() {
    data class Header(val title: String) : PasswordSectionListItem()
    data class Item(val password: Password) :
        PasswordSectionListItem()
}
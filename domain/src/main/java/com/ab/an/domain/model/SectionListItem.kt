package com.ab.an.domain.model

sealed class SectionListItem() {
    data class Header(val title: String) : SectionListItem()
    data class Item(val name: String, val username: String, val password: String) :
        SectionListItem()
}
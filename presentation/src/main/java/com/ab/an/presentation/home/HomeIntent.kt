package com.ab.an.presentation.home

import com.ab.an.presentation.addOrEditPassword.Category

sealed class HomeIntent {
    object  FetchPasswords: HomeIntent()
    data class OnSearchTextChanged(val text:String): HomeIntent()

    data class OnCategoryChanged(val selected: Boolean,val category: Category?): HomeIntent()
}
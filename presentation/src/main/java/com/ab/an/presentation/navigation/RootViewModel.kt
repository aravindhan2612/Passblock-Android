package com.ab.an.presentation.navigation

import androidx.lifecycle.ViewModel
import com.ab.an.domain.repository.AppDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(private val appDataStoreRepository: AppDataStoreRepository) :
    ViewModel() {

    fun onEvent(event: RootIntent) {
    }
}
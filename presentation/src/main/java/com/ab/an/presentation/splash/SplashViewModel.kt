package com.ab.an.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.an.domain.repository.AppDataStoreRepository
import com.ab.an.presentation.navigation.RootRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val appDataStoreRepository: AppDataStoreRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(SplashState())
    val state = _state.onStart {
        loadData()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        null
    )

    private suspend fun loadData() {
        val isOnboardingShown = appDataStoreRepository.getOnBoardShown().firstOrNull()
        val isUserLoggedIn = appDataStoreRepository.isUserLoggedIn().firstOrNull()
        delay(3000L)
        when {
            isUserLoggedIn == true -> {
                _state.update {
                    it.copy(
                        route = RootRoute.NestedGraph
                    )
                }
            }

            isOnboardingShown == true -> {
                _state.update {
                    it.copy(
                        route = RootRoute.Auth()
                    )
                }
            }

            else -> {
                _state.update {
                    it.copy(
                        route = RootRoute.Onboarding
                    )
                }
            }
        }
    }

}
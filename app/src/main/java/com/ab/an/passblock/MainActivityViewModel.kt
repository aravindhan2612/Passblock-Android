package com.ab.an.passblock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.an.domain.repository.AppSettingsDataStoreRepository
import com.ab.an.presentation.navigation.RootRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val appSettingsDataStoreRepository: AppSettingsDataStoreRepository
) : ViewModel(){
    private val _state = MutableStateFlow(MainState())
    val state = _state.onStart {
        setRoute()
        getThemeMode()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        MainState()
    )

    private fun getThemeMode() {
        viewModelScope.launch {
            appSettingsDataStoreRepository.themeMode.collect { themeMode ->
                _state.update {
                    it.copy(
                        theme = themeMode
                    )
                }
            }
        }
    }

    private fun setRoute() {
        viewModelScope.launch {
            val appData = listOf(
                async {
                    appSettingsDataStoreRepository.getOnBoardShown().firstOrNull()
                },
                async {
                    appSettingsDataStoreRepository.isUserLoggedIn().firstOrNull()
                }
            ).awaitAll()
            when {
                appData[1] == true && appData[0] == true -> {
                    _state.update {
                        it.copy(
                            route = RootRoute.BottomBarGraph,
                            isLoading = false
                        )
                    }
                }

                appData[0] == true -> {
                    _state.update {
                        it.copy(
                            route = RootRoute.Auth(),
                            isLoading = false
                        )
                    }
                }

                else -> {
                    _state.update {
                        it.copy(
                            route = RootRoute.Onboarding,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}
package com.ab.an.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.an.domain.repository.AppDataStoreRepository
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

    private fun loadData() {
        viewModelScope.launch {
            val appData = listOf(
                async {
                    appDataStoreRepository.getOnBoardShown().firstOrNull()
                      },
                async { appDataStoreRepository.isUserLoggedIn().firstOrNull() }
            ).awaitAll()
            when {
                appData[1] == true && appData[0] == true -> {
                    _state.update {
                        it.copy(
                            route = RootRoute.BottomBarGraph
                        )
                    }
                }

                appData[0] == true -> {
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

}
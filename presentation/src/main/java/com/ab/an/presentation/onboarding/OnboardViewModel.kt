package com.ab.an.presentation.onboarding

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.an.core.utils.Constants.SECURITY
import com.ab.an.domain.repository.AppSettingsDataStoreRepository
import com.ab.an.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val appDataStoreRepository: AppSettingsDataStoreRepository
) :
    ViewModel() {

    private val _state = MutableStateFlow(OnBoardState())
    val state = _state.asStateFlow()


    fun setOnBoardShown(isRegister: Boolean) {
        viewModelScope.launch {
            async {
                appDataStoreRepository.setOnBoardShown(true)
            }.await()
            _state.update {
                it.copy(
                    isComplete = true,
                    isRegister = isRegister,
                )
            }
        }
    }

    val onBoardDetails = listOf(
        OnBoardDetail(
            headLine = SECURITY,
            title = context.getString(R.string.control_your_security),
            description = context.getString(R.string.ob_description_1),
            imageRes = R.drawable.shield_tick
        ),
        OnBoardDetail(
            headLine = context.getString(R.string.fast),
            title = context.getString(R.string.everything_in_single_click),
            description = context.getString(R.string.ob_description_2),
            imageRes = R.drawable.box
        ),
        OnBoardDetail(
            title = context.getString(R.string.pass_block),
            description = context.getString(R.string.frictionless_security),
            imageRes = R.drawable.obthree
        )
    )
}
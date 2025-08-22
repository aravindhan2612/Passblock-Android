package com.ab.an.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.an.core.R
import com.ab.an.core.utils.Constants.CONTROL_YOUR_SECURITY
import com.ab.an.core.utils.Constants.EVERYTHING_IN_SINGLE_CLICK
import com.ab.an.core.utils.Constants.FAST
import com.ab.an.core.utils.Constants.FRICTIONLESS_SECURITY
import com.ab.an.core.utils.Constants.OB_DESCRIPTION_1
import com.ab.an.core.utils.Constants.OB_DESCRIPTION_2
import com.ab.an.core.utils.Constants.PASS_BLOCK
import com.ab.an.core.utils.Constants.SECURITY
import com.ab.an.domain.repository.AppSettingsDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor(private val appDataStoreRepository: AppSettingsDataStoreRepository) :
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
            title = CONTROL_YOUR_SECURITY,
            description = OB_DESCRIPTION_1,
            imageRes = R.drawable.shield_tick
        ),
        OnBoardDetail(
            headLine = FAST,
            title = EVERYTHING_IN_SINGLE_CLICK,
            description = OB_DESCRIPTION_2,
            imageRes = R.drawable.box
        ),
        OnBoardDetail(
            title = PASS_BLOCK,
            description = FRICTIONLESS_SECURITY,
            imageRes = R.drawable.obthree
        )
    )
}
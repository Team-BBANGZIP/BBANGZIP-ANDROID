package org.android.bbangzip.presentation.ui.screensetting

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.bbangzip.domain.repository.UserDefaultRepository
import org.android.bbangzip.presentation.common.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ScreenSettingViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val userDefaultRepository: UserDefaultRepository,
    ) : BaseViewModel<ScreenSettingContract.ScreenSettingEvent, ScreenSettingContract.ScreenSettingState, ScreenSettingContract.ScreenSettingReduce, ScreenSettingContract.ScreenSettingSideEffect>(
            savedStateHandle = savedStateHandle,
        ) {
        init {
            setEvent(ScreenSettingContract.ScreenSettingEvent.Initialize)
        }

        override fun createInitialState(savedState: Parcelable?): ScreenSettingContract.ScreenSettingState {
            return savedState as? ScreenSettingContract.ScreenSettingState
                ?: ScreenSettingContract.ScreenSettingState()
        }

        override fun handleEvent(event: ScreenSettingContract.ScreenSettingEvent) {
            when (event) {
                ScreenSettingContract.ScreenSettingEvent.Initialize -> {
                    viewModelScope.launch {
                        userDefaultRepository.userPreferenceFlow.collect { preferences ->
                            updateState(
                                ScreenSettingContract.ScreenSettingReduce.UpdateSundayStartEnabled(
                                    isEnabled = preferences.isSundayStart,
                                ),
                            )
                        }
                    }
                }

                ScreenSettingContract.ScreenSettingEvent.OnBackIconClick -> {
                    setSideEffect(ScreenSettingContract.ScreenSettingSideEffect.NavigateToBack)
                }

                ScreenSettingContract.ScreenSettingEvent.OnSundayStartToggle -> {
                    viewModelScope.launch {
                        userDefaultRepository.setIsSundayStart(!currentUiState.isSundayStartEnabled)
                    }
                    updateState(
                        ScreenSettingContract.ScreenSettingReduce.UpdateSundayStartEnabled(
                            isEnabled = !currentUiState.isSundayStartEnabled,
                        ),
                    )
                }
            }
        }

        override fun reduceState(
            state: ScreenSettingContract.ScreenSettingState,
            reduce: ScreenSettingContract.ScreenSettingReduce,
        ): ScreenSettingContract.ScreenSettingState {
            return when (reduce) {
                is ScreenSettingContract.ScreenSettingReduce.UpdateSundayStartEnabled ->
                    state.copy(isSundayStartEnabled = reduce.isEnabled)
            }
        }
    }

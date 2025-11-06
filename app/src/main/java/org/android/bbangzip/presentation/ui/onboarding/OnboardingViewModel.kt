package org.android.bbangzip.presentation.ui.onboarding

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import org.android.bbangzip.presentation.common.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
    ) : BaseViewModel<OnboardingContract.OnboardingEvent, OnboardingContract.OnboardingState, OnboardingContract.OnboardingReduce, OnboardingContract.OnboardingSideEffect>(
            savedStateHandle = savedStateHandle,
        ) {
        override fun createInitialState(savedState: Parcelable?): OnboardingContract.OnboardingState {
            return savedState as? OnboardingContract.OnboardingState ?: OnboardingContract.OnboardingState()
        }

        override fun handleEvent(event: OnboardingContract.OnboardingEvent) {
        }

        override fun reduceState(
            state: OnboardingContract.OnboardingState,
            reduce: OnboardingContract.OnboardingReduce,
        ): OnboardingContract.OnboardingState {
            TODO("Not yet implemented")
        }
    }

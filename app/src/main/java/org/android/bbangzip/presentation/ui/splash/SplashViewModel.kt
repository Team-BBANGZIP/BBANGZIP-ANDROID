package org.android.bbangzip.presentation.ui.splash

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.android.bbangzip.domain.repository.UserDefaultRepository
import org.android.bbangzip.presentation.common.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
    @Inject
    constructor(
        private val userDefaultRepository: UserDefaultRepository,
        savedStateHandle: SavedStateHandle,
    ) : BaseViewModel<SplashContract.SplashEvent, SplashContract.SplashState, SplashContract.SplashReduce, SplashContract.SplashSideEffect>(
            savedStateHandle = savedStateHandle,
        ) {
        override fun createInitialState(savedState: Parcelable?): SplashContract.SplashState =
            savedState as? SplashContract.SplashState ?: SplashContract.SplashState()

        override fun handleEvent(event: SplashContract.SplashEvent) {
            when (event) {
                SplashContract.SplashEvent.Initialize -> checkLoginStatus()
            }
        }

        override fun reduceState(
            state: SplashContract.SplashState,
            reduce: SplashContract.SplashReduce,
        ): SplashContract.SplashState =
            when (reduce) {
                is SplashContract.SplashReduce.UpdateState -> reduce.state
            }

        private fun checkLoginStatus() {
            viewModelScope.launch {
                delay(SPLASH_DELAY_MS)
                val isLogin = userDefaultRepository.userPreferenceFlow.firstOrNull()?.isLogin ?: false
                if (isLogin) {
                    setSideEffect(SplashContract.SplashSideEffect.NavigateToTimer)
                } else {
                    setSideEffect(SplashContract.SplashSideEffect.NavigateToLogin)
                }
            }
        }

        companion object {
            private const val SPLASH_DELAY_MS = 1000L
        }
    }

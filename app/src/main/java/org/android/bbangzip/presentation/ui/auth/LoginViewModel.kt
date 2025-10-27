package org.android.bbangzip.presentation.ui.auth


import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.android.bbangzip.UserPreferences
import org.android.bbangzip.data.auth.service.KakaoAuthService
import org.android.bbangzip.domain.repository.UserDefaultRepository
import org.android.bbangzip.domain.repository.UserRepository
import org.android.bbangzip.presentation.common.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val kakaoAuthService: KakaoAuthService,
    private val userDefaultRepository: UserDefaultRepository,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<LoginContract.LoginEvent, LoginContract.LoginState, LoginContract.LoginReduce, LoginContract.LoginSideEffect>(
    savedStateHandle = savedStateHandle,
) {
    val userPreferencesFlow: Flow<UserPreferences> = userDefaultRepository.userPreferenceFlow

    init {
        startAnimation()
    }

    override fun createInitialState(savedState: Parcelable?): LoginContract.LoginState {
        return savedState as? LoginContract.LoginState ?: LoginContract.LoginState()
    }

    override fun handleEvent(event: LoginContract.LoginEvent) {
        when (event) {
            is LoginContract.LoginEvent.OnClickKakaoLoginBtn -> {
                if (currentUiState.isOnboardingCompleted) {
                    setSideEffect(LoginContract.LoginSideEffect.NavigateToTodo)
                } else {
                    Timber.d("[카카오 로그인] -> 버튼 누름")
                    kakaoAuthService.loginKakao(
                        context = event.context,
                        loginListener = { accessToken ->
                            Timber.d("[카카오 로그인] -> 카카오에서 받은 액세스 토큰 $accessToken")
                            login(accessToken)
                        },
                    )
                    setSideEffect(LoginContract.LoginSideEffect.NavigateToOnboarding)
                }
            }
        }
    }

    override fun reduceState(
        state: LoginContract.LoginState,
        reduce: LoginContract.LoginReduce,
    ): LoginContract.LoginState {
        return when (reduce) {
            is LoginContract.LoginReduce.UpdateState -> reduce.state
            is LoginContract.LoginReduce.UpdateLoginSuccess -> state.copy(loginState = !currentUiState.loginState)
        }
    }

    private fun startAnimation() {
        viewModelScope.launch {
            delay(200)
            updateState(LoginContract.LoginReduce.UpdateState(currentUiState.copy(isSloganVisible = true)))

            updateState(LoginContract.LoginReduce.UpdateState(currentUiState.copy(isBackgroundVisible = true)))

            delay(300)
            updateState(LoginContract.LoginReduce.UpdateState(currentUiState.copy(isKakaoLoginBtnVisible = true)))
        }
    }

    private fun login(accessToken: String) {
        viewModelScope.launch {
            userRepository.login(accessToken)
                .onSuccess { userEntity ->
                    updateState(
                        LoginContract.LoginReduce.UpdateState(
                            currentUiState.copy(
                                loginState = true,
                                isOnboardingCompleted = userEntity.isSignUpComplete,
                            ),
                        ),
                    )

                    saveUserInfoInLocal(
                        accessToken = BEARER + userEntity.accessToken,
                        refreshToken = BEARER + userEntity.refreshToken,
                        isLogin = true,
                    )

                    if (userEntity.isSignUpComplete) {
                        setSideEffect(LoginContract.LoginSideEffect.NavigateToTodo)
                    } else {
                        setSideEffect(LoginContract.LoginSideEffect.NavigateToOnboarding)
                    }
                }.onFailure {
                    updateState(LoginContract.LoginReduce.UpdateLoginSuccess(success = false))
                    Timber.d("[카카오 로그인] -> 서버에서 LoginViewModel login 실패")
                }
        }
    }

    private fun saveUserInfoInLocal(
        accessToken: String,
        refreshToken: String,
        isLogin: Boolean,
    ) {
        Timber.d("[로그인] 데이터스토어에 저장할래~ -> $isLogin")
        viewModelScope.launch {
            with(userDefaultRepository) {
                setAccessToken(accessToken = accessToken)
                setRefreshToken(refreshToken = refreshToken)
                setIsLogin(isLogin = isLogin)
            }
        }
    }

    companion object {
        const val BEARER = "Bearer "
    }
}
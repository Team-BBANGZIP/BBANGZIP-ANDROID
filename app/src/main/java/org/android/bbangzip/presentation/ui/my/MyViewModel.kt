package org.android.bbangzip.presentation.ui.my

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.bbangzip.domain.repository.UserRepository
import org.android.bbangzip.presentation.common.base.BaseViewModel
import org.android.bbangzip.presentation.common.util.constant.OnboardingConstants
import org.android.bbangzip.presentation.common.util.device.DeviceInfo
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val deviceInfo: DeviceInfo,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<MyContract.MyEvent, MyContract.MyState, MyContract.MyReduce, MyContract.MySideEffect>(
    savedStateHandle = savedStateHandle
) {
    override fun createInitialState(savedState: Parcelable?): MyContract.MyState {
        return savedState as? MyContract.MyState ?: MyContract.MyState()
    }

    override fun handleEvent(event: MyContract.MyEvent) {
        when (event) {
            MyContract.MyEvent.Initialize -> {
                loadInitialData()
                updateState(MyContract.MyReduce.UpdateMyAppVersion(appVersion = deviceInfo.appVersion))
            }

            MyContract.MyEvent.OnClickProfileArea -> {
                setSideEffect(MyContract.MySideEffect.NavigateToProfileEdit)
            }

            MyContract.MyEvent.OnClickScreenSetting -> {
                setSideEffect(MyContract.MySideEffect.NavigateToScreenSetting)
            }

            MyContract.MyEvent.OnClickAlarmSetting -> {
                // 현재 인터랙션 없음(알람 설정 페이지 무효)
            }

            MyContract.MyEvent.OnClickCustomerCenter -> {
                setSideEffect(MyContract.MySideEffect.OpenCustomerCenterWeb)
            }

            MyContract.MyEvent.OnClickFeedback -> {
                setSideEffect(MyContract.MySideEffect.OpenFeedbackForm)
            }

            MyContract.MyEvent.OnClickReviewApp -> {
                setSideEffect(MyContract.MySideEffect.OpenAppStoreReview)
            }

            MyContract.MyEvent.OnClickTermsOfService -> {
                setSideEffect(MyContract.MySideEffect.OpenTermsOfServiceWeb)
            }

            MyContract.MyEvent.OnClickLogoutBtn -> updateState(MyContract.MyReduce.UpdateLogoutBottomSheetVisibility(isVisible = true))
            MyContract.MyEvent.OnCancelLogoutBottomSheet -> updateState(MyContract.MyReduce.UpdateLogoutBottomSheetVisibility(isVisible = false))
            MyContract.MyEvent.OnConfirmLogout -> logout()
            MyContract.MyEvent.OnClickLogoutBottomSheetDismissRequest -> updateState(MyContract.MyReduce.UpdateLogoutBottomSheetVisibility(isVisible = false))

            MyContract.MyEvent.OnClickWithdrawalBtn -> updateState(MyContract.MyReduce.UpdateWithdrawalBottomSheetVisibility(isVisible = true))
            MyContract.MyEvent.OnCancelWithdrawalBottomSheet -> updateState(MyContract.MyReduce.UpdateWithdrawalBottomSheetVisibility(isVisible = false))
            MyContract.MyEvent.OnConfirmWithdrawal -> withdrawal()
            MyContract.MyEvent.OnClickWithdrawalBottomSheetDismissRequest -> updateState(MyContract.MyReduce.UpdateWithdrawalBottomSheetVisibility(isVisible = false))
        }
    }

    override fun reduceState(
        state: MyContract.MyState, reduce: MyContract.MyReduce
    ): MyContract.MyState {
        return when (reduce) {
            is MyContract.MyReduce.UpdateState -> reduce.state

            is MyContract.MyReduce.UpdateMyAppVersion -> state.copy(appVersion = reduce.appVersion)
            is MyContract.MyReduce.UpdateUserInfo -> state.copy(
                nickname = reduce.nickname,
                commitmentMessage = reduce.commitmentMessage,
                profileImgRes = reduce.profileImgRes // TODO 서버에서 받아와서 convert 로직 수행
            )

            is MyContract.MyReduce.UpdateLogoutBottomSheetVisibility -> state.copy(isLogoutConfirmBottomSheetVisible = reduce.isVisible)

            is MyContract.MyReduce.UpdateWithdrawalBottomSheetVisibility -> state.copy(isWithdrawalConfirmBottomSheetVisible = reduce.isVisible)
        }
    }

    private fun convertKeyToResId(
        profileImgKey: Int,
    ): Int {
        return when (profileImgKey) {
            0 -> OnboardingConstants.DEFAULT_PROFILE_IMG_RES_ID
            else -> OnboardingConstants.PROFILE_IMG_RES_IDS.indexOf(profileImgKey - 1)
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {

        }
    }

    private fun logout() {
        viewModelScope.launch {
            userRepository.logout()
                .onSuccess {
                    setSideEffect(MyContract.MySideEffect.NavigateToLogin)
                }.onFailure {
                    Timber.d("[마이페이지] 로그아웃 실패")
                }
        }
    }

    private fun withdrawal() {
        viewModelScope.launch {
            userRepository.withdraw()
                .onSuccess {
                    setSideEffect(MyContract.MySideEffect.NavigateToLogin)
                }.onFailure {
                    Timber.d("[마이페이지] 탈퇴 실패")
                }
        }
    }
}

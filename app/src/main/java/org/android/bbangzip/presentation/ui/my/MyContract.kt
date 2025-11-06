package org.android.bbangzip.presentation.ui.my

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.presentation.common.base.BaseContract

class MyContract {
    @Parcelize
    data class MyState(
        val myPageItem: List<String> = listOf("화면 설정", "알림 설정", "고객센터", "제 과제 빵점 사용법", "피드백 남기기", "앱 리뷰 남기기", "버전 정보"),
    ) : BaseContract.State, Parcelable {
        override fun toParcelable(): Parcelable = this
    }

    sealed interface MyEvent : BaseContract.Event {
        data object Initialize : MyEvent
    }

    sealed interface MyReduce : BaseContract.Reduce

    sealed interface MySideEffect : BaseContract.SideEffect
}

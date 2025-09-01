package org.android.bbangzip.presentation.ui.shared

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.R
import org.android.bbangzip.presentation.type.BreadType
import org.android.bbangzip.presentation.util.base.BaseContract

class SharedContract {
    @Parcelize
    data class SharedState(
        val breadId: Int = 1,
    ) : BaseContract.State, Parcelable {
        val breadImg: Int get() = BreadType.getImgFromId(breadId)

        override fun toParcelable(): Parcelable = this
    }

    sealed class SharedEvent : BaseContract.Event {
        data class OnClickBread(val breadId: Int) : SharedEvent()
    }

    sealed class SharedReduce : BaseContract.Reduce {
        data class SetBreadId(val breadId: Int) : SharedReduce()
    }

    sealed class SharedSideEffect : BaseContract.SideEffect
}

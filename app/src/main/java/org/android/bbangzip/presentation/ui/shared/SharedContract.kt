package org.android.bbangzip.presentation.ui.shared

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import org.android.bbangzip.presentation.util.base.BaseContract

class SharedContract {
    @Parcelize
    data class SharedState(
          val breadId : Int = 0,
    ): BaseContract.State, Parcelable {
        override fun toParcelable(): Parcelable = this
    }

    sealed class SharedEvent : BaseContract.Event {
        data class OnClickBread(val breadId: Int) : SharedEvent()
      }

    sealed class SharedReduce :BaseContract.Reduce {
        data class SetBreadId(val breadId: Int) : SharedReduce()
    }

    sealed class SharedSideEffect : BaseContract.SideEffect
}

@Serializable
object Shared

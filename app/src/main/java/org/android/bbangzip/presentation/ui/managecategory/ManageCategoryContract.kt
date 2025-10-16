package org.android.bbangzip.presentation.ui.managecategory

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.android.bbangzip.presentation.common.base.BaseContract
import org.android.bbangzip.presentation.common.model.Category

class ManageCategoryContract {
    @Parcelize
    data class ManageCategoryState(
        val categories: List<Category> =
            listOf(
                Category(id = 1, name = "아침 루틴", color = "RED1", isStopped = false),
                Category(id = 2, name = "SOPT 안드로이드 파트", color = "YELLOW1", isStopped = false),
                Category(id = 3, name = "운동", color = "BLUE1", isStopped = true),
                Category(id = 4, name = "취미 생활", color = "GREEN1", isStopped = false),
                Category(id = 5, name = "사이드 프로젝트", color = "PURPLE1", isStopped = false),
                Category(id = 9, name = "여행 계획", color = "RED2", isStopped = true),
                Category(id = 10, name = "블로그 글쓰기", color = "YELLOW2", isStopped = false),
                Category(id = 11, name = "가족", color = "BLUE2", isStopped = false),
                Category(id = 12, name = "친구 약속", color = "GREEN2", isStopped = true),
                Category(id = 13, name = "업무", color = "PURPLE2", isStopped = false),
                Category(id = 17, name = "대학 과제", color = "RED1", isStopped = false),
                Category(id = 18, name = "자격증 공부", color = "YELLOW1", isStopped = true),
                Category(id = 19, name = "새로운 기술 학습", color = "BLUE1", isStopped = false),
                Category(id = 21, name = "취준", color = "GREEN1", isStopped = false),
                Category(id = 22, name = "개발", color = "PURPLE1", isStopped = false),
                Category(id = 23, name = "휴식", color = "RED1", isStopped = false),
                Category(id = 24, name = "액티비티", color = "YELLOW1", isStopped = false),
                Category(id = 25, name = "공부", color = "GREEN1", isStopped = false),
                Category(id = 26, name = "뉴스", color = "BLUE1", isStopped = false),
            )
    ): Parcelable, BaseContract.State

    sealed interface ManageCategoryEvent: BaseContract.Event {
        data object Initialize: ManageCategoryEvent
        data object OnCategoryChipClick: ManageCategoryEvent
        data object OnTopBarTrailingIconClick: ManageCategoryEvent
        data object OnTopBarLeadingIconClick: ManageCategoryEvent
        data class OnCategoryChipDragEnd(val from: Int, val to: Int): ManageCategoryEvent
    }

    sealed interface ManageCategoryReduce: BaseContract.Reduce{
        data class UpdateCategories(val categories: List<Category>):ManageCategoryReduce
    }

    sealed interface ManageCategorySideEffect: BaseContract.SideEffect
}
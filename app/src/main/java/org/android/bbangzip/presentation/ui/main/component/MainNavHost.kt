package org.android.bbangzip.presentation.ui.main.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import org.android.bbangzip.presentation.ui.addcategory.navigation.addCategoryNavGraph
import org.android.bbangzip.presentation.ui.auth.loginNavGraph
import org.android.bbangzip.presentation.ui.editcategory.navigation.editCategoryNavGraph
import org.android.bbangzip.presentation.ui.main.MainNavigator
import org.android.bbangzip.presentation.ui.managecategory.navigation.manageCategoryNavGraph
import org.android.bbangzip.presentation.ui.my.navigation.myNavGraph
import org.android.bbangzip.presentation.ui.onboarding.onboardingNavGraph
import org.android.bbangzip.presentation.ui.profileedit.navigation.profileEditNavGraph
import org.android.bbangzip.presentation.ui.screensetting.navigation.screenSettingNavGraph
import org.android.bbangzip.presentation.ui.shared.SharedViewModel
import org.android.bbangzip.presentation.ui.splash.splashNavGraph
import org.android.bbangzip.presentation.ui.timer.navigation.timerNavGraph
import org.android.bbangzip.presentation.ui.timer.navigation.timerTodoNavGraph
import org.android.bbangzip.presentation.ui.todo.navigation.todoNavGraph

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    sharedViewModel: SharedViewModel,
    padding: PaddingValues,
) {
    NavHost(
        navController = navigator.navHostController,
        startDestination = navigator.startDestination,
    ) {
        splashNavGraph(
            navigateToLogin = navigator::navigateToLoginFromSplash,
        )

        loginNavGraph(
            navigateToTimer = navigator::navigateToTimerAfterLogin,
            navigateToOnboarding = navigator::navigateToOnboarding,
        )

        onboardingNavGraph(
            navigateToTimer = navigator::navigateToTimerAfterLogin,
            navigateToLogin = navigator::navigateToLoginAndClearStack,
        )

        timerNavGraph(
            sharedViewModel = sharedViewModel,
            navigateToTimerTodo = navigator::navigateToTimerTodo,
        )

        timerTodoNavGraph(
            navigateToTimer = navigator::navigateToTimerWithRestart,
            navigateToBack = navigator::popBackStack,
        )

        todoNavGraph(
            padding = padding,
            navigateToManageCategory = navigator::navigateToManageCategory,
            navigateToAddCategory = navigator::navigateToAddCategory,
        )

        myNavGraph(
            navigateToProfileEdit = navigator::navigateToProfileEdit,
            navigateToScreenSetting = navigator::navigateToScreenSetting,
            navigateToLogin = navigator::navigateToLoginAndClearStack
        )

        manageCategoryNavGraph(
            popBackStack = navigator::popBackStack,
            navigateToAddCategory = navigator::navigateToAddCategory,
            navigateToEditCategory = navigator::navigateToEditCategory,
        )

        addCategoryNavGraph(
            popBackStack = navigator::popBackStack,
        )

        editCategoryNavGraph(
            popBackStack = navigator::popBackStack,
        )

        profileEditNavGraph(
            navigateToBack = navigator::popBackStack,
        )

        screenSettingNavGraph(
            navigateToBack = navigator::popBackStack,
        )
    }
}

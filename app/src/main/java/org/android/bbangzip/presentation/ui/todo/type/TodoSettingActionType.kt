package org.android.bbangzip.presentation.ui.todo.type

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.android.bbangzip.R

enum class TodoSettingActionType(
    @DrawableRes val interactionIconResId: Int,
    @StringRes val actionName: Int,
) {
    START_TIME(
        interactionIconResId = R.drawable.ic_clock_default_24,
        actionName = R.string.todo_setting_start_time_action_name,
    ),
    NOTIFICATION(
        interactionIconResId = R.drawable.ic_bell_default_24,
        actionName = R.string.todo_setting_notification_action_name,
    ),
    POSTPONE(
        interactionIconResId = R.drawable.ic_move_back_default_24,
        actionName = R.string.todo_setting_move_back_action_name,
    ),
    DUPLICATE(
        interactionIconResId = R.drawable.ic_duplicate_default_24,
        actionName = R.string.todo_setting_duplicate_action_name,
    ),
    CHANGE_DATE(
        interactionIconResId = R.drawable.ic_calendar_default_24,
        actionName = R.string.todo_setting_change_date_action_name,
    ),
}
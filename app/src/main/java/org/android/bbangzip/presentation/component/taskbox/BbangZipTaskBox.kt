package org.android.bbangzip.presentation.component.taskbox

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.component.taskbox.model.TaskBoxColors
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.presentation.util.extension.formatTimeWithAmPm
import org.android.bbangzip.presentation.util.extension.noRippleClickable
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme
import java.time.LocalTime

@Composable
fun BbangZipTaskBox(
    task: String,
    categoryColor: Color,
    modifier: Modifier = Modifier,
    isCompleted: Boolean = false,
    startTime: LocalTime? = null,
    onCheckBoxClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    onDrag: () -> Unit = {},
    onDrop: () -> Unit = {},
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        CheckBox(
            isCompleted = isCompleted,
            checkedBoxColor = categoryColor,
            onCheckBoxClick = onCheckBoxClick
        )

        Gap(8)

        TaskText(
            task = task,
            modifier = Modifier.weight(1f),
            startTime = startTime
        )

        Gap(12)

        Icon(
            painter = painterResource(R.drawable.ic_meatball_menu_default_24),
            contentDescription = null,
            modifier =
                Modifier
                    .size(BbangZipTaskBoxDefaults.MENU_ICON_SIZE)
                    .noRippleClickable { onMenuClick() },
            tint = BbangZipTaskBoxDefaults.colors().menuIconColor
        )
    }
}

@Composable
private fun CheckBox(
    checkedBoxColor: Color,
    modifier: Modifier = Modifier,
    isCompleted: Boolean = false,
    onCheckBoxClick: () -> Unit = {}
){
    Box(
        modifier = Modifier.noRippleClickable{onCheckBoxClick}
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_bread_default_24),
            contentDescription = null,
            modifier = modifier.size(BbangZipTaskBoxDefaults.CHECK_BOX_SIZE),
            tint = if (isCompleted) checkedBoxColor else BbangZipTaskBoxDefaults.colors().unCheckedBoxColor
        )
        if (isCompleted) {
            Box(
                modifier = Modifier.align(Alignment.Center)
            ){
                Icon(
                    painter = painterResource(R.drawable.ic_check_default_24),
                    contentDescription = null,
                    modifier =
                        Modifier
                            .size(BbangZipTaskBoxDefaults.CHECK_ICON_SIZE),
                    tint = BbangZipTaskBoxDefaults.colors().checkIconColor
                )
            }
        }
    }
}

@Composable
private fun TaskText(
    task: String,
    modifier: Modifier = Modifier,
    startTime: LocalTime? = null,
){
    Column(
        modifier = modifier.fillMaxWidth()
    ){
        Text(
            text = task,
            style = BbangZipTaskBoxDefaults.textStyles().taskTextStyle,
            color = BbangZipTaskBoxDefaults.colors().taskTextColor
        )

        Gap(4)

        if (startTime != null) {
            Time(startTime = startTime)
        }
    }
}

@Composable
private fun Time(
    startTime: LocalTime,
    modifier: Modifier = Modifier,
) {
    val displayTime = remember(startTime){
        startTime.formatTimeWithAmPm()
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_clock_default_24),
            contentDescription = null,
            modifier =
                Modifier
                    .size(BbangZipTaskBoxDefaults.CLOCK_ICON_SIZE),
            tint = BbangZipTaskBoxDefaults.colors().timeContentColor
        )

        Gap(3)

        Text(
            text = displayTime,
            style = BbangZipTaskBoxDefaults.textStyles().timeTextStyle,
            color = BbangZipTaskBoxDefaults.colors().timeContentColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BbangZipTaskBoxPreview() {
    BBANGZIPANDROIDTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            BbangZipTaskBox(
                task = "두줄 텍스트 두줄 텍스트 두줄 텍스트 두줄 텍스트 두줄 텍스트",
                categoryColor = Color.Red
            )

            BbangZipTaskBox(
                task = "한줄 텍스트",
                categoryColor = Color.Red,
                isCompleted = true
            )

            BbangZipTaskBox(
                task = "한줄 텍스트",
                categoryColor = Color.Red,
                isCompleted = true,
                startTime = LocalTime.now()
            )
        }
    }
}

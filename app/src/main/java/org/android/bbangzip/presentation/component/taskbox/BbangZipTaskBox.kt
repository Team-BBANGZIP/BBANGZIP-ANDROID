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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.util.extension.Gap
import org.android.bbangzip.presentation.util.extension.formatTimeWithAmPm
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
            categoryColor = categoryColor
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
            modifier = Modifier.size(20.dp),
            tint = BbangZipTheme.color.secondaryStrong_F2EAE4
        )
    }
}

@Composable
private fun CheckBox(
    categoryColor: Color,
    modifier: Modifier = Modifier,
    isCompleted: Boolean = false
){
    Box {
        Icon(
            painter = painterResource(R.drawable.ic_bread_default_24),
            contentDescription = null,
            modifier = modifier.size(28.dp),
            tint = if(isCompleted) categoryColor else BbangZipTheme.color.secondaryNormal_F6F1EE
        )
        if (isCompleted) {
            Box(
                modifier = Modifier.align(Alignment.Center)
            ){
                Icon(
                    painter = painterResource(R.drawable.ic_check_default_24),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = BbangZipTheme.color.staticWhite_FFFFFF
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
            style = BbangZipTheme.typography.body2Medium,
            color = BbangZipTheme.color.labelNormal_6B6560
        )

        Gap(4)

        if (startTime != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painter = painterResource(R.drawable.ic_clock_default_24),
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = BbangZipTheme.color.labelAssistive_C9C7C5
                )

                Gap(3)

                Text(
                    text = startTime.formatTimeWithAmPm(),
                    style = BbangZipTheme.typography.label4Regular,
                    color = BbangZipTheme.color.labelAssistive_C9C7C5
                )
            }
        }
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

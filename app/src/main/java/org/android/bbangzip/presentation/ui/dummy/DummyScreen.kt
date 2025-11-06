package org.android.bbangzip.presentation.ui.dummy

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.android.bbangzip.R

@Composable
fun DummyScreen(
    dummyState: DummyContract.DummyState,
    onClickNextBtn: (String) -> Unit = {},
    viewModel: DummyViewModel = hiltViewModel(),
) {
    val drawableClass = R.drawable::class.java
    val drawables =
        remember {
            drawableClass.declaredFields.mapNotNull { field ->
                val name = field.name
                val resId =
                    try {
                        field.getInt(null)
                    } catch (e: Exception) {
                        null
                    }
                resId?.let { name to it }
            }
        }

    LazyColumn(modifier = Modifier.padding(bottom = 80.dp)) {
        items(
            drawables.filter {
                it.first != "ic_launcher_foreground" &&
                    it.first != "ic_launcher_background" &&
                    it.first != "ic_dummy_x_24"
            },
        ) { (name, resId) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier =
                    Modifier
                        .fillMaxWidth(),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = resId),
                    contentDescription = name,
                    modifier = Modifier.size(48.dp),
                    tint = Color.Unspecified,
                )

                Spacer(Modifier.width(16.dp))

                Text(
                    text = name,
                    color = Color.Black,
                )
            }
        }
    }
}

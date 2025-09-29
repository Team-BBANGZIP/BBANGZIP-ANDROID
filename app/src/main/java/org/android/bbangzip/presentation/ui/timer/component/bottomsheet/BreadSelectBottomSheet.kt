package org.android.bbangzip.presentation.ui.timer.component.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.component.bottomsheet.BbangZipBottomSheetSlot
import org.android.bbangzip.presentation.common.type.BreadType
import org.android.bbangzip.presentation.common.util.extension.Gap
import org.android.bbangzip.presentation.common.util.extension.noRippleClickable
import org.android.bbangzip.presentation.ui.timer.contract.model.BreadInfoUiState
import org.android.bbangzip.ui.theme.BbangZipTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreadSelectBottomSheet(
    currentBreadId: Int,
    breadList: List<BreadInfoUiState>,
    isBottomSheetVisible: Boolean,
    breadCount: Int,
    onDismissRequest: () -> Unit,
    onBreadSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    BbangZipBottomSheetSlot(
        isBottomSheetVisible = isBottomSheetVisible,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        contentPadding = contentPadding(top = 40.dp, start = 32.dp, end = 32.dp, bottom = 40.dp),
        title = {
            BreadSelectHeader(breadCount = breadCount)
        },
        content = {
            BreadSelectionGrid(
                currentBreadId = currentBreadId,
                breadList = breadList,
                onBreadSelect = onBreadSelect,
                modifier = Modifier,
            )
        },
    )
}

@Composable
private fun BreadCountBadge(
    breadCount: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .background(
                    color = BbangZipTheme.color.backgroundAlternative_FAF6F3,
                    shape = RoundedCornerShape(5.dp),
                )
                .padding(horizontal = 16.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.bread_sheet_subtitle, breadCount),
            color = BbangZipTheme.color.labelAlternative_A29D96,
            style = BbangZipTheme.typography.subTitle1Medium,
        )
    }
}

@Composable
private fun BreadSelectHeader(
    breadCount: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.bread_sheet_title),
            color = BbangZipTheme.color.primaryNormal_897869,
            style = BbangZipTheme.typography.title1SemiBold,
        )

        Gap(20.dp)

        BreadCountBadge(breadCount = breadCount)

        Gap(24.dp)
    }
}

@Composable
private fun BreadSelectionGrid(
    currentBreadId: Int,
    breadList: List<BreadInfoUiState>,
    onBreadSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(0.dp),
    ) {
        items(
            items = breadList,
            key = { breadInfo -> breadInfo.id },
        ) { breadInfo ->
            BreadItem(
                breadInfo = breadInfo,
                isSelected = currentBreadId == breadInfo.id,
                onBreadSelect = onBreadSelect,
            )
        }
    }
}

@Composable
private fun BreadItem(
    breadInfo: BreadInfoUiState,
    isSelected: Boolean,
    onBreadSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        BreadItemImage(
            breadInfo = breadInfo,
            isSelected = isSelected,
            onBreadSelect = onBreadSelect,
        )

        Gap(8.dp)

        BreadItemLabel(
            breadInfo = breadInfo,
        )
    }
}

@Composable
private fun BreadItemImage(
    breadInfo: BreadInfoUiState,
    isSelected: Boolean,
    onBreadSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.aspectRatio(1f),
        contentAlignment = Alignment.Center,
    ) {
        if (breadInfo.isLocked) {
            LockedBreadImage()
        } else {
            UnlockedBreadImage(
                breadInfo = breadInfo,
                onBreadSelect = onBreadSelect,
            )

            if (isSelected) {
                CheckBox(modifier = Modifier.align(Alignment.TopStart))
            }
        }
    }
}

@Composable
private fun LockedBreadImage(
    modifier: Modifier = Modifier,
) {
    Image(
        imageVector = ImageVector.vectorResource(R.drawable.ic_lock_default_40),
        contentDescription = null,
        modifier = modifier.fillMaxSize(),
    )
}

@Composable
private fun UnlockedBreadImage(
    breadInfo: BreadInfoUiState,
    onBreadSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(BreadType.getImgFromId(breadInfo.id)),
        contentDescription = null,
        modifier =
            modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(color = BbangZipTheme.color.backgroundAlternative_FAF6F3)
                .noRippleClickable { onBreadSelect(breadInfo.id) }
                .padding(vertical = 16.dp, horizontal = 7.dp),
    )
}

@Composable
private fun BreadItemLabel(
    breadInfo: BreadInfoUiState,
    modifier: Modifier = Modifier,
) {
    Text(
        text = if (breadInfo.isLocked) "???" else breadInfo.name,
        color = BbangZipTheme.color.labelNormal_6B6560,
        style = BbangZipTheme.typography.body2Medium,
        modifier = modifier,
    )
}

@Composable
fun CheckBox(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .clip(CircleShape)
                .background(
                    color = BbangZipTheme.color.primaryNormal_897869,
                ),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_check_default_24),
            contentDescription = null,
            tint = BbangZipTheme.color.staticWhite_FFFFFF,
            modifier =
                Modifier
                    .align(Alignment.Center),
        )
    }
}

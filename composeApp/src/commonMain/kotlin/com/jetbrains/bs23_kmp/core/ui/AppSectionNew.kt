package com.jetbrains.bs23_kmp.core.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bs23.msfa.R
import com.bs23.msfa.chemist.addChemist.view.toDate
import com.bs23.msfa.core.animation.AnimateFadeIn
import com.jetbrains.bs23_kmp.core.extensions.conditional
import com.jetbrains.bs23_kmp.core.theme.AppTheme
import com.jetbrains.bs23_kmp.core.util.ButtonContent
import com.jetbrains.bs23_kmp.core.util.DropdownItem
import com.jetbrains.bs23_kmp.core.util.toDisplayDate
import kotlinx.coroutines.delay
import java.util.Calendar
import java.util.Date


@Composable
fun AppSection(
    modifier: Modifier = Modifier,
    isTopPadding: Boolean = true,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier.padding(
            start = dimensionResource(id = R.dimen.padding),
            end = dimensionResource(id = R.dimen.padding),
            top = if (isTopPadding) dimensionResource(id = R.dimen.padding) else 0.dp,
            bottom = dimensionResource(id = R.dimen.padding),
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        content()
    }
}

@Composable
fun AppSubSection(
    modifier: Modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding)),
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        content()
    }
}

@Composable
fun AppSectionHeaderForCard(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.onSurface,
    @DrawableRes icon: Int? = null,
) {

    Row(
        modifier = modifier.padding(
            top = dimensionResource(id = R.dimen.vertical),
            bottom = dimensionResource(id = R.dimen.paddingSmall),
        ),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (icon != null) {
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = color
            )
            Spacer(modifier = Modifier.size(16.dp))
        }
        Text(
            text = text,
            color = color,
            style = MaterialTheme.typography.headlineSmall
        )


    }
}

@Composable
fun AppSectionHeader(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.onSurface,
    @DrawableRes icon: Int? = null,
    trailingContent: @Composable () -> Unit = {}
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = dimensionResource(id = R.dimen.vertical),
                bottom = dimensionResource(id = R.dimen.paddingSmall),
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {


        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            if (icon != null) {
                FilledTonalIconButton(
                    onClick = {},
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    )
                ) {
                    Icon(
                        modifier = Modifier.padding(8.dp),
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = color
                    )

                }
                Spacer(modifier = Modifier.size(16.dp))
            }
            Text(
                text = text,
                color = color,
                style = MaterialTheme.typography.headlineSmall
            )
        }


        // add trailing content
        trailingContent()


    }
}

@Composable
fun AppSectionTitleWithLine(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.primary,
    @DrawableRes icon: Int? = null,

    actionText: String? = null,
    action: (() -> Unit)? = null,
) {

    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = R.dimen.vertical),
                    bottom = dimensionResource(id = R.dimen.paddingSmall),
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row {
                if (icon != null && actionText == null) {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = color
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                }
                Text(
                    text = text,
                    color = color,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            actionText?.let {
                Row {
                    if (icon != null) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = icon),
                            contentDescription = null,
                            tint = color
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                    Text(
                        modifier = Modifier.clickable {
                            action?.invoke()
                        },
                        text = actionText,
                        color = color,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }


        }
        AppDivider()
    }

}

@Composable
fun AppSubSectionHeader(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.onSurface,
    icon: Int? = null,
    imageVector: ImageVector? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    )
    {

        if (icon != null) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = color
            )
            AppSpacer()
        } else if (imageVector != null) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = imageVector,
                contentDescription = null,
                tint = color
            )
            AppSpacer()
        }
        Text(
            modifier = modifier,
            text = text,
            color = color,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Medium,
                letterSpacing = TextUnit(0.8f, TextUnitType.Sp)
            )
        )
    }
}


@Composable
fun AppComponentHeaderWithAction(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes icon: Int? = null,
    onAction: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.column_padding))
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
        OutlinedIconButton(onClick = {
            onAction()
        }, modifier = Modifier.size(24.dp)) {
            if (icon != null) {
                Icon(
                    painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            } else {
                Icon(
                    Icons.Outlined.Add,
                    contentDescription = "Add Item",
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}


@Composable
fun AppSubSectionHeaderWithLoader(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes icon: Int? = null,
    isLoading: Boolean = false,
) {
//    AppSubSectionHeader(
//        text = text,
//        color = MaterialTheme.colorScheme.primary
//    )

    var sec by remember { mutableStateOf(0) }
    var visible by remember { mutableStateOf(false) }
    val done = remember { mutableStateOf(false) }
    if(!isLoading){
        LaunchedEffect(key1 = Unit, block = {
            visible = true
            done.value = true
            while (sec < 3000) {
                delay(1000)
                sec += 1000
            }
            done.value = false

            //
        })
    }

    SectionRow{
        AppSubSectionHeader(text = text, icon = icon, color = MaterialTheme.colorScheme.primary,)
        if(isLoading){
            CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
        }




//        if(done.value){
        else if(done.value){
            AnimateFadeIn {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Filled.Done,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

        }
    }

}

@Composable
fun LabelText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.onSurface,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
    )
}

@Composable
fun AppText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.onSurface,
    icon: Int? = null,
    iconTint: Color? = null
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            if (iconTint != null) AppIcon(
                modifier = Modifier.size(14.dp),
                icon = icon,
                tint = iconTint
            )
            else AppIcon(modifier = Modifier.size(16.dp), icon = icon)
        }
        Text(
            modifier = modifier.align(Alignment.CenterVertically),
            text = text,
            color = color,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
        )
    }
}

@Preview
@Composable
fun AppTextPreview() {
    Surface {
        AppText(text = "Hello", icon = R.drawable.ic_add_circle)
    }

}

@Composable
fun LabelValue(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.onSurface,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
    )
}

@Composable
fun LabelValueEndAligned(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.onSurface,
) {
    Text(
        modifier = modifier,
        textAlign = TextAlign.End,
        text = text,
        color = color,
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
    )
}


@Composable
fun LabelTextWithIcon(
    modifier: Modifier = Modifier,
    text: String,
    icon: Int? = null,
    color: Color = MaterialTheme.colorScheme.onSurface,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            AppIcon(
                modifier = Modifier.size(16.dp),
                icon = it,
                tint = color
            )
            Spacer(modifier = Modifier.size(12.dp))
        }
        LabelText(
            text = text,
            color = color
        )
    }
}

@Composable
fun SectionItem(
    modifier: Modifier = Modifier,
    text: String,
    value: String,
    color: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit = {}
) {
//    Row(
//        horizontalArrangement = Arrangement.SpaceBetween,
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp)
//            .clickable(onClick = onClick),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        LabelText(
//            text = text,
//            color = color
//        )
//        LabelValue(
//            text = value,
//        )
//    }

    SectionRow(onClick = onClick) {
        LabelText(
            modifier = Modifier
                .weight(0.66f)
                .padding(end = 8.dp),
            text = text,
            color = color,

            )
        Row(
            modifier = Modifier
                .weight(0.34f)
                .padding(start = 8.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            LabelValueEndAligned(
                text = value,
                color = color
            )
        }
//        LabelValue(
//            modifier = Modifier.weight(0.5f).padding(end = 8.dp),
//            text = value,
//            color = color
//        )
    }

//        ConstraintLayout(
//            modifier = Modifier.fillMaxWidth()
//
//        ) {
//            // create vertical guideline
//            val guideline0 = createGuidelineFromStart(0.4f)
//
//            val (textRef, valueRef) = createRefs()
//            LabelText(
//                modifier = Modifier.constrainAs(textRef) {
//                    start.linkTo(parent.start)
//                    top.linkTo(parent.top)
//                    bottom.linkTo(parent.bottom)
//                    end.linkTo(guideline0)
//                    width = Dimension.fillToConstraints
//
//                },
//                text = text,
//                color = color
//            )
//            LabelValue(
//                modifier = Modifier.constrainAs(valueRef) {
//                    end.linkTo(parent.end)
//                    top.linkTo(parent.top)
//                    bottom.linkTo(parent.bottom)
//                    start.linkTo(guideline0)
//                    width = Dimension.fillToConstraints
//
//                },
//                text = value,
//            )
//        }
}


@Composable
fun AppSelectable(
    modifier: Modifier = Modifier,
    text: String = "Select Item",
    onClick: () -> Unit = {},
    icon: Int? = null,
    actionIcon: Int? = null,
    content: @Composable () -> Unit = {}
) {
    AppCard(
        onClick = onClick,
    ) {
        SectionRow(onClick = onClick) {
//            Text(
//                text = text,
//                color = MaterialTheme.colorScheme.onSurfaceVariant,
//            )
            AppText(
                text = text,
                icon = icon,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            FilledTonalIconButton(
                onClick = onClick,
                modifier = Modifier.size(24.dp)
            ) {
                AppIcon(
                    icon = actionIcon ?: R.drawable.ic_add,
                    modifier = Modifier.size(16.dp)
                )

            }
        }
        content()
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppPart(
    modifier: Modifier = Modifier,
    text: String? = null,
    icon: Int? = null,
    onClick: () -> Unit = {},
    actionText: String? = null,
    onAction: () -> Unit = {},
    isError: Boolean = false,
    content: @Composable () -> Unit
) {
    // use AppCard with zero corner radius
    AppCard(
        modifier = modifier,
        text = text,
        icon = icon,
        onClick = onClick,
        actionText = actionText,
        onAction = onAction,
        isError = isError,
        isZeroCornerRadius = true,
        content = content
    )


}


@Composable
fun SectionRow(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable RowScope.() -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        content = content
    )
}

@Composable
fun AppActionItem(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    icon: Int? = null,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
//        modifier = Modifier.fillMaxWidth()
    ) {
        AppButton(
            modifier = modifier,
            text = text,
            icon = icon ?: R.drawable.ic_add_circle,
            onClick = onClick,
            containerColor = containerColor,
            contentColor = contentColor
        )

    }
//    Row(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp)
//            .clickable(onClick = onClick),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        LabelTextWithIcon(
//            text = text,
//            icon = icon,
//            color = color
//        )
//        Icon(
//            modifier = Modifier.size(16.dp),
//            painter = painterResource(id = R.drawable.ic_add),
//            contentDescription = null,
//            tint = color
//        )
//    }
}

@Composable
fun SectionIconItem(
    modifier: Modifier = Modifier,
    text: String,
    value: String = "",
    icon: Int? = null,
    color: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LabelTextWithIcon(
            text = text,
            icon = icon,
            color = color
        )

        LabelValue(
            text = value,
        )
    }
}

@Composable
fun AppButtonContent(
    text: String,
    imageVector: ImageVector? = null,
    @DrawableRes drawable: Int? = null
) {
    if (drawable != null) {
        Icon(
            painter = painterResource(id = drawable),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        AppSpacer()

    } else if (imageVector != null) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }
    Text(text = text)
}

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: Int? = null,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
    ) {
        AppButtonContent(text = text, drawable = icon)
    }
}

@Composable
fun AppOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: Int? = null,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
    ) {
        AppButtonContent(text = text, drawable = icon)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    text: String? = null,
    icon: Int? = null,
    onClick: () -> Unit = {},
    actionText: String? = null,
    onAction: () -> Unit = {},
    isError: Boolean = false,
    isZeroCornerRadius: Boolean = false,
    content: @Composable () -> Unit
) {
    val errorColor = MaterialTheme.colorScheme.error
    val errorShape = MaterialTheme.shapes.small

    ElevatedCard(
        modifier = modifier
            .padding(vertical = dimensionResource(id = R.dimen.vertical))
            .conditional(isError) {
                border(
                    width = 1.dp,
                    color = errorColor,
                    shape = errorShape
                )
            },
        onClick = onClick,
        shape = if (isZeroCornerRadius) RoundedCornerShape(0.dp) else CardDefaults.elevatedShape
    ) {
        text?.let {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                shape = if (isZeroCornerRadius) RoundedCornerShape(0.dp) else CardDefaults.elevatedShape
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
//                    AppSubSectionHeader(
//                        text = it,
//                        icon = icon,
//                        color = MaterialTheme.colorScheme.onSecondaryContainer,
//                    )


                    SectionRow {
                        AppSubSectionHeader(text = text, icon = icon, color = MaterialTheme.colorScheme.onSecondaryContainer,)

                        if (actionText != null) {
                            Text(
                                modifier = Modifier.clickable {
                                    onAction()
                                },
                                text = actionText,
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    letterSpacing = TextUnit(0.8f, TextUnitType.Sp),
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }

                    }

                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            content()

        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AppCard(
//    modifier: Modifier = Modifier,
//    text: String? = null,
//    icon: Int? = null,
//    onClick: () -> Unit = {},
//    actionText: String? = null,
//    onAction: () -> Unit = {},
//    isError: Boolean = false,
//    content: @Composable () -> Unit
//) {
//    val errorColor = MaterialTheme.colorScheme.error
//    val errorShape = MaterialTheme.shapes.small
//
//    ElevatedCard(
//        modifier = modifier
//            .padding(vertical = dimensionResource(id = R.dimen.vertical))
//            .conditional(isError) {
//                border(
//                    width = 1.dp,
//                    color = errorColor,
//                    shape = errorShape
//                )
//            },
//        onClick = onClick,
//    ) {
//        text?.let {
//            Card(
//                colors = CardDefaults.cardColors(
//                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.72f),
//                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
//                ),
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    verticalArrangement = Arrangement.spacedBy(4.dp)
//                ) {
////                    AppSubSectionHeader(
////                        text = it,
////                        icon = icon,
////                        color = MaterialTheme.colorScheme.onSecondaryContainer,
////                    )
//
//
//                    SectionRow {
//                        AppSubSectionHeader(text = text, icon = icon, color = MaterialTheme.colorScheme.onSecondaryContainer,)
//
//                        if (actionText != null) {
//                            Text(
//                                modifier = Modifier.clickable {
//                                    onAction()
//                                },
//                                text = actionText,
//                                color = MaterialTheme.colorScheme.primary,
//                                style = MaterialTheme.typography.titleSmall.copy(
//                                    letterSpacing = TextUnit(0.8f, TextUnitType.Sp),
//                                    fontWeight = FontWeight.Medium
//                                )
//                            )
//                        }
//
//                    }
//
//                }
//            }
//        }
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            content()
//
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCardWithoutBodyPadding(
    modifier: Modifier = Modifier,
    text: String? = null,
    icon: Int? = null,
    onClick: () -> Unit = {},
    actionText: String? = null,
    onAction: () -> Unit = {},
    isError: Boolean = false,
    content: @Composable () -> Unit
) {
    val errorColor = MaterialTheme.colorScheme.error
    val errorShape = MaterialTheme.shapes.small

    ElevatedCard(
        modifier = modifier
            .padding(vertical = dimensionResource(id = R.dimen.vertical))
            .conditional(isError) {
                border(
                    width = 1.dp,
                    color = errorColor,
                    shape = errorShape
                )
            },
        onClick = onClick,
    ) {
        text?.let {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.72f),
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    SectionRow {
                        AppSubSectionHeader(text = text, icon = icon, color = MaterialTheme.colorScheme.onSecondaryContainer,)

                        if (actionText != null) {
                            Text(
                                modifier = Modifier.clickable {
                                    onAction()
                                },
                                text = actionText,
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    letterSpacing = TextUnit(0.8f, TextUnitType.Sp),
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }

                    }

                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            content()
        }
    }
}

@Composable
fun AppOutlinedCard(
    modifier: Modifier = Modifier,
    text: String? = null,
    icon: Int? = null,
    actionIcon: Int? = null,
    onActionClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    OutlinedCard(
        modifier = modifier.padding(vertical = dimensionResource(id = R.dimen.vertical)),
        // change color of the border of material 3 outlined card
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))

    ) {
        text?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    AppSubSectionHeader(
                        text = it,
                        icon = icon,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    actionIcon?.let {
                        IconButton(
                            onClick = onActionClick,
                            modifier = Modifier.size(20.dp)
                        ) {
                            AppIconButton(
                                icon = it,
                                onClick = onActionClick
                            )
                        }
                    }
                }
            }
            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            content()

        }
    }
}

@Composable
fun AppFilledCard(
    modifier: Modifier = Modifier,
    text: String? = null,
    icon: Int? = null,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.padding(vertical = dimensionResource(id = R.dimen.vertical)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )

    ) {
        text?.let {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.72f),
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    AppSubSectionHeader(
                        text = it,
                        icon = icon,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            content()

        }
    }
}

@Composable
fun AppSectionCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.padding(vertical = dimensionResource(id = R.dimen.vertical)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            content()

        }
    }
}


@Composable
fun AppList(
    modifier: Modifier = Modifier,
    list: List<Any>,
) {
//    LazyColumn(
//        modifier = modifier,
//    ) {
//        items(list.size) { index ->
//            AppListItem(
//                item = list[index].toString()
//            )
//        }
//    }

    if (list.isEmpty()) {
        AppSectionDividerWithText(text = "No Data")
    } else {
        list.forEach {
            AppInsetListItem(
                item = it.toString()
            )
        }
    }
}

@Composable
fun AppInsetListItem(
    item: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
//            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = item,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


@Composable
fun AppListRemovable(
    modifier: Modifier = Modifier,
    list: List<DropdownItem>,
    onRemove: (DropdownItem) -> Unit,
    isRemovable: (DropdownItem) -> Boolean = { true }
) {
    if(list.isNotEmpty()){
        Column(modifier = Modifier.clip(MaterialTheme.shapes.small)) {
            list.forEachIndexed { idx, item ->
                AppListItemRemovable(
                    item = item,
                    onRemove = onRemove,
                    isAlternateColor = idx % 2 == 0,
                    isRemovable = isRemovable
                )
            }
        }
    }

//    if (list.isNotEmpty()) {
//        AppSpacer()
//    }
}

@Composable
fun AppIcon(
    modifier: Modifier = Modifier,
    icon: Int? = null,
    imageVector: ImageVector? = null,
    tint: Color = MaterialTheme.colorScheme.onSurface,
) {
//    Icon(
//        modifier = modifier,
//        painter = painterResource(id = icon),
//        contentDescription = null,
//        tint = tint
//    )

    if (icon != null) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = modifier,
            tint = tint,
        )
        AppSpacer()

    } else if (imageVector != null) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = modifier,
            tint = tint,
        )
    }
}

@Composable
fun AppIconButton(
    modifier: Modifier = Modifier,
    icon: Int? = null,
    tint: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit
) {
    if (icon != null) {
        IconButton(
            onClick = onClick
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = modifier,
                tint = tint,
            )
        }
    }

}

@Composable
fun AppBadge(
    modifier: Modifier = Modifier,
    text: String,
    color: Color? = null,
    textColor: Color? = null,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier.clickable {
            onClick()
        },
        shape = RoundedCornerShape(4.dp),
        color = color ?: MaterialTheme.colorScheme.secondaryContainer,
    ) {
        Text(
            modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            text = text,
            style = MaterialTheme.typography.labelSmall.copy(
                color = textColor ?: MaterialTheme.colorScheme.onSecondaryContainer
            )
        )
    }
}

@Composable
fun AppOutlineBadge(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color? = null,
    @DrawableRes icon: Int? = null,
) {
    Surface(
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically){
            if(icon != null){
                Icon(
                    modifier = Modifier
                        .padding(horizontal = 4.dp, vertical = 1.dp)
                        .size(16.dp),
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = textColor ?: MaterialTheme.colorScheme.onSecondaryContainer
                )
//                Spacer(modifier = Modifier.size(4.dp))
            }

            Text(
                modifier = if(icon == null) modifier.padding(horizontal = 8.dp, vertical = 4.dp) else modifier.padding(end = 8.dp, top = 4.dp, bottom = 4.dp),
                text = text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = textColor ?: MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
        }
    }
}

@Composable
fun AppBadgeWithLabel(
    modifier: Modifier = Modifier,
    label: String = "Label",
    content: String = "Content",
    contentColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    textColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
) {
    Surface(
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(
            width = 1.dp,
            color = contentColor,
        )
    ) {
        Row {
            Column(verticalArrangement = Arrangement.Center) {
                Surface(color = contentColor) {
                    Text(
                        modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        text = label,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = textColor
                        )
                    )
                }
            }
            Text(
                modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                text = content,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = textColor
                )
            )
        }
    }
}

@Preview
@Composable
fun AppBadgeWithLabelPreview() {
    AppTheme {
        AppBadgeWithLabel()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDate(
    title: String,
    date: Date?,
    onDateSelected: (Date) -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }

//    OutlinedTextField(
//        value = date?.toDisplayDate() ?: "",
//        onValueChange = {},
//        label = { Text(text = title) },
//        modifier = Modifier
//            .clickable { openDialog.value = true }
//            .fillMaxWidth()
//            .padding(vertical = 4.dp),
//        trailingIcon = {
//            IconButton(onClick = { openDialog.value = true }) {
//                Icon(
//                    imageVector = Icons.Filled.DateRange,
//                    contentDescription = null,
//                )
//            }
//        },
//        readOnly = true,
//        placeholder = { Text(text = title) }
//    )
    AppSelectable(
        text = title,
        icon = R.drawable.ic_calendar,
        onClick = { openDialog.value = true }
    ) {
        if (date != null) {
            AppFilledCard {
                SectionItem(text = date.toDisplayDate(), value = "")
            }
        }


    }
    AppDatePicker(
        openDialog = openDialog.value,
        onDismissRequest = { openDialog.value = false }) { datePickerState ->
        datePickerState.selectedDateMillis?.toDate()?.let { date ->
            onDateSelected(date)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDateCustomDateBlock(
    title: String,
    date: Date?,
    onDateSelected: (Date) -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }

//    OutlinedTextField(
//        value = date?.toDisplayDate() ?: "",
//        onValueChange = {},
//        label = { Text(text = title) },
//        modifier = Modifier
//            .clickable { openDialog.value = true }
//            .fillMaxWidth()
//            .padding(vertical = 4.dp),
//        trailingIcon = {
//            IconButton(onClick = { openDialog.value = true }) {
//                Icon(
//                    imageVector = Icons.Filled.DateRange,
//                    contentDescription = null,
//                )
//            }
//        },
//        readOnly = true,
//        placeholder = { Text(text = title) }
//    )
    AppSelectable(
        text = title,
        icon = R.drawable.ic_calendar,
        onClick = { openDialog.value = true }
    ) {
        if (date != null) {
            AppFilledCard {
                SectionItem(text = date.toDisplayDate(), value = "")
            }
        }


    }
    AppDatePicker(
        openDialog = openDialog.value,
        //create date object of 26/12/2023
        blockedDate = Calendar.getInstance().apply { set(2023, 11, 25) }.time,

        onDismissRequest = { openDialog.value = false }) { datePickerState ->
        datePickerState.selectedDateMillis?.toDate()?.let { date ->
            onDateSelected(date)
        }
    }

}


@Composable
fun AppFullScreenDialog() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Title",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Description",
                style = MaterialTheme.typography.bodyMedium
            )
            AppButton(
                text = "Button",
                onClick = {}
            )
        }

    }
}

@Composable
fun AppStatus(
    modifier: Modifier = Modifier,
    text: String,
    color: Color? = null,
    textColor: Color? = null,
    icon: Int? = null,
) {
//    Surface(
//        shape = RoundedCornerShape(4.dp),
//        color = color ?: MaterialTheme.colorScheme.secondaryContainer,
//    ) {
//        Row{
//            AppIcon()
//            Text(
//                modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp),
//                text = text,
//                style = MaterialTheme.typography.labelSmall.copy(
//                    color = textColor ?: MaterialTheme.colorScheme.onSecondaryContainer
//                )
//            )
//        }
//
//    }

    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = color ?: MaterialTheme.colorScheme.secondaryContainer,
            contentColor = textColor ?: MaterialTheme.colorScheme.onSecondaryContainer
        ),
    ) {
        Row(
            modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        )
        {
            AppIcon(
                modifier = Modifier.size(16.dp),
                icon = icon, tint = textColor ?: MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = textColor ?: MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
        }
    }
}


@Composable
fun AppTonalCard(
    modifier: Modifier = Modifier,
    key: String,
    value: String,
) {

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = modifier.defaultMinSize(minWidth = 40.dp)
            ) {
                Text(
                    modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    text = value,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
            }
            Text(
                modifier = modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .align(Alignment.CenterHorizontally),
                text = key,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.secondary
                ),
                textAlign = TextAlign.Center
            )
        }

    }

}

@Composable
fun AppTonalLargeCard(
    modifier: Modifier = Modifier,
    key: String,
    value: String,
) {

    ElevatedCard {
        AppLargeCardContent(modifier = modifier, key = key, value = value)
    }
}

@Composable
fun AppLargeCardContent(
    modifier: Modifier = Modifier,
    key: String,
    value: String,
) {
    Column(modifier = modifier.width(80.dp)) {
        ConstraintLayout {
            val (valueText, keyText) = createRefs()
            Text(
                textAlign = TextAlign.Center,
                modifier = modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .constrainAs(valueText) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                text = value,
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
            Text(
                textAlign = TextAlign.Center,
                modifier = modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .constrainAs(keyText) {
                        top.linkTo(valueText.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                text = key,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppChipRow(
    filterChip: List<Any>,
    selectedFilterChip: List<Any>,
    onSelectedFilterChipChange: (Any) -> Unit
) {
    val localCheckedItems: SnapshotStateList<DropdownItem> =
        remember { mutableStateListOf<DropdownItem>() }

    // initialize chip row
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
        // populate chip row
        filterChip.forEachIndexed { idx, chip ->

            ElevatedFilterChip(
                modifier = Modifier.padding(end = 8.dp),
                selected = selectedFilterChip.contains(chip),
                onClick = { onSelectedFilterChipChange(chip) },
                label = { Text(chip.toString()) },
                leadingIcon = if (selectedFilterChip.contains(chip)) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Localized Description",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                }
            )
        }
    }
}

@Composable
fun AppFAB(
    text: String,
    onClick: () -> Unit,

    ) {
    ExtendedFloatingActionButton(
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        onClick = onClick,
        modifier = Modifier
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add",
            tint = MaterialTheme.colorScheme.onTertiaryContainer
        )
        AppSpacer()
        androidx.compose.material.Text(
            text = text,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
    }
}


@Composable
fun AppOutlinedText(
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    label: String = "",
    value: String = "",
    onValueChange: (String) -> Unit

) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        label = { Text(label) },
        value = value,
        onValueChange = onValueChange,
        isError = isError
    )
}

@Composable
fun AppAction(
    modifier: Modifier = Modifier,
    onAction: () -> Unit,
    onCancel: () -> Unit,
    text: String = "Action"
) {
    Row(
        modifier = modifier
            .fillMaxWidth()

    ) {
        OutlinedButton(
            modifier = Modifier.weight(1.25f),
            onClick = onCancel
        ) {
            ButtonContent(text = "Cancel", icon = R.drawable.ic_reject)
        }

        AppSpacer()

        AppButton(
            modifier = Modifier.weight(2f),
            text = text,
            onClick = onAction

        )

    }
}

@Composable
fun AppColumn(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        content()
    }
}


@Composable
fun AppDemo() {
    Column {
        AppSection {
            AppTonalLargeCard(key = "Valuefasdfasdfasdfasdf", value = "64")
            AppTonalCard(key = "Value", value = "23")
            AppSectionTitleWithLine(text = "Section Title", action = { }, actionText = "Add")
            AppCard {
                AppSubSectionHeader(text = "Master")
                AppSectionCard {
                    SectionItem(text = "Key 1", value = "123")
                    SectionItem(text = "Key 2", value = "456")
                }

                AppSubSectionHeader(text = "Master")
                AppSectionCard {
                    AppList(
                        list = listOf("Data 1", "Data 2", "Data 3")
                    )
                }
            }
        }
        AppSection {
            AppSectionTitleWithLine(text = "Section Title", action = { }, actionText = "Add")
            SectionItem(text = "Basic", value = "123")
            SectionItem(text = "Advanced", value = "456")

            AppSectionCard {
                SectionItem(text = "Basic", value = "123")
                SectionItem(text = "Advanced", value = "456")
            }

            AppHeaderCard {
                SectionItem(text = "Chemist Name", value = "ABC Chemist")
                SectionItem(text = "Area", value = "Dhaka 4")
                SectionIconItem(text = "Zone", value = "Zone 1")

                AppSectionDivider()

                SectionIconItem(text = "abc@gmail.com", value = "", icon = R.drawable.ic_email)
                SectionIconItem(
                    text = "215, Block B, Uttara, Dhaka -1206",
                    value = "",
                    icon = R.drawable.ic_phone
                )

            }
        }


        AppSection {
            AppSectionHeader(text = "Section Header")
            SectionBody()
            MySectionBody()
//                SectionBody2()
        }
        AppSection {
            AppSectionHeader(text = "Header Icon", icon = R.drawable.doctor)
            SectionBody()
            SectionBody2()

        }

    }


}

@Composable
fun MySectionBody() {
    AppCard {
        AppSubSectionHeader(text = "Sub Section Header")
        AppList(
            list = listOf("Item 1")
        )
    }
}

@Composable
fun SectionBody() {
    AppCard {
        AppSubSectionHeader(text = "Sub Section Header")
        AppList(
            list = listOf("Item 1", "Item 2", "Item 3")
        )
//        Row(modifier = Modifier
//            .padding(8.dp)){
//
//            OutlinedButton(
//                onClick = {}
//            ) {
//                AppButtonContent(text = "Button 1")
//            }
//            AppButton(
//                modifier = Modifier.weight(1f),
//                text = "Button 2",
//                onClick = {}
//            )
//        }
    }
}


@Composable
fun SectionBody2() {
    AppCard {
        AppSubSectionHeader(text = "Sub Section Header with icon", icon = R.drawable.chemist)
        AppList(
            list = listOf("Item 1", "Item 2", "Item 3")
        )
        AppSectionDivider()
        AppSubSectionHeader(
            text = "Sub section with color",
            color = MaterialTheme.colorScheme.primary
        )
        AppList(
            list = listOf("Item 1", "Item 2", "Item 3")
        )
        AppSectionDividerWithText("Thursday, 12 August 2021")


        AppSubSection {
            LabelText(text = "Intended for")
            SectionItem(text = "cat", value = "1")
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHeaderCard(
    text: String = "Order",
    @DrawableRes icon: Int? = null,
    content: @Composable () -> Unit
) {
    ElevatedCard(
        onClick = { /* Do something */ },
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            AppSectionHeaderForCard(
                modifier = Modifier.padding(16.dp),
                text = text,
                icon = icon ?: R.drawable.order
            )
            AppDivider()
            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding))
            ) {
                content()
            }
        }
    }
}

@Composable
fun AppSuggestionChip(
    text: String,
    onClick: () -> Unit = {}
    ){
    SuggestionChip(
        onClick = onClick,
    border = SuggestionChipDefaults.suggestionChipBorder(
        borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)),
        label = {
            Text(text = text)
        },
    )
}

@Composable
fun AppItemCardLite(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f),
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
    ) {
        Column(
            modifier = modifier.padding(dimensionResource(id = R.dimen.padding))
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppDemoPreview() {
    AppTheme {
        AppDemo()
    }
}


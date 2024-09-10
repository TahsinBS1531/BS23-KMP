package com.jetbrains.bs23_kmp.core.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.jetbrains.bs23_kmp.core.extensions.conditional
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCardWithLoader(
    modifier: Modifier = Modifier,
    text: String? = null,
    icon: Int? = null,
    onClick: () -> Unit = {},
    actionText: String? = null,
    onAction: () -> Unit = {},
    isError: Boolean = false,
    isLoading: Boolean = false,
    content: @Composable () -> Unit
) {
    val errorColor = MaterialTheme.colorScheme.error
    val errorShape = MaterialTheme.shapes.small

    var visible by remember { mutableStateOf(false) }
    val done = remember { mutableStateOf(false) }

    ElevatedCard(
        modifier = modifier
//            .padding(vertical = dimensionResource(id = R.dimen.vertical))
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
//                    AppSubSectionHeader(
//                        text = it,
//                        icon = icon,
//                        color = MaterialTheme.colorScheme.onSecondaryContainer,
//                    )


                    var sec by remember {
                        mutableStateOf(0)
                    }

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



                    SectionRow {
                        AppSubSectionHeader(text = text, icon = icon, color = MaterialTheme.colorScheme.onSecondaryContainer,)

                        if(isLoading){
                            CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                        } else if (actionText != null) {
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

                        if(done.value){
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
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
//            content()


            val density = LocalDensity.current
            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically {
                    // Slide in from 40 dp from the top.
                    with(density) { -40.dp.roundToPx() }
                } + expandVertically(
                    // Expand from the top.
                    expandFrom = Alignment.Top
                ) + fadeIn(
                    // Fade in with the initial alpha of 0.3f.
                    initialAlpha = 0.3f
                ),
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {
                content()
            }

        }
    }
}


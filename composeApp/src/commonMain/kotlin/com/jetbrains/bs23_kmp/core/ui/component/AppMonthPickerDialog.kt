package com.jetbrains.bs23_kmp.core.ui.component

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jetbrains.bs23_kmp.core.ui.AppFilledCard
import com.jetbrains.bs23_kmp.core.ui.AppSelectable
import com.jetbrains.bs23_kmp.core.ui.AppSpacer
import com.jetbrains.bs23_kmp.core.ui.AppText
import com.jetbrains.bs23_kmp.core.util.ButtonContent
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AppMonthMonthPicker(
    currentMonth: Int,
    currentYear: Int,

    confirmButtonCLicked: (Int, Int) -> Unit,
    cancelClicked: () -> Unit
) {

    val visible = remember { mutableStateOf(false) }


    val months = listOf(
        "JAN",
        "FEB",
        "MAR",
        "APR",
        "MAY",
        "JUN",
        "JUL",
        "AUG",
        "SEP",
        "OCT",
        "NOV",
        "DEC"
    )

    var month by remember {
        mutableStateOf(months[currentMonth])
    }

    var year by remember {
        mutableStateOf(currentYear)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    AppSelectable(
        text = "Select Month",
//        icon = R.drawable.ic_calendar,
        onClick = {
            visible.value = true
//            confirmButtonCLicked(months.indexOf(month), year)
        }
    ){
        AppFilledCard {
            AppText(text = "$month, $year")
        }

    }

    if (visible.value) {
        Dialog(
            content = {
                Card{
                    Column(
                        modifier = Modifier
//                            .requiredHeight(LocalConfiguration.current.screenHeightDp.dp * 0.90f)
//                            .requiredWidth(LocalConfiguration.current.screenWidthDp.dp * 0.96f)
                            .padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally

                        ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                modifier = Modifier
                                    .size(35.dp)
                                    .rotate(90f)
                                    .clickable(
                                        indication = null,
                                        interactionSource = interactionSource,
                                        onClick = {
                                            year--
                                        }
                                    ),
                                imageVector = Icons.Rounded.KeyboardArrowDown,
                                contentDescription = null
                            )

                            Text(
                                modifier = Modifier.padding(horizontal = 20.dp),
                                text = year.toString(),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Icon(
                                modifier = Modifier
                                    .size(35.dp)
                                    .rotate(-90f)
                                    .clickable(
                                        indication = null,
                                        interactionSource = interactionSource,
                                        onClick = {
                                            year++
                                        }
                                    ),
                                imageVector = Icons.Rounded.KeyboardArrowDown,
                                contentDescription = null
                            )

                        }


                        Card(
                            modifier = Modifier
                                .padding(30.dp)
                                .fillMaxWidth(),
//                        elevation = 0.dp
                        ) {

//                            FlowRow(
//                                modifier = Modifier.fillMaxWidth(),

//                            mainAxisSpacing = 16.dp,
//                            crossAxisSpacing = 16.dp,
//                            mainAxisAlignment = MainAxisAlignment.Center,
//                            crossAxisAlignment = FlowCrossAxisAlignment.Center
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(minSize = 70.dp)


                            ) {

                                items(months.size) { idx ->
                                    val it = months[idx]
                                    Box(
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clickable(
                                                indication = null,
                                                interactionSource = interactionSource,
                                                onClick = {
                                                    month = it
                                                }
                                            )
                                            .background(
                                                color = Color.Transparent
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {

                                        val animatedSize by animateDpAsState(
                                            targetValue = if (month == it) 60.dp else 0.dp,
                                            animationSpec = tween(
                                                durationMillis = 500,
                                                easing = LinearOutSlowInEasing
                                            )
                                        )

                                        Box(
                                            modifier = Modifier
                                                .size(animatedSize)
                                                .background(
                                                    color = if (month == it) MaterialTheme.colorScheme.primary else Color.Transparent,
                                                    shape = CircleShape
                                                )
                                        )

                                        Text(
                                            text = it,
                                            color = if (month == it)  MaterialTheme.colorScheme.onPrimary else  MaterialTheme.colorScheme.onSurface,
                                            fontWeight = FontWeight.Medium
                                        )

                                    }
                                }

                            }

                            AppSpacer()

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ){

                                TextButton(onClick = {
                                    visible.value = false
                                }){
                                    ButtonContent(text = "Cancel")
                                }

                                Button(onClick = {
                                    confirmButtonCLicked(months.indexOf(month), year)
                                    visible.value = false
                                }){
                                    ButtonContent(text = "Confirm")
                                }
                            }

                        }

                    }
                }

            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,

                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = {

            },
        )

    }

}

@Preview
@Composable
fun AppMonthPickerPreview() {
    AppMonthMonthPicker(
        currentMonth = 0,
        currentYear = 2022,
        confirmButtonCLicked = { _, _ -> },
        cancelClicked = { }
    )
}
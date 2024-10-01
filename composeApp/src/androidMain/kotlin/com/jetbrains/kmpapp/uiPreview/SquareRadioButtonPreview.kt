package com.jetbrains.kmpapp.uiPreview
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.material3.Button
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.SegmentedButton
//import androidx.compose.material3.SegmentedButtonDefaults
//import androidx.compose.material3.SegmentedButtonDefaults.Icon
//import androidx.compose.material3.SingleChoiceSegmentedButtonRow
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.jetbrains.bs23_kmp.core.theme.AppTheme
//import com.jetbrains.bs23_kmp.core.ui.ItemIcon
//import com.jetbrains.bs23_kmp.core.ui.ItemText
//import com.jetbrains.bs23_kmp.core.ui.MaterialButtonGroup
//import com.jetbrains.bs23_kmp.core.ui.MaterialButtonGroupItem
//import com.jetbrains.bs23_kmp.core.ui.SegmentedButtonItem
//import com.jetbrains.bs23_kmp.core.ui.SegmentedButtons
//import com.jetbrains.bs23_kmp.core.ui.SquareRadioButton
//import com.jetbrains.kmpapp.R
//import kmp_app_template.composeapp.generated.resources.Res
//import kmp_app_template.composeapp.generated.resources.baseline_check_circle_outline_24
//import org.jetbrains.compose.resources.painterResource
//
//
//@Preview
//@Composable
//fun SquareRadioButtonPreview() {
//    AppTheme{
//        var selected by remember { mutableStateOf(false) }
//
//        Surface {
//            Column(modifier = Modifier.fillMaxWidth()) {
//                Button(onClick = {}) {
//                    Text("test")
//                }
//                SquareRadioButton(selected = selected, onClick = { selected = !selected })
//
//            }
//        }
//    }
//}
//
//
//@Preview
//@Composable
//fun MaterialButtonGroupPreview() {
//    MaterialTheme {
//        MaterialButtonGroup {
//            MaterialButtonGroupItem(onClick = { /* Handle click */ }) {
//                Text("Button 1")
//            }
//            MaterialButtonGroupItem(onClick = { /* Handle click */ }) {
//                Text("Button 2")
//            }
//            MaterialButtonGroupItem(onClick = { /* Handle click */ }) {
//                Text("Button 3")
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Preview
//@Composable
//fun SegmentedMaterialButton(){
//    var selectedIndex by  remember { mutableIntStateOf(0) }
//
//
//
//    MaterialTheme {
//        Column(modifier = Modifier.fillMaxWidth()) {
//            SegmentedButtons {
//                SegmentedButtonItem(
//                    selected = selectedIndex == 0,
//                    onClick = { selectedIndex = 0 },
//                    label = { Text("Selected") },
////                    icon = {
////                        androidx.compose.material3.Icon(
////                            painter = painterResource(resource = Res.drawable.baseline_check_circle_outline_24),
////                            contentDescription = null
////                        )
////                    }
//                )
//                SegmentedButtonItem(
//                    selected = selectedIndex == 1,
//                    onClick = { selectedIndex = 1 },
//                    label = { Text("Option 1") }
//
//                )
//                SegmentedButtonItem(
//                    selected = selectedIndex == 2,
//                    onClick = { selectedIndex = 2 },
//                    label = { ItemText(text = "Option 2",) },
//
//
//                    )
//            }
//
////            SingleChoiceSegmentedButtonRow {
////                SegmentedButton(
////                    selected = selectedIndex == 0,
////                    onClick = { selectedIndex = 0 },
////                    label = { Text("Selected") },
////                    shape = MaterialTheme.shapes.extraLarge
////                )
////                SegmentedButton(
////                    selected = selectedIndex == 0,
////                    onClick = { selectedIndex = 0 },
////                    label = { Text("Selected") },
////                    shape = MaterialTheme.shapes.extraLarge
////                )
////            }
//
//            var selectedIndex by remember { mutableStateOf(0) }
//            val options = listOf("Day", "Month", "Week")
//            SingleChoiceSegmentedButtonRow {
//                options.forEachIndexed { index, label ->
//                    SegmentedButton(
//                        shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
//                        onClick = { selectedIndex = index },
//                        selected = index == selectedIndex
//                    ) {
//                        Text(label)
//                    }
//                }
//            }
//        }
//    }
//}

package com.jetbrains.bs23_kmp.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jetbrains.bs23_kmp.core.util.ButtonContent
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun FilterChipInstance(
    text: String,
    isSelected: Boolean,
    onSelectedChange: (Boolean) -> Unit
) {
    ElevatedFilterChip(
        selected = isSelected,
        onClick = { onSelectedChange(!isSelected) },
        label = { Text(text) },
        leadingIcon = if (isSelected) {
            {
                AppIcon(
                    imageVector = Icons.Filled.Done,
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        }
    )
}
@Composable
fun FilterPlaceHolder(
    onFilterCleared: () -> Unit,
    onFilterClosed: () -> Unit,
    content: @Composable () -> Unit
){
    // box with a clear button

    AppSection{
       AppOutlinedCard(
           text = "Filter",
//              icon = R.drawable.outline_filter_alt_24,
//            actionIcon = R.drawable.ic_close,
              onActionClick = onFilterClosed,
       )
        {
//           Row(
//               modifier = Modifier.fillMaxWidth(),
//               horizontalArrangement = Arrangement.SpaceBetween,
//               verticalAlignment = Alignment.CenterVertically
//           ) {
//
//               AppText(
//                   text = "Filter",
//                   icon = R.drawable.outline_filter_alt_24,
//               )
//
//               Row(verticalAlignment = Alignment.CenterVertically) {
//                   TextButton(
//                       onClick = onFilterCleared
//                   ) {
//                       ButtonContent(text = "Clear Filter")
//                   }
//                   AppIconButton(
//                       icon = R.drawable.ic_close,
//                   ) {
//                       onFilterClosed()
//                   }
//               }
//
//           }
           content()
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()

            ){
                OutlinedButton(
                    onClick = onFilterCleared
                ) {
                    ButtonContent(text = "Clear Filter")
                }
            }

       }

    }
}



@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> AppFilter(
    text: String = "Filter",
    items: List<T>,
    selections: List<T>,
    onItemSelect: (List<T>) -> Unit,
    isSingleSelect: Boolean = false,
){
    Column{
        Row{
            Text(text = text)
        }
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items.forEach { item ->

                FilterChipInstance(
                    text = item.toString(),
                    isSelected = selections.contains(item),
                    onSelectedChange = {
                        if(isSingleSelect){
                            onItemSelect(listOf(item))
                        }
                        else{
                            val newSelections = selections.toMutableList()
                            if(it){
                                newSelections.add(item)
                            }
                            else{
                                newSelections.remove(item)
                            }
                            onItemSelect(newSelections)
                        }
                    }
                )
            }
        }
    }

}

@Preview
@Composable
fun TestFilter(){
    MaterialTheme{
        Surface{
            FilterPlaceHolder(onFilterCleared = {}, onFilterClosed = {}) {
                AppFilter(
                    items = listOf("A", "B", "C"),
                    selections = listOf("A"),
                    onItemSelect = {

                    }
                )
            }
        }

    }
}


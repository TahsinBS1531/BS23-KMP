package com.jetbrains.bs23_kmp.core.ui

import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AppInsetListItem(
    text: String,
    isAlternateColor: Boolean = false,
){
    ListItem(
        headlineContent = {
            LabelText(
                text = text,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        },
        tonalElevation = if(isAlternateColor) 4.dp else 8.dp,
    )

//    AppFilledCard(modifier = Modifier.padding(0.dp)) {
//        Row(modifier = Modifier.fillMaxWidth()){
//            AppText(text = text)
//        }
//    }

}

@Preview
@Composable
fun AppListItemPreview(){
    AppSection{
        AppInsetListItem(
            text = "Item 1",
        )
    }
}
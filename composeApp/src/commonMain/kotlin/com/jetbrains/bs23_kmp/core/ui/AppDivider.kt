package com.jetbrains.bs23_kmp.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AppDivider(){
    Divider(thickness = 0.45.dp)
}

@Composable
fun AppSpacer(){
    Spacer(modifier = Modifier.size(8.dp))
}

@Composable
fun AppSectionDivider(){
    Divider(
        thickness = 0.45.dp,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}
@Composable
fun AppSectionDividerWithText(text: String){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Divider(
            thickness = 0.45.dp,
            modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.size(8.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Thin
            ),
        )
//        Row(
//            modifier = Modifier.weight(1f),
//            horizontalArrangement = Arrangement.Center
//        ){
//            Divider(
//                thickness = 0.45.dp,
//                modifier = Modifier.weight(1f)
//            )
//        }
        Spacer(modifier = Modifier.size(8.dp))
        Divider(
            thickness = 0.45.dp,
            modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
        )

    }
}
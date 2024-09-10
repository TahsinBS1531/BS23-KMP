package com.jetbrains.bs23_kmp.core.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jetbrains.bs23_kmp.core.util.DropdownItem
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun AppListItemRemovable(
    modifier: Modifier = Modifier,
    item: DropdownItem,
//    @DrawableRes icon: Int? = null,
    onRemove: (DropdownItem) -> Unit,
    isAlternateColor: Boolean = false,
    isRemovable:(DropdownItem) -> Boolean = {true}
) {
//    ListItem(
//        headlineContent = {
//            Text(text = item.name, style = MaterialTheme.typography.bodyMedium)
//        },
//        leadingContent = {
//            AppIcon(
//                modifier = Modifier.size(8.dp),
//                icon = icon ?: R.drawable.ic_bullet_point,
//                tint = MaterialTheme.colorScheme.onSurface
//            )
//        },
//        trailingContent = {
//            AppIconButton(
//                icon = R.drawable.ic_delete,
//            ) {
//                onRemove(item)
//            }
//        }
//    )
    ListItem(
        modifier = Modifier.fillMaxWidth(),
        headlineContent = {
            LabelText(
                text = item.name,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        },
        trailingContent = {
            if (isRemovable(item)){
//                Icon(
//                    modifier = Modifier.size(24.dp).clickable { onRemove(item)  },
//                    painter = painterResource(id = icon ?: R.drawable.ic_delete),
//                    tint = MaterialTheme.colorScheme.onSurface,
//                    contentDescription = null
//                )
            }

//            AppIconButton(
//                icon = R.drawable.ic_delete,
//            ) {
//                onRemove(item)
//            }

//            IconButton(
//                onClick = { onRemove(item) }
//            ){
//                Icon(
//                    modifier = Modifier.size(24.dp),
//                    painter = painterResource(id = icon ?: R.drawable.ic_delete),
//                    tint = MaterialTheme.colorScheme.onSurface,
//                    contentDescription = null
//                )
//            }
        },
        tonalElevation = if(isAlternateColor) 4.dp else 8.dp,
    )

}

@Preview()
@Composable
fun AppListItemRemovablePreview() {
    AppListItemRemovable(
        item = DropdownItem("Item 1", "1"),
        onRemove = {}
    )
}

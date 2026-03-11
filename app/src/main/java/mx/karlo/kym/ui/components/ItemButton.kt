package mx.karlo.kym.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ItemButton(
    text: String,
    onClick: () -> Unit,
    onDelete:() -> Unit,
) {

    var expanded by remember { mutableStateOf(false) }

    Button(
        modifier = Modifier
            .padding(
                bottom = 8.dp,
                start = 8.dp,
                end = 8.dp,
            ),
        contentPadding = PaddingValues(
            start = 16.dp,
        ),
        onClick = { onClick() },
        shape = RoundedCornerShape(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text)

            IconButton(
                onClick = { expanded = !expanded }
            ) {

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {

                    DropdownMenuItem(
                        text = { Text("Delete") },
                        onClick = {
                            expanded = !expanded
                            onDelete()
                        }
                    )

                }
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "Exercise list"
                )
            }
        }
    }
}

@Preview
@Composable
fun H() {
    ItemButton(
        "h",
        {},
        {}
    )
}
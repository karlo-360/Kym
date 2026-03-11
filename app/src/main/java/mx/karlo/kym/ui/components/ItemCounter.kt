package mx.karlo.kym.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun ItemCounter(
    count: Float = 0f,
    minusOnClick: () -> Unit,
    plusOnClick: () -> Unit,
    content: @Composable (Float) -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilledIconButton(
            onClick = minusOnClick
        ) {
            Icon(
                imageVector = Icons.Filled.Remove,
                contentDescription = "Reduce"
            )
        }
        content(count)

        FilledIconButton(
            onClick = plusOnClick
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add"
            )
        }
    }
}
package mx.karlo.kym.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ToggleButton(
    text: String? = null,
    icon: ImageVector? = null,
    selected: Boolean,
    onToggle: (Boolean) -> Unit
) {

    val containerColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.surfaceVariant
    )

    val contentColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.onPrimary
        else MaterialTheme.colorScheme.onSurfaceVariant
    )

    val shape by animateDpAsState(
        targetValue = if (selected) 16.dp else 50.dp,
        label = "shape"
    )

    Surface(
        color = containerColor,
        selected = selected,
        onClick = { onToggle(!selected) },
        shape = RoundedCornerShape(shape),
        contentColor = contentColor,
    ) {

        Row(
            modifier = Modifier
                .minimumInteractiveComponentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            icon?.let {
                var modifier: Modifier by remember { mutableStateOf(Modifier) }
                if (text != null) {
                    modifier = Modifier.padding(start = 16.dp)
                }

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = modifier
                )
            }


            text?.let {
                val modifier = if (icon == null)
                    Modifier.padding(16.dp)
                else
                    Modifier.padding(end = 16.dp)

                    Text(
                    text = text,
                    modifier = modifier
                )
            }
        }
    }
}
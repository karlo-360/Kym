package mx.karlo.kym.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun <T> ItemsList(
    modifier: Modifier,
    items: List<T>,
    itemContent: @Composable (T) -> Unit
    ) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items) {item ->
            itemContent(item)
        }
    }
}
package mx.karlo.kym.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.karlo.kym.data.local.routine.RoutineExercise
import sh.calvin.reorderable.ReorderableCollectionItemScope
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.ReorderableLazyListState

@Composable
fun RoutineExercisesList(
    exercises: List<RoutineExercise>,
    lazyListState: LazyListState,
    reorderableLazyListState: ReorderableLazyListState,
    itemContent: @Composable ReorderableCollectionItemScope.(RoutineExercise, Boolean) -> Unit,
) {
    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        itemsIndexed(
            items = exercises,
            key = {_, ex -> ex.id },
        ) { _, exercise ->
            ReorderableItem(
                state = reorderableLazyListState,
                key = exercise.id,
            ) { isDragging ->
                itemContent(exercise, isDragging)
            }
        }
    }
}
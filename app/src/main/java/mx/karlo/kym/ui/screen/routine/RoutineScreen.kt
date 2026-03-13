package mx.karlo.kym.ui.screen.routine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DragIndicator
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Reorder
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import mx.karlo.kym.ui.components.ExerciseCard
import mx.karlo.kym.ui.components.ItemCounter
import mx.karlo.kym.ui.components.ItemsList
import mx.karlo.kym.ui.components.RoutineExercisesList
import mx.karlo.kym.ui.components.ToggleButton
import mx.karlo.kym.ui.theme.CatppuccinTheme
import mx.karlo.kym.ui.viewmodel.exercise.ExerciseViewModel
import mx.karlo.kym.ui.viewmodel.routine.RoutineViewModel
import mx.karlo.kym.ui.viewmodel.routineExercise.RoutineExerciseViewModel
import sh.calvin.reorderable.rememberReorderableLazyListState
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineScreen(
    id: Int,
    navController: NavHostController,
    routineVM: RoutineViewModel,
    routineExerciseVM: RoutineExerciseViewModel,
    exerciseVM: ExerciseViewModel,
) {
    //sheetState
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    //VM
    LaunchedEffect(id) {
        routineExerciseVM.getRoutineExercisesByRoutineId(id)
        routineVM.getRoutineById(id)
    }

    val routineExercises by routineExerciseVM.routineExercises.collectAsState(emptyList())
    val routine by routineVM.routine.collectAsState(null)

    //list of exercises for ItemsList
    var exerciseList by remember { mutableStateOf(routineExercises)}
    LaunchedEffect(routineExercises) {
        if (routineExercises.isNotEmpty()) {
            exerciseList = routineExercises
        }
    }

    val lazyListState = rememberLazyListState()
    val state = rememberReorderableLazyListState(lazyListState) { from, to ->
        exerciseList = exerciseList.toMutableList().apply {
            val fromIndex = indexOfFirst { it.id == from.key }
            val toIndex = indexOfFirst { it.id == to.key }

            add(toIndex, removeAt(fromIndex))
        }
        if (routineExercises.isNotEmpty()) {
            routineExerciseVM.reorder(routineExercises, from.index, to.index)
        }
    }

    //max value of all orderIndex
    val maxOrderIndex = remember(routineExercises) {
        derivedStateOf { routineExercises.maxOfOrNull { it.orderIndex } ?: 0 }
    }

    //show fao if there is exercises
    val isFaoEnabled = routineExercises.isNotEmpty()

    //state of reorderable button
    var isReorderable by remember { mutableStateOf(false) }
    //state of edit sets button
    var isEditing by remember { mutableStateOf(false) }


    CatppuccinTheme {
        Scaffold (
            topBar = {
                TopBar(
                    name = routine?.name ?: "Nameless",
                    reorderSelected = isReorderable,
                    onReorderToggle = {
                        isReorderable = it
                        isEditing = false
                    },
                    editSelected = isEditing,
                    onEditToggle = {
                        isEditing = it
                        isReorderable = false
                    }
                )
            },
            floatingActionButton = {
                if (isFaoEnabled) {
                    Fab(
                        id = id,
                        navController = navController
                    )
                }
            }

        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                if (routineExercises.isEmpty()) {

                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("This routine has no exercises")
                        Button(
                            onClick = { showBottomSheet = true }
                        ) { Text("Add Exercise") }
                    }

                } else {

                    RoutineExercisesList(
                        exercises = exerciseList,
                        lazyListState = lazyListState,
                        reorderableLazyListState = state,
                    ) { exercise, isDragging ->

                        ExerciseCard(
                            exercise = exercise,
                            isReorderable = isReorderable,
                            dragState = isDragging,
                            reorderIcon = {
                                Icon(
                                    modifier = Modifier.draggableHandle(),
                                    imageVector = Icons.Filled.DragIndicator,
                                    contentDescription = "Drag Indicator"
                                )
                            },
                        ) {
                            ItemCounter(
                                count = exercise.sets.toFloat(),
                                showUI = isEditing,
                                minusOnClick = {
                                    if (exercise.isUnilateral) {
                                        if (exercise.sets > 3) {
                                            routineExerciseVM.updateExerciseSets(
                                                id = exercise.id,
                                                sets = exercise.sets - 2
                                            )
                                        }
                                    } else {
                                        if (exercise.sets > 1) {
                                            routineExerciseVM.updateExerciseSets(
                                                id = exercise.id,
                                                sets = exercise.sets - 1
                                            )
                                        }
                                    }
                                },
                                plusOnClick = {
                                    if (exercise.isUnilateral) {
                                        routineExerciseVM.updateExerciseSets(
                                            id = exercise.id,
                                            sets = exercise.sets + 2
                                        )
                                    } else {
                                        routineExerciseVM.updateExerciseSets(
                                            id = exercise.id,
                                            sets = exercise.sets + 1
                                        )
                                    }
                                }

                            ) {
                                val sets = if (exercise.isUnilateral) {
                                    exercise.sets / 2
                                } else {
                                    exercise.sets
                                }

                                if (exercise.reps != 0 || exercise.weight != 0f) {
                                    val formatter = DecimalFormat("#.##")
                                    val weight = formatter.format(exercise.weight)
                                    Text("$sets Sets: ${exercise.reps} x $weight ${exercise.uom}")
                                } else {
                                    Text("$sets Sets")
                                }
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),

                ) {
                    Button(
                        onClick = {
                            showBottomSheet = !showBottomSheet
                        },
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                    ) { Text("Add Exercise") }
                }

            }

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    SheetContent(
                        routineId = id,
                        navController = navController,
                        routineExerciseVM = routineExerciseVM,
                        exerciseViewModel = exerciseVM,
                        maxIndex = maxOrderIndex.value,
                        onSaved = {
                            showBottomSheet = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun Fab(
    id: Int,
    navController: NavHostController
) {
    FloatingActionButton(
        onClick = {
            navController.navigate("workoutScreen/$id")
        },
    ) {
        Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = "Start Routine"
        )
    }

}

@Composable
private fun SheetContent (
    routineId: Int,
    navController: NavHostController,
    exerciseViewModel: ExerciseViewModel,
    routineExerciseVM: RoutineExerciseViewModel,
    maxIndex: Int,
    onSaved: () -> Unit
) {

    var selectedExercises by remember {
        mutableStateOf(setOf<Int>())
    }

    val exercises by exerciseViewModel.exercises.collectAsState(initial = emptyList())
    val enabled = selectedExercises.isNotEmpty()
    val scope = rememberCoroutineScope()

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (exercises.isEmpty()){
            Text("There is no exercises yet")

            Button(
                onClick = {
                    navController.navigate("createExerciseScreen")
                }
            ) { Text("Create exercise") }

        } else {

            ItemsList(
                modifier = Modifier.fillMaxWidth(),
                items = exercises
            ) { exercise->

                val isChecked = selectedExercises.contains(exercise.id)
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { checked ->
                            selectedExercises =
                                if (checked) {
                                    selectedExercises + exercise.id
                                } else {
                                    selectedExercises - exercise.id
                                }
                        },
                    )

                    Text("Exercise: ${exercise.name}")
                }

            }

            Button(
                onClick = {

                    var index = maxIndex
                    scope.launch {
                        for (exerciseId in selectedExercises) {
                            val exercise = exerciseViewModel.getExerciseById(exerciseId)
                            index += 100

                            routineExerciseVM.addRoutineExercise(
                                routineId = routineId,
                                exerciseName = exercise.name,
                                orderIndex = index,
                                sets = if (exercise.isUnilateral) {
                                    2
                                } else {
                                    1
                                },
                                isUnilateral = exercise.isUnilateral
                            )
                        }
                        onSaved()
                    }

                },

                enabled = enabled
            ) { Text("Confirm") }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    name: String,
    reorderSelected: Boolean,
    onReorderToggle: (Boolean) -> Unit,
    editSelected: Boolean,
    onEditToggle: (Boolean) -> Unit
) {
    CenterAlignedTopAppBar(

        colors = TopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
            navigationIconContentColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary,
            subtitleContentColor = Color.Transparent
        ),

        title = { Text(name) },

        actions = {
            ToggleButton(
                icon = Icons.Filled.Edit,
                selected = editSelected,
                onToggle = onEditToggle
            )
            Spacer(Modifier.padding(horizontal = 4.dp))
            ToggleButton(
                icon = Icons.Filled.Reorder,
                selected = reorderSelected,
                onToggle = onReorderToggle
            )
            Spacer(Modifier.padding(horizontal = 2.dp))
        }
    )
}
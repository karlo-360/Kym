package mx.karlo.kym.ui.screen

import android.annotation.SuppressLint
import android.icu.text.DecimalFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import mx.karlo.kym.core.model.Uom
import mx.karlo.kym.data.local.routine.RoutineExercise
import mx.karlo.kym.ui.components.ItemCounter
import mx.karlo.kym.ui.components.ItemsList
import mx.karlo.kym.ui.theme.CatppuccinTheme
import mx.karlo.kym.ui.viewmodel.routineExercise.RoutineExerciseViewModel
import mx.karlo.kym.ui.viewmodel.workout.WorkoutSessionViewModel
import mx.karlo.kym.ui.viewmodel.workout.WorkoutSetViewModel

enum class WorkoutStage {
    LIST,
    TRAINING,
    FINISHED
}

@Composable
fun WorkoutScreen(
    id: Int,
    routineExerciseVM: RoutineExerciseViewModel,
    workoutSessionVM: WorkoutSessionViewModel,
    workoutSetVM: WorkoutSetViewModel,
    onFinished: () -> Unit
) {

    var stage by remember { mutableStateOf(WorkoutStage.LIST)}
    var currentExerciseIndex by remember { mutableIntStateOf(0) }
    var currentSetIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(id) {
        routineExerciseVM.getRoutineExercisesByRoutineId(id)
    }

    val routineExercises by routineExerciseVM.routineExercises.collectAsState(initial = emptyList())
    val activeSession = workoutSessionVM.activeSession.collectAsState(initial = null)

    val sessionId = activeSession.value?.id ?: -1
    val exercise = routineExercises.getOrNull(currentExerciseIndex)


    CatppuccinTheme{
        Scaffold(

            topBar = { TopBar(
                stage = stage,
                exercise = exercise,
                currentSet = currentSetIndex,
            ) }

        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                if (routineExercises.isEmpty()) {
                    Text("There is no exercises")
                } else {

                    when (stage) {
                        WorkoutStage.LIST -> {
                            ItemsList(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                items = routineExercises
                            ) { routineExercise ->

                                Text(routineExercise.exerciseName)
                            }

                            Button(

                                onClick = {

                                    if (sessionId == -1) {
                                        workoutSessionVM.addWorkoutSession{
                                            stage = WorkoutStage.TRAINING
                                        }
                                    } else {
                                        stage = WorkoutStage.TRAINING
                                    }

                                }
                            ) { Text("Begin") }
                        }

                        WorkoutStage.TRAINING -> {

                            val exercise = routineExercises.getOrNull(currentExerciseIndex) ?: return@Column

                            TrainingLogView(
                                exercise = exercise,
                                onSetCompleted = { reps, weight ->

                                    workoutSetVM.addWorkoutSet(
                                        sessionId = sessionId,
                                        exerciseName = exercise.exerciseName,
                                        setNumber = currentSetIndex + 1,
                                        reps = reps,
                                        weight = weight,
                                        uom = Uom.KG
                                    )

                                    routineExerciseVM.updateExerciseLoad(
                                        id = exercise.id,
                                        reps = reps,
                                        weight = weight
                                    )

                                    if (currentSetIndex < exercise.sets-1) {
                                        currentSetIndex ++
                                    } else {
                                        currentSetIndex = 0

                                        if (currentExerciseIndex < routineExercises.lastIndex) {
                                            currentExerciseIndex ++
                                        } else {
                                            stage = WorkoutStage.FINISHED
                                        }
                                    }

                                }
                            )

                        }

                        WorkoutStage.FINISHED-> {

                            Text("Workout Complete")
                            Button(
                                onClick = {
                                    workoutSessionVM.deactivateSession(sessionId)
                                    onFinished()
                                }
                            ) { Text("Finish") }

                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    stage: WorkoutStage,
    exercise: RoutineExercise?,
    currentSet: Int,

    ) {
    var side by remember { mutableStateOf("") }

    CenterAlignedTopAppBar(
        colors = TopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
            navigationIconContentColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary,
            subtitleContentColor = Color.Transparent
        ),
        title = {
            when (stage) {
                WorkoutStage.LIST -> Text("List")
                WorkoutStage.TRAINING -> Text(
                    if (exercise?.isUnilateral == true) {
                        side = if (currentSet % 2 == 1) "(Left)" else "(Right)"
                        "${exercise.exerciseName} $side - ${(currentSet / 2) + 1}/${exercise.sets / 2}"
                    } else {
                        "${exercise?.exerciseName} - ${currentSet + 1}/${exercise?.sets}"
                    }
                )

                WorkoutStage.FINISHED -> Text("Finished")
            }
        },
        actions = {
            IconButton(
                onClick = {},
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Exercise list"
                )
            }
        }
    )
}

@SuppressLint("DefaultLocale")
@Composable
private fun TrainingLogView(
    exercise: RoutineExercise,
    onSetCompleted: (reps: Int, weight: Float) -> Unit
    ) {

    var reps by remember(exercise.id) { mutableStateOf(exercise.reps.toString()) }
    var weight by remember(exercise.id) { mutableStateOf(exercise.weight.toString()) }
    val formatter = DecimalFormat("#.##")

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(exercise.exerciseName)

        ItemCounter(
            minusOnClick = {
                val value = reps.toIntOrNull() ?: 0
                reps = (value - 1).toString()
            },
            plusOnClick = {
                val value = reps.toIntOrNull() ?: 0
                reps = (value + 1).toString()
            }
        ) {
            TextField(
                value = reps,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) {
                        reps = newValue
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                label = { Text("Reps") },
                singleLine = true
            )

        }

        ItemCounter(
            minusOnClick = {
                val value = weight.toFloatOrNull() ?: 0f
                weight = formatter.format(value - 1)
            },
            plusOnClick = {
                val value = weight.toFloatOrNull() ?: 0f
                weight = formatter.format(value + 1)
            }
        ) {
            TextField(
                value = weight,
                onValueChange = { newValue ->
                    if (newValue.matches(Regex("^\\d*\\.?\\d{0,2}$"))) {
                        weight = newValue
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                label = {
                    Text("Weight (${exercise.uom})")
                },
            )
        }

        Button(
            onClick = {
                onSetCompleted(
                    reps.toIntOrNull() ?: 0,
                    weight.toFloatOrNull() ?:0f
                )
            }
        ) { Text("Save") }
    }
}
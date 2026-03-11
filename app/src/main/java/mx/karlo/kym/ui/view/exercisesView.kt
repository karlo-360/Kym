package mx.karlo.kym.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import mx.karlo.kym.data.local.repository.ExerciseRepository
import mx.karlo.kym.ui.components.ItemButton
import mx.karlo.kym.ui.components.ItemsList
import mx.karlo.kym.ui.viewmodel.exercise.ExerciseViewModel
import mx.karlo.kym.ui.viewmodel.exercise.ExerciseViewModelFactory

@Composable
fun ExercisesView(
    navController: NavHostController,
    repository: ExerciseRepository
) {
    val viewModel: ExerciseViewModel = viewModel(
        factory = ExerciseViewModelFactory(repository)
    )

    val exercises by viewModel.exercises.collectAsState(initial = emptyList())

    var buttonText by remember { mutableStateOf("") }

    if (exercises.isEmpty()) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("There is no exercises")
        }

    } else {

        ItemsList(
            modifier = Modifier
                .fillMaxSize(),
            items = exercises
        ) { exercise ->

            buttonText = if (exercise.isUnilateral) {
                "${exercise.name} - Unilateral"
            } else {
                exercise.name
            }

            ItemButton(
                text = buttonText,
                onClick = {
                    navController.navigate("exerciseScreen/${exercise.id}")
                },
                onDelete = {
                    viewModel.deleteExercise(exercise)
                }

            )
        }
    }
}
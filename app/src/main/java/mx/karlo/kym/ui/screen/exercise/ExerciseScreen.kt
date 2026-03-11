package mx.karlo.kym.ui.screen.exercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import mx.karlo.kym.data.local.exercise.Exercise
import mx.karlo.kym.ui.theme.CatppuccinTheme
import mx.karlo.kym.ui.viewmodel.exercise.ExerciseViewModel

@Composable
fun ExerciseScreen(
    id: Int,
    exerciseVM: ExerciseViewModel
) {

    var exercise by remember { mutableStateOf<Exercise?>(null) }

    LaunchedEffect(id) {
        exercise = exerciseVM.getExerciseById(id)
    }

    CatppuccinTheme {
        Scaffold { paddingValues ->
            exercise?.let {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Change Name")
                    Text("name: ${it.name}")
                }
            }
        }
    }
}

package mx.karlo.kym.ui.screen.routineExercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mx.karlo.kym.ui.theme.CatppuccinTheme

@Composable
fun EditRoutineExerciseScreen() {

    CatppuccinTheme {
        Scaffold { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                Text("")
            }
        }
    }
}
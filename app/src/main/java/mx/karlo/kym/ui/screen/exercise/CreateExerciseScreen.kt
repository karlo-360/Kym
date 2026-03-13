package mx.karlo.kym.ui.screen.exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardCapitalization
import mx.karlo.kym.ui.theme.CatppuccinTheme
import mx.karlo.kym.ui.viewmodel.exercise.ExerciseViewModel

@Composable
fun CreateExerciseScreen(
    exerciseVM: ExerciseViewModel,
    onSaved: () -> Unit
) {

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    var name by remember { mutableStateOf("") }
    var isUnilateral by remember { mutableStateOf(false) }

    CatppuccinTheme {
        Scaffold { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .background(
                        color = MaterialTheme.colorScheme.background
                    )
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = name,
                    modifier = Modifier.focusRequester(focusRequester),
                    onValueChange = { name = it },
                    label = {
                        Text("Name")
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words
                    ),
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Checkbox(
                        checked = isUnilateral,
                        onCheckedChange = {
                            isUnilateral = !isUnilateral
                        },
                    )

                    Text("Unilateral")
                }
                Button(
                    onClick = {
                        if (name.isNotBlank()) {
                            exerciseVM.addExercise(
                                name = name,
                                isUnilateral = isUnilateral
                            )

                            onSaved()
                        }
                    }
                ) {
                    Text("Save Exercise")
                }
            }
        }
    }
}
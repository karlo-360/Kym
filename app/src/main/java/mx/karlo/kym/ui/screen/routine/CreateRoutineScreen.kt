package mx.karlo.kym.ui.screen.routine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import mx.karlo.kym.ui.viewmodel.routine.RoutineViewModel

@Composable
fun CreateRoutineScreen(
    routineVM: RoutineViewModel,
    onSaved: () -> Unit
) {

    var name by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = name,
            onValueChange = {name = it},
            label = {
                Text("Name")
            },
            singleLine = true,
        )
        Button(
            onClick = {
                if (name.isNotBlank()) {
                    routineVM.addRoutine(
                        name = name
                    )
                    onSaved()
                }
            }
        ) {
            Text("Add Routine")
        }
    }
}
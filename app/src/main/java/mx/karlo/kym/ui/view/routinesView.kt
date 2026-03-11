package mx.karlo.kym.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import mx.karlo.kym.data.local.repository.RoutineRepository
import mx.karlo.kym.ui.components.ItemButton
import mx.karlo.kym.ui.components.ItemsList
import mx.karlo.kym.ui.viewmodel.routine.RoutineViewModel
import mx.karlo.kym.ui.viewmodel.routine.RoutineViewModelFactory

@Composable
fun RoutinesView(
    navController: NavHostController,
    repository: RoutineRepository
) {

    val viewModel: RoutineViewModel = viewModel(
        factory = RoutineViewModelFactory(repository)
    )

    val routines by viewModel.routines.collectAsState(initial = emptyList())

    if (routines.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("There is no routines")
        }
    } else {
        ItemsList(
            modifier = Modifier
                .fillMaxSize(),
            items = routines,
        ) { routine ->
            ItemButton(
                text = routine.name,
                onClick = {
                    navController.navigate("routineScreen/${routine.id}")
                },
                onDelete = { viewModel.deleteRoutine(routine) }
            )
        }
    }
}
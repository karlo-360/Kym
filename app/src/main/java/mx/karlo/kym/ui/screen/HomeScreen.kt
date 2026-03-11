package mx.karlo.kym.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import mx.karlo.kym.data.local.repository.ExerciseRepository
import mx.karlo.kym.data.local.repository.RoutineRepository
import mx.karlo.kym.ui.theme.CatppuccinTheme
import mx.karlo.kym.ui.theme.MacchiatoMauve
import mx.karlo.kym.ui.view.ExercisesView
import mx.karlo.kym.ui.view.InsightsView
import mx.karlo.kym.ui.view.RoutinesView

private enum class View(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String,
) {
    ROUTINES ("routinesView", "Routines", Icons.Filled.Menu, "Routines"),
    EXERCISES("exercisesView", "Exercises", Icons.Filled.FitnessCenter, "Exercises"),
    INSIGHTS ("insightsView", "Insights", Icons.Filled.Insights, "Insights"),
}

@Composable
fun HomeScreen(
    rootNavHostController: NavHostController,
    exerciseRepository: ExerciseRepository,
    routineRepository: RoutineRepository
) {
    val navController = rememberNavController()
    val startView = View.ROUTINES

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    CatppuccinTheme {
        Scaffold (

            topBar = { TopBar() },

            bottomBar = {
                BottomBar(navController, currentRoute)
            },

            floatingActionButton = {
                Fab(rootNavHostController, currentRoute)
            }
        ) { paddingValues ->

            NavHost(
                navController = navController,
                startDestination = startView.route,
                modifier = Modifier.padding(paddingValues)
            ) {

                composable(View.ROUTINES.route)  {
                    RoutinesView(
                        navController = rootNavHostController,
                        repository = routineRepository
                    )
                }

                composable(View.EXERCISES.route) {
                    ExercisesView(
                        navController = rootNavHostController,
                        repository = exerciseRepository
                    )
                }

                composable(View.INSIGHTS.route)  { InsightsView() }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    TopAppBar(

        colors = TopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
            navigationIconContentColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary,
            subtitleContentColor = Color.Transparent
        ),

        title = { Text("") },

        actions = {
            IconButton(
                onClick = {},
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings"
                )
            }
        }
    )
}

@Composable
private fun BottomBar(navController: NavHostController, currentRoute: String?) {
    

    NavigationBar {
        View.entries.forEach { view ->
            NavigationBarItem(

                selected = (currentRoute == view.route),

                onClick = {
                    navController.navigate(route = view.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },

                icon = {
                    Icon(
                        imageVector = view.icon,
                        contentDescription = view.contentDescription
                    )
                },

                label = { Text(view.label) },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = MacchiatoMauve
                )
            )
        }
    }

}

@Composable
private fun Fab(navController: NavHostController, currentRoute: String?) {
    when (currentRoute) {
        View.ROUTINES.route -> {
            FloatingActionButton(
                onClick = {
                    navController.navigate("createRoutineScreen")
                }
            ) {
                Icon(
                    Icons.Filled.Add,
                    "add a routine"
                )
            }
        }
        View.EXERCISES.route -> {
            FloatingActionButton(
                onClick = {
                    navController.navigate("createExerciseScreen")
                }
            ) {
                Icon(
                    Icons.Filled.Add,
                    "add a exercise"
                )
            }
        }
    }
}
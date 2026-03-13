package mx.karlo.kym.ui.screen

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Insights
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import mx.karlo.kym.data.local.repository.ExerciseRepository
import mx.karlo.kym.data.local.repository.RoutineRepository
import mx.karlo.kym.ui.theme.CatppuccinTheme
import mx.karlo.kym.ui.view.ExercisesView
import mx.karlo.kym.ui.view.InsightsView
import mx.karlo.kym.ui.view.RoutinesView

private enum class View(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String,
) {
    ROUTINES(
        route = "routinesView",
        label = "Routines",
        icon = Icons.AutoMirrored.Filled.List,
        contentDescription = "Routines"
    ),
    EXERCISES(
        route = "exercisesView",
        label = "Exercises",
        icon = Icons.Filled.FitnessCenter,
        contentDescription = "Exercises"
    ),
    INSIGHTS(
        route = "insightsView",
        label = "Insights",
        icon = Icons.Filled.Insights,
        contentDescription = "Insights"
    ),
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

    val views = View.entries
    val currentIndex = views.indexOfFirst { it.route == currentRoute }

    CatppuccinTheme {
        Scaffold (

            topBar = { TopBar(currentRoute) },

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
                modifier = Modifier
                    .padding(paddingValues)
                    .pointerInput(currentRoute) {
                        detectHorizontalDragGestures { _, dragAmount ->

                            if (dragAmount < -25) {
                                val nextIndex = currentIndex + 1
                                if (nextIndex < views.size) {
                                    navController.navigate(views[nextIndex].route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                    }
                                }
                            }

                            if (dragAmount > 25) {
                                val prevIndex = currentIndex - 1
                                if (prevIndex >= 0) {
                                    navController.navigate(views[prevIndex].route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true

                                    }

                                }
                            }
                        }
                    }
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

                composable(View.INSIGHTS.route)  {
                    InsightsView()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(currentRoute: String?) {
    TopAppBar(

        colors = TopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
            navigationIconContentColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary,
            subtitleContentColor = Color.Transparent
        ),

        title = {
            when(currentRoute) {
                View.ROUTINES.route -> { Text("Routines") }
                View.EXERCISES.route -> { Text("Exercises") }
                View.INSIGHTS.route -> { Text("Insights") }
            }
        },

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

                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = MaterialTheme.colorScheme.primary
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
                    "Create a routine"
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
                    "Create a exercise"
                )
            }
        }
    }
}
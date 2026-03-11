package mx.karlo.kym.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import mx.karlo.kym.data.local.Database
import mx.karlo.kym.data.local.repository.ExerciseRepository
import mx.karlo.kym.data.local.repository.RoutineExerciseRepository
import mx.karlo.kym.data.local.repository.RoutineRepository
import mx.karlo.kym.data.local.repository.WorkoutSessionRepository
import mx.karlo.kym.data.local.repository.WorkoutSetRepository
import mx.karlo.kym.ui.screen.*
import mx.karlo.kym.ui.screen.exercise.*
import mx.karlo.kym.ui.screen.routine.*
import mx.karlo.kym.ui.viewmodel.exercise.ExerciseViewModel
import mx.karlo.kym.ui.viewmodel.exercise.ExerciseViewModelFactory
import mx.karlo.kym.ui.viewmodel.routine.RoutineViewModel
import mx.karlo.kym.ui.viewmodel.routine.RoutineViewModelFactory
import mx.karlo.kym.ui.viewmodel.routineExercise.RoutineExerciseVMFactory
import mx.karlo.kym.ui.viewmodel.routineExercise.RoutineExerciseViewModel
import mx.karlo.kym.ui.viewmodel.workout.WorkoutSessionVMFactory
import mx.karlo.kym.ui.viewmodel.workout.WorkoutSessionViewModel
import mx.karlo.kym.ui.viewmodel.workout.WorkoutSetVMFactory
import mx.karlo.kym.ui.viewmodel.workout.WorkoutSetViewModel


@Composable
fun AppNavigation(db: Database) {

    val rootNavController = rememberNavController()

    val exerciseRepository = remember {
        ExerciseRepository(db.exerciseDao())
    }

    val routineRepository = remember {
        RoutineRepository(db.routineDao())
    }

    val routineExerciseRepository = remember {
        RoutineExerciseRepository(db.routineExerciseDao())
    }

    val workoutSessionRepository = remember {
        WorkoutSessionRepository(db.workoutSessionDao())
    }

    val workoutSetRepository = remember {
        WorkoutSetRepository(db.workoutSetDao())
    }

    NavHost(
        navController = rootNavController,
        startDestination = "homeScreen"
    ) {

        composable("homeScreen") {
            HomeScreen(
                rootNavHostController = rootNavController,
                exerciseRepository = exerciseRepository,
                routineRepository = routineRepository
            )
        }

        composable("createExerciseScreen") {
            val exerciseVM: ExerciseViewModel = viewModel(
                factory = ExerciseViewModelFactory(exerciseRepository)
            )

            CreateExerciseScreen(
                exerciseVM = exerciseVM,
                onSaved = {
                    rootNavController.popBackStack()
                }
            )
        }

        composable("createRoutineScreen") {
            val routineVM: RoutineViewModel = viewModel(
                factory = RoutineViewModelFactory(routineRepository)
            )
            CreateRoutineScreen(
                routineVM = routineVM,
                onSaved = {
                    rootNavController.popBackStack()
                }
            )
        }

        composable(
            route = "workoutScreen/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val id: Int = (backStackEntry.arguments?.getInt("id") ?: -1)

            val routineExerciseVM: RoutineExerciseViewModel = viewModel(
                factory = RoutineExerciseVMFactory(routineExerciseRepository)
            )

            val workoutSessionVM: WorkoutSessionViewModel = viewModel(
                factory = WorkoutSessionVMFactory(workoutSessionRepository)
            )

            val workoutSetVM: WorkoutSetViewModel = viewModel(
                factory = WorkoutSetVMFactory(workoutSetRepository)
            )

            WorkoutScreen(
                id = id,
                routineExerciseVM = routineExerciseVM,
                workoutSessionVM = workoutSessionVM,
                workoutSetVM = workoutSetVM,
                onFinished = {
                    rootNavController.navigate("homeScreen")
                }
            )

        }

        composable(
            route = "routineScreen/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val id: Int = (backStackEntry.arguments?.getInt("id") ?: -1)

            val routineVM: RoutineViewModel = viewModel(
                factory = RoutineViewModelFactory(routineRepository)
            )

            val routineExerciseVM: RoutineExerciseViewModel = viewModel(
                factory = RoutineExerciseVMFactory(routineExerciseRepository)
            )

            val exerciseVM: ExerciseViewModel = viewModel(
                factory = ExerciseViewModelFactory(exerciseRepository)
            )

            val workoutSetVM: WorkoutSetViewModel = viewModel(
                factory = WorkoutSetVMFactory(workoutSetRepository)
            )

            RoutineScreen(
                id = id,
                navController = rootNavController,
                routineVM = routineVM,
                routineExerciseVM = routineExerciseVM,
                exerciseVM = exerciseVM,
            )

        }

        composable(
            route = "exerciseScreen/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val id: Int = (backStackEntry.arguments?.getInt("id") ?: -1)

            val exerciseVM: ExerciseViewModel = viewModel(
                factory = ExerciseViewModelFactory(exerciseRepository)
            )

            ExerciseScreen(
                id = id,
                exerciseVM = exerciseVM
            )

        }
    }
}
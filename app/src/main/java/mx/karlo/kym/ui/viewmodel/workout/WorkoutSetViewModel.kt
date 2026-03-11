package mx.karlo.kym.ui.viewmodel.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.karlo.kym.core.model.Uom
import mx.karlo.kym.data.local.repository.WorkoutSetRepository
import mx.karlo.kym.data.local.workout.WorkoutSet

class WorkoutSetViewModel(
    private val repository: WorkoutSetRepository
) : ViewModel() {

    private val _workout = MutableStateFlow<WorkoutSet?>(null)
    val lastWorkoutSet: StateFlow<WorkoutSet?> = _workout

    private val _workouts = MutableStateFlow<List<WorkoutSet?>>(emptyList())
    val workoutSets: StateFlow<List<WorkoutSet?>> = _workouts

    fun addWorkoutSet(
        sessionId: Int,
        exerciseName: String,
        setNumber: Int,
        reps: Int,
        weight: Float,
        uom: Uom,
    ) {

        viewModelScope.launch {
            repository.insert(
                WorkoutSet(
                    sessionId = sessionId,
                    exerciseName = exerciseName,
                    setNumber = setNumber,
                    reps = reps,
                    weight = weight,
                    uom = uom,
                )
            )
        }
    }

    fun deleteWorkoutSet(workoutSet: WorkoutSet) {
        viewModelScope.launch {
            repository.delete(workoutSet)
        }
    }

    fun getLastSetByExerciseName(exerciseName: String): Flow<WorkoutSet?> {
            return repository.getLastSetByExerciseName(exerciseName)
    }

    fun getSetsByExerciseName(exerciseName: String) {
        viewModelScope.launch {
            repository.getSetsByExerciseName(exerciseName).collect {
                _workouts.value = it
            }
        }
    }

    fun getSetsBySessionId(sessionId: Int) {
        viewModelScope.launch {
            repository.getSetsBySessionId(sessionId).collect {
                _workouts.value = it
            }
        }
    }
}
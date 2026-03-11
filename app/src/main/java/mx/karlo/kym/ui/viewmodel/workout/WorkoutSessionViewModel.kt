package mx.karlo.kym.ui.viewmodel.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import mx.karlo.kym.data.local.repository.WorkoutSessionRepository
import mx.karlo.kym.data.local.workout.WorkoutSession

class WorkoutSessionViewModel(
    private val repository: WorkoutSessionRepository
) : ViewModel() {

    val workoutSessions = repository.workoutSessions
    val lastWorkoutSessionId = repository.lastWorkoutSessionId
    val activeSession =  repository.activeSession

    private val _workout = MutableStateFlow<WorkoutSession?>(null)
    val workoutSession: StateFlow<WorkoutSession?> = _workout

    fun addWorkoutSession(onResult: (Int) -> Unit) {
        viewModelScope.launch {
            val id = repository.insert(
                WorkoutSession(
                    date = System.currentTimeMillis(),
                    isActive = true
                )
            )

            onResult(id)
        }
    }

    fun deleteWorkoutSession(workoutSession: WorkoutSession) {
        viewModelScope.launch {
            repository.delete(workoutSession)
        }
    }

    fun getWorkoutSessionById(id: Int) {
        viewModelScope.launch {
            repository.getWorkoutSessionById(id).collectLatest {
                _workout.value = it
            }
        }
    }

    fun deactivateSession(id: Int) {
        viewModelScope.launch {
            repository.deactivateSession(id)
        }
    }

}
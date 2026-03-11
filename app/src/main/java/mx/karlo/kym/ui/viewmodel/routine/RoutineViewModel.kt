package mx.karlo.kym.ui.viewmodel.routine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.karlo.kym.data.local.repository.RoutineRepository
import mx.karlo.kym.data.local.routine.Routine

class RoutineViewModel(
    private val repository: RoutineRepository
) : ViewModel() {
    val routines = repository.routines

    private val _routine = MutableStateFlow<Routine?>(null)
    //private val _routineWithExercises = MutableStateFlow<RoutineWithExercises?>(null)

    val routine: StateFlow<Routine?> = _routine
    //val routineWithExercises: StateFlow<RoutineWithExercises?> = _routineWithExercises

    fun getRoutineById(id: Int) {
        viewModelScope.launch {
            repository.getRoutineById(id).collect {
                _routine.value = it
            }
        }
    }

    //fun getRoutineWithExercises(id: Int) {
    //    viewModelScope.launch {
    //        repository.getRoutineWithExercises(id).collect {
    //            _routineWithExercises.value = it
    //        }
    //    }
    //}

    fun addRoutine(name: String) {
        viewModelScope.launch {
            repository.insert(
                Routine(
                    name = name,
                )
            )
        }
    }

    fun deleteRoutine(routine: Routine) {
        viewModelScope.launch {
            repository.delete(routine)
        }
    }

    //fun addExerciseToRoutine(
    //    routineId: Int,
    //    exerciseId: Int,
    //    reps: Int,
    //    sets: Int
    //    ) {
    //    viewModelScope.launch {
    //        repository.addExerciseToRoutine(
    //            routineId,
    //            exerciseId,
    //            sets,
    //            reps
    //        )
    //    }
    //}

    //fun removeExerciseToRoutine(
    //    routineId: Int,
    //    exerciseId: Int
    //) {
    //    viewModelScope.launch {
    //        repository.removeExerciseToRoutine(
    //            routineId,
    //            exerciseId
    //        )
    //    }
    //}

}
package mx.karlo.kym.ui.viewmodel.routineExercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.karlo.kym.data.local.repository.RoutineExerciseRepository
import mx.karlo.kym.data.local.routine.RoutineExercise

class RoutineExerciseViewModel(
    private val repository: RoutineExerciseRepository
) : ViewModel() {

    //val routineExercises = repository.routinesExercises

    private val _routine = MutableStateFlow<List<RoutineExercise>>(emptyList())
    val routineExercises: StateFlow<List<RoutineExercise>> = _routine

    fun getRoutineExercisesByRoutineId(id: Int) {
        viewModelScope.launch {
            repository.getRoutineExercisesByRoutineId(id).collect {
                _routine.value = it
            }
        }
    }

    fun addRoutineExercise(
        routineId: Int,
        exerciseName: String,
        orderIndex: Int,
        sets: Int = 1,
        isUnilateral: Boolean
    ) {
        viewModelScope.launch {
            repository.insert(
                RoutineExercise(
                    routineId = routineId,
                    exerciseName = exerciseName,
                    orderIndex = orderIndex,
                    sets = sets,
                    isUnilateral = isUnilateral,
                )
            )
        }
    }

    fun deleteRoutineExercise(routineExercise: RoutineExercise) {
        viewModelScope.launch {
            repository.delete(routineExercise)
        }
    }

    fun updateExerciseLoad(
        id: Int,
        reps: Int,
        weight: Float
    ) {
        viewModelScope.launch {
            repository.updateExerciseLoad(
                id = id,
                reps = reps,
                weight = weight
            )
        }
    }
    fun updateExerciseSets(
        id: Int,
        sets: Int,
    ) {
        viewModelScope.launch {
            repository.updateExerciseSets(
                id = id,
                sets = sets
            )
        }
    }

    fun reorder(
        exercises: List<RoutineExercise>,
        from: Int,
        to: Int
    ) {

        val updatedExercise = moveExercise(exercises, from, to)

        viewModelScope.launch {
            repository.updateRoutineExercise(updatedExercise)
        }
    }

}

private fun moveExercise(
    list: List<RoutineExercise>,
    fromIndex: Int,
    toIndex: Int
): RoutineExercise {

    val prev = list.getOrNull(toIndex - 1)
    val next = list.getOrNull(toIndex + 1)

    val newPosition = when {
        prev == null && next == null -> 100
        prev == null -> next!!.orderIndex / 2
        next == null -> prev.orderIndex + 100
        else -> (prev.orderIndex + next.orderIndex) / 2
    }

    return list[fromIndex].copy(orderIndex = newPosition)
}

package mx.karlo.kym.ui.viewmodel.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.karlo.kym.data.local.exercise.Exercise
import mx.karlo.kym.data.local.repository.ExerciseRepository

class ExerciseViewModel(
    private val repository: ExerciseRepository
) : ViewModel() {

    val exercises = repository.exercises

    private val _exercise = MutableStateFlow<Exercise?>(null)
    val exercise: StateFlow<Exercise?> = _exercise

    suspend fun getExerciseById(id: Int): Exercise {
            return repository.getExerciseById(id)
    }

    fun addExercise(
        name: String,
        isUnilateral: Boolean,
    ) {
        viewModelScope.launch {
            repository.insert(
                Exercise(
                    name = name,
                    isUnilateral = isUnilateral
                )
            )
        }
    }

    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch {
            repository.delete(exercise)
        }
    }

}
package mx.karlo.kym.ui.viewmodel.routineExercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.karlo.kym.data.local.repository.RoutineExerciseRepository

class RoutineExerciseVMFactory(
    private val repository: RoutineExerciseRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoutineExerciseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RoutineExerciseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
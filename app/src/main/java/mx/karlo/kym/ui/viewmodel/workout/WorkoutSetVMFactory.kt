package mx.karlo.kym.ui.viewmodel.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.karlo.kym.data.local.repository.WorkoutSetRepository

class WorkoutSetVMFactory(
    private val repository: WorkoutSetRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutSetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WorkoutSetViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
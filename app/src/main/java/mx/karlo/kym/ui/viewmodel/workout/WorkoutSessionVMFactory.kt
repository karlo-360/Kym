package mx.karlo.kym.ui.viewmodel.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.karlo.kym.data.local.repository.WorkoutSessionRepository

class WorkoutSessionVMFactory(
    private val repository: WorkoutSessionRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutSessionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WorkoutSessionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
package mx.karlo.kym.data.local.repository

import kotlinx.coroutines.flow.Flow
import mx.karlo.kym.data.local.exercise.Exercise
import mx.karlo.kym.data.local.exercise.ExerciseDao

class ExerciseRepository(
    private val dao: ExerciseDao
) {
    val exercises: Flow<List<Exercise>> =
        dao.getAllExercises()

    suspend fun getExerciseById(id: Int): Exercise {
        return dao.getExerciseById(id)
    }

    suspend fun insert(exercise: Exercise) {
        dao.insert(exercise)
    }

    suspend fun delete(exercise: Exercise) {
        dao.delete(exercise)
    }

}
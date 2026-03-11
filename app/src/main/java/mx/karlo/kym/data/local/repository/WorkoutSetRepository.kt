package mx.karlo.kym.data.local.repository

import kotlinx.coroutines.flow.Flow
import mx.karlo.kym.data.local.workout.WorkoutSet
import mx.karlo.kym.data.local.workout.WorkoutSetDao

class WorkoutSetRepository(
    private val dao: WorkoutSetDao
) {
    suspend fun insert(workoutSet: WorkoutSet) {
        return dao.insert(workoutSet)
    }

    suspend fun delete(workoutSet: WorkoutSet) {
        return dao.delete(workoutSet)
    }

    fun getLastSetByExerciseName(exerciseName: String): Flow<WorkoutSet?> {
        return dao.getLastSetByExerciseName(exerciseName)
    }

    fun getSetsByExerciseName(exerciseName: String): Flow<List<WorkoutSet?>> {
        return dao.getSetsByExerciseName(exerciseName)
    }

    fun getSetsBySessionId(sessionId: Int): Flow<List<WorkoutSet?>> {
        return dao.getSetsBySessionId(sessionId)
    }
}
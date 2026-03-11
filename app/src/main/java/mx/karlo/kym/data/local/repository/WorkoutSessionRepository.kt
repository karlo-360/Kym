package mx.karlo.kym.data.local.repository

import kotlinx.coroutines.flow.Flow
import mx.karlo.kym.data.local.workout.WorkoutSession
import mx.karlo.kym.data.local.workout.WorkoutSessionDao

class WorkoutSessionRepository(
    private val dao: WorkoutSessionDao
) {

    suspend fun insert(workoutSession: WorkoutSession): Int {
        return dao.insert(workoutSession).toInt()
    }

    suspend fun delete(workoutSession: WorkoutSession) {
        return dao.delete(workoutSession)
    }

    val workoutSessions: Flow<List<WorkoutSession>> =
        dao.getAllWorkoutSessions()

    fun getWorkoutSessionById(id: Int): Flow<WorkoutSession?> {
        return dao.getWorkoutSessionById(id)
    }

    suspend fun deactivateSession(id: Int) {
        return dao.deactivateSession(id)
    }

   val lastWorkoutSessionId: Flow<Int?> =
       dao.getLastWorkoutSessionId()

    val activeSession: Flow<WorkoutSession?> =
        dao.getActiveSession()
}
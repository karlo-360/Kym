package mx.karlo.kym.data.local.workout

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutSessionDao {
    @Insert
    suspend fun insert(workoutSession: WorkoutSession): Long

    @Delete
    suspend fun delete(workoutSession: WorkoutSession)

    @Query("SELECT * FROM workout_sessions ORDER BY id DESC")
    fun getAllWorkoutSessions(): Flow<List<WorkoutSession>>

    @Query("SELECT * FROM workout_sessions WHERE id = :id")
    fun getWorkoutSessionById(id: Int): Flow<WorkoutSession?>

    @Query("SELECT MAX(id) FROM workout_sessions")
    fun getLastWorkoutSessionId(): Flow<Int?>

    @Query("SELECT * FROM workout_sessions WHERE isActive = 1 LIMIT 1")
    fun getActiveSession(): Flow<WorkoutSession?>

    @Query("UPDATE workout_sessions SET isActive = 0 WHERE id = :sessionId")
    suspend fun deactivateSession(sessionId: Int)
}
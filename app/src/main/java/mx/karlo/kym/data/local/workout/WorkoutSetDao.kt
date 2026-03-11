package mx.karlo.kym.data.local.workout

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutSetDao {

    @Insert
    suspend fun insert(workoutSet: WorkoutSet)

    @Delete
    suspend fun delete(workoutSet: WorkoutSet)

    @Query("SELECT * FROM workout_sets WHERE exerciseName = :exerciseName LIMIT 1")
    fun getLastSetByExerciseName(exerciseName: String): Flow<WorkoutSet?>

    @Query("SELECT * FROM workout_sets WHERE exerciseName = :exerciseName")
    fun getSetsByExerciseName(exerciseName: String): Flow<List<WorkoutSet?>>

    @Query("SELECT * FROM workout_sets WHERE sessionId = :sessionId")
    fun getSetsBySessionId(sessionId: Int): Flow<List<WorkoutSet?>>

}
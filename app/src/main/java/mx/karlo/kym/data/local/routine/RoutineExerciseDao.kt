package mx.karlo.kym.data.local.routine

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineExerciseDao {
    @Insert
    suspend fun insert(routineExercise: RoutineExercise)

    @Delete
    suspend fun delete(routineExercise: RoutineExercise)

    //@Query("SELECT * FROM routine_exercise ORDER BY id DESC")
    //fun getAllRoutineExercises(): Flow<List<RoutineExercise>>

    @Query("SELECT * FROM routine_exercise WHERE routineId = :routineId ORDER BY orderIndex ASC")
    fun getRoutineExercisesByRoutineId(routineId: Int): Flow<List<RoutineExercise>>

    @Update
    suspend fun updateRoutineExercise(routineExercise: RoutineExercise)

    @Query("""
        UPDATE routine_exercise
        SET reps = :reps,
            weight = :weight
        WHERE id = :id
    """)
    suspend fun updateExerciseLoad(
        id: Int,
        reps: Int,
        weight: Float
    )

    @Query("""
        UPDATE routine_exercise
        SET sets = :sets
        WHERE id = :id
    """)
    suspend fun updateExerciseSets(
        id: Int,
        sets: Int,
    )
}
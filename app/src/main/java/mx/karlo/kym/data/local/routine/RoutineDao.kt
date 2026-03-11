package mx.karlo.kym.data.local.routine

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {
    @Insert
    suspend fun insert(routine: Routine)

    @Delete
    suspend fun delete(routine: Routine)

    @Query("SELECT * FROM routines ORDER BY id DESC")
    fun getAllRoutines(): Flow<List<Routine>>

    @Query("SELECT * FROM routines WHERE id = :id")
    fun getRoutineById(id: Int): Flow<Routine?>

   // @Transaction
   // @Query("SELECT * FROM routines WHERE id = :routineId")
   // fun getRoutineWithExercises(routineId: Int): Flow<RoutineWithExercises?>

   // @Insert
   // suspend fun insertCrossRef(crossRef: RoutineExerciseCrossRef)

   // @Query("""
   // DELETE FROM routine_exercise_cross_ref
   // WHERE routineId = :routineId AND exerciseId = :exerciseId
   // """)
   // suspend fun deleteCrossRef(routineId: Int, exerciseId: Int)
}
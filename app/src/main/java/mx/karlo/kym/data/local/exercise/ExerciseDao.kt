package mx.karlo.kym.data.local.exercise

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Insert
    suspend fun insert(exercise: Exercise)

    @Delete
    suspend fun delete(exercise: Exercise)

    @Query("SELECT * FROM exercises ORDER BY id DESC")
    fun getAllExercises(): Flow<List<Exercise>>

    @Query("SELECT * FROM exercises WHERE id = :id")
    suspend fun getExerciseById(id: Int): Exercise

}
package mx.karlo.kym.data.local.routine

import androidx.room.Entity
import androidx.room.PrimaryKey
import mx.karlo.kym.core.model.Uom

@Entity(tableName = "routine_exercise")
data class RoutineExercise(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val routineId: Int,
    val orderIndex: Int,
    val exerciseName: String,
    val sets: Int = 1,
    val reps: Int = 0,
    val weight: Float = 0f,
    val uom: Uom = Uom.KG,
    val isUnilateral: Boolean = false
)
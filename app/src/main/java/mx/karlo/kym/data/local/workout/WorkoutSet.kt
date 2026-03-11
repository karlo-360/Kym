package mx.karlo.kym.data.local.workout

import androidx.room.Entity
import androidx.room.PrimaryKey
import mx.karlo.kym.core.model.Uom

@Entity(tableName = "workout_sets")
data class WorkoutSet(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val sessionId: Int,
    val exerciseName: String,
    val setNumber: Int,
    val reps: Int,
    val weight: Float,
    val uom: Uom,
    val date: Long = System.currentTimeMillis()
)

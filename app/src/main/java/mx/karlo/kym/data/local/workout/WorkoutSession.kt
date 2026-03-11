package mx.karlo.kym.data.local.workout

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_sessions")
data class WorkoutSession(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: Long = System.currentTimeMillis(),
    val isActive: Boolean = false
)

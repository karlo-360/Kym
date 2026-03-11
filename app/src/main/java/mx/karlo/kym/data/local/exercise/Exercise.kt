package mx.karlo.kym.data.local.exercise

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class Exercise (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val isUnilateral: Boolean,
)

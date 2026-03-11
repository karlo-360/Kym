package mx.karlo.kym.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import mx.karlo.kym.data.local.exercise.Exercise
import mx.karlo.kym.data.local.exercise.ExerciseDao
import mx.karlo.kym.data.local.routine.Routine
import mx.karlo.kym.data.local.routine.RoutineDao
import mx.karlo.kym.data.local.routine.RoutineExercise
import mx.karlo.kym.data.local.routine.RoutineExerciseDao
import mx.karlo.kym.data.local.workout.WorkoutSession
import mx.karlo.kym.data.local.workout.WorkoutSessionDao
import mx.karlo.kym.data.local.workout.WorkoutSet
import mx.karlo.kym.data.local.workout.WorkoutSetDao

@Database(
    entities = [
        Exercise::class,
        Routine::class,
        RoutineExercise::class,
        WorkoutSession::class,
        WorkoutSet::class
               ],
    version = 3
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun routineDao(): RoutineDao

    abstract fun routineExerciseDao(): RoutineExerciseDao

    abstract fun workoutSessionDao(): WorkoutSessionDao

    abstract fun workoutSetDao(): WorkoutSetDao
}
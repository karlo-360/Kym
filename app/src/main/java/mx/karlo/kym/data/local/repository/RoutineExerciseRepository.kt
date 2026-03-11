package mx.karlo.kym.data.local.repository

import kotlinx.coroutines.flow.Flow
import mx.karlo.kym.data.local.routine.RoutineExercise
import mx.karlo.kym.data.local.routine.RoutineExerciseDao

class RoutineExerciseRepository(
    private val dao: RoutineExerciseDao
) {

    //val routineExercises: Flow<List<RoutineExercise>> =
    //    dao.getAllRoutineExercises()

    fun getRoutineExercisesByRoutineId(routineId: Int): Flow<List<RoutineExercise>> {
        return dao.getRoutineExercisesByRoutineId(routineId)
    }

    suspend fun insert(routineExercise: RoutineExercise) {
        dao.insert(routineExercise)
    }

    suspend fun delete(routineExercise: RoutineExercise) {
        dao.delete(routineExercise)
    }

    suspend fun updateRoutineExercise(routineExercise: RoutineExercise) {
        dao.updateRoutineExercise(routineExercise)
    }

    suspend fun updateExerciseLoad(
        id: Int,
        reps: Int,
        weight: Float
    ) {
        dao.updateExerciseLoad(
            id = id,
            reps = reps,
            weight = weight
        )
    }

    suspend fun updateExerciseSets(
        id: Int,
        sets: Int,
    ) {
        dao.updateExerciseSets(
            id = id,
            sets = sets,
        )
    }
}
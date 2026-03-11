package mx.karlo.kym.data.local.repository

import kotlinx.coroutines.flow.Flow
import mx.karlo.kym.data.local.routine.Routine
import mx.karlo.kym.data.local.routine.RoutineDao

class RoutineRepository(
    private val dao: RoutineDao
) {
    val routines: Flow<List<Routine>> =
        dao.getAllRoutines()

    fun getRoutineById(id: Int): Flow<Routine?> {
        return dao.getRoutineById(id)
    }

    suspend fun insert(routine: Routine) {
        dao.insert(routine)
    }

    suspend fun delete(routine: Routine) {
        dao.delete(routine)
    }

    //fun getRoutineWithExercises(id: Int): Flow<RoutineWithExercises?> {
    //    return dao.getRoutineWithExercises(id)
    //}

    //suspend fun addExerciseToRoutine(
    //    routineId: Int,
    //    exerciseId: Int,
    //    sets: Int,
    //    reps: Int,
    //) {
    //    dao.insertCrossRef(
    //        RoutineExerciseCrossRef(
    //            routineId = routineId,
    //            exerciseId = exerciseId,
    //            sets = sets,
    //            reps = reps
    //        )
    //    )
    //}

    //suspend fun removeExerciseToRoutine(
    //    routineId: Int,
    //    exerciseId: Int
    //) {
    //    dao.deleteCrossRef(
    //        routineId = routineId,
    //        exerciseId = exerciseId
    //    )
    //}
}
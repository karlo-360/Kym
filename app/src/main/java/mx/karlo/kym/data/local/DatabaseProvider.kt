package mx.karlo.kym.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: Database? = null

    fun getDatabase(context: Context): Database {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                        context.applicationContext,
                        Database::class.java,
                        "kessentials_database"
                    ).fallbackToDestructiveMigration(true)
                .build()

            INSTANCE = instance
            instance
        }
    }
}
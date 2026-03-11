package mx.karlo.kym

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import mx.karlo.kym.data.local.DatabaseProvider
import mx.karlo.kym.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    private val db by lazy {
        DatabaseProvider.getDatabase(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavigation(db)
        }
    }
}
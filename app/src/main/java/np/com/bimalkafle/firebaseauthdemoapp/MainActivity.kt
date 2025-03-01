package np.com.bimalkafle.firebaseauthdemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import np.com.bimalkafle.firebaseauthdemoapp.data.FirestoreRepository
import np.com.bimalkafle.firebaseauthdemoapp.ui.theme.FirebaseAuthDemoAppTheme

import np.com.bimalkafle.firebaseauthdemoapp.viewmodel.NoteViewModel
import np.com.bimalkafle.firebaseauthdemoapp.viewmodel.WeatherViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // 🔥 Inject WeatherViewModel langsung menggunakan Hilt
    private val weatherViewModel: WeatherViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val noteViewModel = NoteViewModel(FirestoreRepository()) // NoteViewModel tetap manual

        setContent {
            FirebaseAuthDemoAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyAppNavigation(
                        modifier = Modifier.padding(innerPadding),
                        authViewModel = authViewModel,
                        weatherViewModel = weatherViewModel,
                        noteViewModel = noteViewModel
                    )
                }
            }
        }
    }
}

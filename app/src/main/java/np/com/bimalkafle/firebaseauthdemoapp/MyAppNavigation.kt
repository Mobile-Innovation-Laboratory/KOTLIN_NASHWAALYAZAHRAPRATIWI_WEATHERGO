package np.com.bimalkafle.firebaseauthdemoapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import np.com.bimalkafle.firebaseauthdemoapp.pages.AddNoteScreen
import np.com.bimalkafle.firebaseauthdemoapp.pages.FavoriteScreen
import np.com.bimalkafle.firebaseauthdemoapp.pages.HomePage
import np.com.bimalkafle.firebaseauthdemoapp.pages.LoginPage
import np.com.bimalkafle.firebaseauthdemoapp.pages.NoteDetailScreen
import np.com.bimalkafle.firebaseauthdemoapp.pages.NoteEditScreen
import np.com.bimalkafle.firebaseauthdemoapp.pages.NoteListScreen
import np.com.bimalkafle.firebaseauthdemoapp.pages.SignupPage

import np.com.bimalkafle.firebaseauthdemoapp.viewmodel.NoteViewModel

import np.com.bimalkafle.firebaseauthdemoapp.viewmodel.WeatherViewModel

import np.com.bimalkafle.firebaseauthdemoapp.pages.WeatherPage

@Composable
fun MyAppNavigation(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    weatherViewModel: WeatherViewModel,
    noteViewModel: NoteViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginPage(modifier, navController, authViewModel)
        }
        composable("signup") {
            SignupPage(modifier, navController, authViewModel)
        }
        composable("home") {
            HomePage(modifier, navController, authViewModel)
        }
        composable("weather") {
            WeatherPage(weatherViewModel, navController)
        }
        composable("favorite") {
            FavoriteScreen(weatherViewModel = weatherViewModel, navController = navController)
        }



        // 🔥 Navigasi untuk daftar catatan
        composable("notes") {
            NoteListScreen(navController, noteViewModel)
        }
        composable("add_note") {
            AddNoteScreen(navController, noteViewModel)

        }
            // 🔥 Navigasi untuk detail catatan
        composable("note_detail/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId") ?: ""
            NoteDetailScreen(navController, noteViewModel, noteId)
        }

        // 🔥 Navigasi untuk tambah/edit catatan (noteId bisa null untuk mode tambah)
        composable("edit_note/{noteId}?") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")
            NoteEditScreen(navController, noteViewModel, noteId)
        }

    }
}

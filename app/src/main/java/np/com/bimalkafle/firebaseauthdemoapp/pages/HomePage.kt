package np.com.bimalkafle.firebaseauthdemoapp.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock

import androidx.compose.material.icons.filled.Warning

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.util.getColumnIndex
import np.com.bimalkafle.firebaseauthdemoapp.AuthState
import np.com.bimalkafle.firebaseauthdemoapp.AuthViewModel

@Composable

fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }

    // Warna gradient untuk background
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFF6A11CB), Color(0xFF2575FC)), // Daftar warna
        startY = 0f, // Posisi awal gradient (opsional)
        endY = 1000f, // Posisi akhir gradient (opsional)
        tileMode = TileMode.Clamp // Mode tile (opsional)
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gradientBackground) // Terapkan gradient background
    ) {
        // Konten utama ditempatkan di tengah
        Column(
            modifier = Modifier
                .align(Alignment.Center) // Posisikan di tengah
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Judul dengan gaya modern
            Text(
                text = "Selamat Datang di WeatherGo",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,

                        textAlign = TextAlign.Center, // Menengahkan teks secara horizontal
                modifier = Modifier
                    .fillMaxWidth() // Mengisi lebar penuh
                    .padding(bottom = 16.dp)
            )

            // Card untuk tombol "Cek Cuaca"
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Button(
                    onClick = { navController.navigate("weather") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6200EE) // Warna ungu
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Cek Cuaca",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Cek Cuaca",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }

            // Card untuk tombol "Buka Catatan"
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Button(
                    onClick = { navController.navigate("notes") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF03DAC5) // Warna hijau toska
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "Buka Catatan",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Buka Catatan",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        }

        // Tombol Sign Out ditempatkan di bagian bawah
        TextButton(
            onClick = { authViewModel.signout() }, // Lambda untuk onClick
            modifier = Modifier
                .align(Alignment.BottomCenter) // Posisikan di bagian bawah
                .padding(bottom = 32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "Sign Out",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Sign Out",
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}
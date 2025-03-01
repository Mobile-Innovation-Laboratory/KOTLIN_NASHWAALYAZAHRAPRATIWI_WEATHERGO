package np.com.bimalkafle.firebaseauthdemoapp.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import np.com.bimalkafle.firebaseauthdemoapp.api.NetworkResponse
import np.com.bimalkafle.firebaseauthdemoapp.api.WeatherModel
import np.com.bimalkafle.firebaseauthdemoapp.viewmodel.WeatherViewModel


@Composable

fun WeatherPage(viewModel: WeatherViewModel, navController: NavController) {
    var city by remember { mutableStateOf("") }
    val weatherResult by viewModel.weatherResult.observeAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = city,
            onValueChange = { city = it },
            label = { Text(text = "Cari Kota") },
            trailingIcon = {
                IconButton(
                    onClick = {
                        viewModel.getData(city)
                        keyboardController?.hide()
                    }
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Cari Cuaca", tint = Color(0xFF6200EE))
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        when (val result = weatherResult) {
            is NetworkResponse.Error -> {
                Text(text = "Error: ${result.message}", color = Color.Red)
            }
            NetworkResponse.Loading -> {
                CircularProgressIndicator(color = Color(0xFF6200EE))
            }
            is NetworkResponse.Success -> {
                WeatherDetails(data = result.data, viewModel)
            }
            null -> {
                Text(text = "Masukkan nama kota untuk mencari cuaca", color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("favorite") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Lihat Favorit", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun WeatherDetails(data: WeatherModel, viewModel: WeatherViewModel) {
    val isFavorite by viewModel.isCityFavorite(data.location.name).observeAsState(false)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Ikon Lokasi",
                    modifier = Modifier.size(40.dp),
                    tint = Color(0xFF6200EE)
                )
                Text(text = data.location.name, fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6200EE))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "${data.current.temp_c} °C", fontSize = 56.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            AsyncImage(
                modifier = Modifier.size(160.dp),
                model = "https:${data.current.condition.icon}".replace("64x64", "128x128"),
                contentDescription = "Ikon Cuaca"
            )
            Text(text = data.current.condition.text, fontSize = 20.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (isFavorite) {
                        viewModel.removeFavorite(data.location.name)
                    } else {
                        viewModel.addFavorite(data.location.name)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = if (isFavorite) Color.Red else Color(0xFF6200EE)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Tambah ke Favorite",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    if (isFavorite) "Hapus dari Favorit" else "Tambahkan ke Favorit",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

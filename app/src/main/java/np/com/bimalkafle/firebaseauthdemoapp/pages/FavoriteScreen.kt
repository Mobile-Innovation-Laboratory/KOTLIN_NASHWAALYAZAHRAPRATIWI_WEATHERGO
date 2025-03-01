package np.com.bimalkafle.firebaseauthdemoapp.pages

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import np.com.bimalkafle.firebaseauthdemoapp.viewmodel.WeatherViewModel

@Composable
fun FavoriteScreen(
    weatherViewModel: WeatherViewModel,
    navController: NavController
) {
    val favoriteCities by weatherViewModel.favoriteCities.observeAsState(emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF7B1FA2), Color(0xFF512DA8))
                )
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Kota Favorit",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium

            )

            Spacer(modifier = Modifier.height(16.dp))

            if (favoriteCities.isEmpty()) {
                Text(
                    text = "Belum ada kota favorit.",
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.padding(top = 8.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp)
                ) {
                    items(favoriteCities) { city ->
                        FavoriteCityCard(city.cityName) {
                            weatherViewModel.removeFavorite(city.cityName)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteCityCard(cityName: String, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(8.dp, shape = RoundedCornerShape(12.dp))
            .animateContentSize(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = cityName,
                fontSize = 18.sp,
                color = Color(0xFF512DA8),
                style = MaterialTheme.typography.bodyLarge
            )

            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Red, shape = RoundedCornerShape(50))
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Hapus Favorit",
                    tint = Color.White
                )
            }
        }
    }
}

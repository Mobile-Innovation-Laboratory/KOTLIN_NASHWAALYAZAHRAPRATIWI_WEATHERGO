package np.com.bimalkafle.firebaseauthdemoapp.data

import kotlinx.coroutines.flow.Flow
import np.com.bimalkafle.firebaseauthdemoapp.model.FavoriteCity
import np.com.bimalkafle.firebaseauthdemoapp.model.FavoriteCityDao
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val favoriteCityDao: FavoriteCityDao) {
    fun getAllFavorites(): Flow<List<FavoriteCity>> = favoriteCityDao.getAllFavorites()

    suspend fun addFavorite(city: FavoriteCity) {
        favoriteCityDao.insertFavorite(city)
    }

    suspend fun removeFavorite(city: FavoriteCity) {
        favoriteCityDao.deleteFavorite(city)
    }

    fun isCityFavorite(cityName: String): Flow<Boolean> = favoriteCityDao.isCityFavorite(cityName)
}

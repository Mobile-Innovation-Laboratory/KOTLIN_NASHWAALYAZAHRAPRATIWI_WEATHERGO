package np.com.bimalkafle.firebaseauthdemoapp.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow





@Dao
interface FavoriteCityDao {
    @Query("SELECT * FROM favorite_cities")
    fun getAllFavorites(): Flow<List<FavoriteCity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(city: FavoriteCity)

    @Delete
    suspend fun deleteFavorite(city: FavoriteCity)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_cities WHERE cityName = :cityName)")
    fun isCityFavorite(cityName: String): Flow<Boolean>
}

package np.com.bimalkafle.firebaseauthdemoapp.model

// FavoriteCity.kt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_cities")
data class FavoriteCity(
    @PrimaryKey val cityName: String
)
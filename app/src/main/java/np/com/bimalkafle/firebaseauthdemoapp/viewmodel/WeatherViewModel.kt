package np.com.bimalkafle.firebaseauthdemoapp.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import np.com.bimalkafle.firebaseauthdemoapp.api.Constant
import np.com.bimalkafle.firebaseauthdemoapp.api.NetworkResponse
import np.com.bimalkafle.firebaseauthdemoapp.api.WeatherModel
import np.com.bimalkafle.firebaseauthdemoapp.api.RetrofitInstance
import np.com.bimalkafle.firebaseauthdemoapp.data.WeatherRepository
import np.com.bimalkafle.firebaseauthdemoapp.model.FavoriteCity
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult: LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    val favoriteCities: LiveData<List<FavoriteCity>> = repository.getAllFavorites().asLiveData()

    fun getData(city: String) {
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = weatherApi.getWeather(Constant.apiKey, city)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _weatherResult.value = NetworkResponse.Error("Gagal memuat data")
                }
            } catch (e: Exception) {
                _weatherResult.value = NetworkResponse.Error("Gagal memuat data")
            }
        }
    }

    fun addFavorite(city: String) {
        viewModelScope.launch {
            repository.addFavorite(FavoriteCity(city))
        }
    }

    fun removeFavorite(city: String) {
        viewModelScope.launch {
            repository.removeFavorite(FavoriteCity(city))
        }
    }

    fun isCityFavorite(city: String): LiveData<Boolean> {
        return repository.isCityFavorite(city).asLiveData()
    }
}

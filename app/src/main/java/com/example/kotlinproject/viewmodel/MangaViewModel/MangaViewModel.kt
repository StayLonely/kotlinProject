import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinproject.models.MangaFromApi.MangaFromApi
import com.example.kotlinproject.utils.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MangaViewModel : ViewModel() {
    private val _mangaResponse = mutableStateOf<MangaFromApi?>(null)
    val mangaResponse = _mangaResponse
    init {
        fetchManga()
    }

    private fun fetchManga() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getManga()
                if (response.isSuccessful && response.body() != null) {
                    _mangaResponse.value = response.body()
                }
            } catch (e: Exception) {

            }
        }
    }

}
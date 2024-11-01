import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinproject.dataStore.DataStoreManager
import com.example.kotlinproject.models.FilterSettings.FilterSettings
import com.example.kotlinproject.models.MangaFromApi.MangaFromApi
import com.example.kotlinproject.utils.RetrofitInstance
import com.example.kotlinproject.utils.Util.getTagIdsByNames
import com.example.kotlinproject.utils.Util.getTagNamesByIds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MangaViewModel : ViewModel() {
    private val _mangaResponse = mutableStateOf<MangaFromApi?>(null)
    val mangaResponse: State<MangaFromApi?> get() = _mangaResponse

    fun fetchManga(
        savedStatus: List<String>,
        savedContentRating: List<String>,
        savedTags: List<String>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getManga(savedStatus, savedContentRating, getTagIdsByNames(savedTags))
                if (response.isSuccessful && response.body() != null) {
                    _mangaResponse.value = response.body()
                }
            } catch (e: Exception) {

            }
        }
    }
}
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinproject.dao.MangaDao
import com.example.kotlinproject.dataStore.DataStoreManager
import com.example.kotlinproject.models.FilterSettings.FilterSettings
import com.example.kotlinproject.models.MangaEntity.MangaEntity
import com.example.kotlinproject.models.MangaFromApi.MangaFromApi
import com.example.kotlinproject.utils.RetrofitInstance
import com.example.kotlinproject.utils.Util.getTagIdsByNames
import com.example.kotlinproject.utils.Util.getTagNamesByIds
import com.example.kotlinproject.utils.Util.isFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MangaViewModel : ViewModel() {
    private val _mangaResponse = mutableStateOf<MangaFromApi?>(null)
    val mangaResponse: State<MangaFromApi?> get() = _mangaResponse



    fun fetchManga(dataStoreManager: DataStoreManager) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                dataStoreManager.getSettings().collect { settings ->

                    val savedStatus = settings.status.split(",").filter { it.isNotBlank() }
                    val savedContentRating = settings.contentRating.split(",").filter { it.isNotBlank() }
                    val savedTags = getTagIdsByNames(settings.tags)

                    isFilter.value = savedStatus.isNotEmpty() || savedContentRating.isNotEmpty() || (savedTags != null && savedTags.isNotEmpty())


                    val response = RetrofitInstance.api.getManga(savedStatus, savedContentRating, savedTags)

                    if (response.isSuccessful && response.body() != null) {
                        _mangaResponse.value = response.body() // Обновляем состояние
                    } else {
                    }
                }
            } catch (e: Exception) {
            }
        }
    }
}
package com.example.kotlinproject.viewmodel.MangaViewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kotlinproject.models.MangaFromApi.MangaFromApi
import com.example.kotlinproject.models.MangaItemFromApi.MangaItemFromApi
import com.example.kotlinproject.utils.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MangaItemView(private val mangaId: String) : ViewModel() {
    private val _mangaResponse = mutableStateOf<MangaItemFromApi?>(null)
    val mangaResponse= _mangaResponse

    init {
        fetchManga()
    }

    private fun fetchManga() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getMangaWithId(mangaId)
                if (response.isSuccessful && response.body() != null) {
                    _mangaResponse.value = response.body()
                } else {
                    Log.d("ERROR", "Response was not successful: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("ERROR", "Exception occurred: ${e.message}", e)
            } catch (e: NullPointerException){
                Log.e("ERROR", "Exception occurred: ${e.message}", e)

            }
        }
    }
}


package cm.watchlist.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import android.content.Context
import androidx.lifecycle.viewModelScope
import cm.watchlist.R
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.io.IOException


class WatchlistViewModel: ViewModel(){
    private val _state= MutableStateFlow(WatchListState())
    val state: StateFlow<WatchListState> = _state.asStateFlow()

    fun loadFromJson(context: Context) {
        if(_state.value.isLoaded){
            return
        }
        val jsonFile=context.resources.openRawResource(R.raw.mock_data)
        val jsonString = jsonFile.bufferedReader().use { it.readText() }

        val gson = Gson()
        _state.value=_state.value.copy(isLoaded = true,items=gson.fromJson(jsonString, Array<WatchListItem>::class.java).toList())
    }

    fun toggleCheckbox(item: WatchListItem) {
        viewModelScope.launch {
            val updatedItems = _state.value.items.map {
                if (it.id == item.id) {
                    it.toggle()
                } else {
                    it
                }
            }
            _state.value = _state.value.copy(items = updatedItems)
        }
    }
}
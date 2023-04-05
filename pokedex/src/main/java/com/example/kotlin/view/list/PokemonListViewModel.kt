package com.example.kotlin.view.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.kotlin.data.PokemonApi
import com.example.kotlin.data.PokemonListRepository
import com.example.kotlin.data.model.PokemonListEntryResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonListViewModel : ViewModel() {

    val pokemonFlow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(PokemonListRepository.PER_PAGE)
    ) {
        PokemonListPagingSource()
    }.flow.cachedIn(viewModelScope)

    val pokemonDetailLiveData = MutableLiveData<PokemonApi.PokemonDetail>()

    fun getPokemonDetail(pokemonName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            pokemonDetailLiveData.postValue(PokemonListRepository.getPokemonDetail(pokemonName = pokemonName))
        }
    }

}
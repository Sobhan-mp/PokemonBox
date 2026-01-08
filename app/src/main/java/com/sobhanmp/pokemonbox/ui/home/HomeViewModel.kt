package com.sobhanmp.pokemonbox.ui.home

import androidx.lifecycle.viewModelScope
import com.sobhanmp.pokemonbox.app.base.BaseViewModel
import com.sobhanmp.pokemonbox.domain.entity.Pokemon
import com.sobhanmp.pokemonbox.domain.repo.PokemonRepository
import com.sobhanmp.pokemonbox.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
): BaseViewModel(){

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private var offset: Int = 0
    private val masterPokemonList = mutableListOf<Pokemon>()

    init {
        getPokemons()
    }

    fun filterPokemons() {
        val query = state.value.searchText.trim().lowercase()

        val filteredList = if (query.isEmpty()) {
            masterPokemonList.toList()
        } else {
            masterPokemonList.filter { pokemon ->
                pokemon.name.lowercase().contains(query) ||
                        pokemon.types.any { type -> type.lowercase().contains(query) }
            }
        }

        _state.update {
            it.copy(pokemons = filteredList)
        }
    }

    fun onSearchTextChange(newText: String) {
        _state.update { it.copy(searchText = newText) }
        filterPokemons()
    }

    fun getPokemons() {
        if (_state.value.isLoading) return

        viewModelScope.launch {
            pokemonRepository.getPokemons(offset).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        val newUniqueItems = resource.data.filter { new ->
                            masterPokemonList.none { old -> old.name == new.name }
                        }
                        masterPokemonList.addAll(newUniqueItems)

                        if (newUniqueItems.isNotEmpty()) offset += 20
                        filterPokemons()
                        _state.update { it.copy(isLoading = false) }
                    }
                    is Resource.Failure -> {
                        _state.update { it.copy(isLoading = false, error = resource.message) }
                    }
                }
            }
        }
    }
}
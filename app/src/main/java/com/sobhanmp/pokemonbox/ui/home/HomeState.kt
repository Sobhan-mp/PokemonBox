package com.sobhanmp.pokemonbox.ui.home

import com.sobhanmp.pokemonbox.domain.entity.Pokemon

data class HomeState(
    val searchText: String = "",
    val pokemons: List<Pokemon> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

package com.sobhanmp.pokemonbox.domain.repo

import com.sobhanmp.pokemonbox.domain.entity.Pokemon
import com.sobhanmp.pokemonbox.util.Resource
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getPokemons(offset: Int): Flow<Resource<List<Pokemon>>>
}
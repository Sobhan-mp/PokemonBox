package com.sobhanmp.pokemonbox.infra.repo

import android.util.Log
import com.sobhanmp.pokemonbox.util.Resource
import com.sobhanmp.pokemonbox.domain.entity.Pokemon
import com.sobhanmp.pokemonbox.domain.repo.PokemonRepository
import com.sobhanmp.pokemonbox.infra.api.PokemonApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(private val api: PokemonApi): PokemonRepository {

    override suspend fun getPokemons(offset: Int): Flow<Resource<List<Pokemon>>> = flow {
        emit(Resource.Loading)
        try {
            val pokemonList = api.getPokemonList(offset = offset, limit = 20)

            val pokemons = coroutineScope { pokemonList.results.map { res ->

                    async {
                        val pokemonDetails = api.getPokemonDetails(res.name)
                        val pokemonSpecies = api.getPokemonSpecies(res.name)

                        Pokemon(
                            name = res.name.replaceFirstChar { it.uppercase() },
                            imageUrl = pokemonDetails.sprites.other.officialArtwork.frontDefault,
                            types = pokemonDetails.types.map { it.type.name },
                            description = pokemonSpecies.flavorTextEntries.firstOrNull { it.language.name == "en" }?.flavorText
                                ?: ""
                        )
                    }

                }
            }.awaitAll()
            emit(Resource.Success(pokemons))
        } catch (e: Exception) {
            emit(Resource.Failure(message = "There is an Error in calling the api. Check the logs!"))
            Log.e("ApiError", "getPokemonsError: ${e.message}")
        }
    }


}
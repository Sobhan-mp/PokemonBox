package com.sobhanmp.pokemonbox.infra.api

import com.sobhanmp.pokemonbox.infra.api.model.PokemonDetailsResponse
import com.sobhanmp.pokemonbox.infra.api.model.PokemonResponse
import com.sobhanmp.pokemonbox.infra.api.model.SpeciesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int, @Query("limit") limit: Int = 20
    ): PokemonResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonDetails(
        @Path("name") name: String
    ): PokemonDetailsResponse

    @GET("pokemon-species/{name}")
    suspend fun getPokemonSpecies(
        @Path("name") name: String
    ): SpeciesResponse
}
package com.sobhanmp.pokemonbox.infra.api.model

data class PokemonResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)

data class Result(
    val name: String,
    val url: String
)



package com.sobhanmp.pokemonbox.domain.entity

data class Pokemon(
    val name: String,
    val imageUrl: String,
    val types: List<String>,
    val description: String,
    val isFavorite: Boolean = false
)
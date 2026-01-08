package com.sobhanmp.pokemonbox.infra.api.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class PokemonDetailsResponse(
    val sprites: Sprites,
    val types: List<TypeSlot>
)

data class Sprites(
    val other: OtherSprites
)

@Serializable
data class OtherSprites(
    @SerializedName("official-artwork") val officialArtwork: Artwork
)


data class Artwork(
    @SerializedName("front_default") val frontDefault: String
)

data class TypeSlot(
    val type: TypeInfo
)

data class TypeInfo(
    val name: String
)
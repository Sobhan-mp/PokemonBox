package com.sobhanmp.pokemonbox.ui.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sobhanmp.pokemonbox.domain.entity.Pokemon

@Composable
fun PokemonCard(pokemon: Pokemon) {
    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = pokemon.imageUrl,
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f) ,verticalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.CenterVertically)) {
                Text(text = pokemon.name, style = MaterialTheme.typography.titleLarge)
                Row() {
                    for (type in pokemon.types)
                        Card(modifier = Modifier.padding(horizontal = 2.dp)) { Text(modifier = Modifier.padding(vertical = 2.dp , horizontal = 8.dp), text = type.replaceFirstChar { it.uppercase() }) }
                }
                val cleanDescription = pokemon.description.replace("\n", " ").replace("\r", " ")
                Text(modifier = Modifier.fillMaxWidth(), text = cleanDescription, color = Color.Gray, maxLines = 2, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}
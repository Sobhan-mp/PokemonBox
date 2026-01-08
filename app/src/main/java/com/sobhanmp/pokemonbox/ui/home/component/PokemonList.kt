package com.sobhanmp.pokemonbox.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sobhanmp.pokemonbox.domain.entity.Pokemon

@Composable
fun PokemonList(
    pokemons: List<Pokemon>,
    disablePaging: Boolean,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    val reachedEnd by remember(pokemons.size) {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != null && lastVisibleItem.index == pokemons.size - 1
        }
    }

    LaunchedEffect(reachedEnd) {
        if (reachedEnd && !disablePaging) {
            onLoadMore()
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
    ) {

        itemsIndexed(pokemons) { index, pokemon ->
            PokemonCard(pokemon = pokemon)
            HorizontalDivider(Modifier
                .fillMaxWidth()
                .background(Color.LightGray))
        }
    }
}

package com.sobhanmp.pokemonbox.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.sobhanmp.pokemonbox.R
import com.sobhanmp.pokemonbox.ui.home.component.PokemonList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Pokemon Box") })
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Column() {
            OutlinedTextField(leadingIcon = { Icon(painter = painterResource(R.drawable.baseline_search_24),"") },modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),value = state.searchText, onValueChange = {viewModel.onSearchTextChange(it)}, label = {Text("Search")} , placeholder = {Text("Name or Type")})
            PokemonList(
                pokemons = state.pokemons,
                disablePaging = state.isLoading || !state.searchText.isEmpty() ,
                onLoadMore = { viewModel.getPokemons() },
                modifier = Modifier.fillMaxHeight()

            )
            }

            if (state.isLoading && state.pokemons.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
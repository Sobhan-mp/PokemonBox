package com.sobhanmp.pokemonbox.ui.home

import androidx.lifecycle.viewmodel.compose.viewModel
import app.cash.turbine.test
import com.sobhanmp.pokemonbox.domain.entity.Pokemon
import com.sobhanmp.pokemonbox.domain.repo.PokemonRepository
import com.sobhanmp.pokemonbox.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var pokemonRepository: PokemonRepository

    private lateinit var homeViewModel: HomeViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        pokemonRepository = mockk()
        homeViewModel = HomeViewModel(pokemonRepository)
        coEvery { pokemonRepository.getPokemons(0) } returns flowOf(Resource.Success(emptyList()))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when loadPokemon is called, state emits loading then success`() = runTest {
        val fakePokemonList = listOf(
            Pokemon("Pikachu", "imageUrl", listOf("Electric"), "description"),
            Pokemon("Bulbasaur", "imageUrl", listOf("Grass", "Poison"), "description"),
            Pokemon("Charmander", "imageUrl", listOf("Fire"), "description")
        )

        coEvery { pokemonRepository.getPokemons(0) } returns flowOf(
            Resource.Loading,
            Resource.Success(fakePokemonList)
        )

        homeViewModel.state.test {
            val initialState = awaitItem()
            assertFalse(initialState.isLoading)
            assertTrue(initialState.pokemons.isEmpty())

            homeViewModel.getPokemons()

            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            val successState = awaitItem()
            assertEquals(fakePokemonList, successState.pokemons)

            cancelAndConsumeRemainingEvents()
        }


    }

    @Test
    fun `when typing in search field, the state changes`() = runTest {

        homeViewModel.state.test {
            val state = awaitItem()
            val text = "Search"
            homeViewModel.onSearchTextChange(text)
            val newState = awaitItem()
            assertEquals(text,newState.searchText)
        }

    }


}
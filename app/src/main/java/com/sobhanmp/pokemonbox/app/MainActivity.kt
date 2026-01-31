package com.sobhanmp.pokemonbox.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.sobhanmp.pokemonbox.ui.home.HomeScreen
import com.sobhanmp.pokemonbox.ui.home.navigation.Home
import com.sobhanmp.pokemonbox.ui.theme.PokemonBOXTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonBOXTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val backStack = remember { mutableStateListOf<NavKey>(Home) }
                    NavDisplay(
                        backStack = backStack,
                        onBack = {backStack.removeLastOrNull()},
                        entryProvider = entryProvider {
                            entry<Home> { HomeScreen() }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokemonBOXTheme {
        Greeting("Android")
    }
}
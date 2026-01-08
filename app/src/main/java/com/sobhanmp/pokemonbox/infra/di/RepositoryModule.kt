package com.sobhanmp.pokemonbox.infra.di

import com.sobhanmp.pokemonbox.domain.repo.PokemonRepository
import com.sobhanmp.pokemonbox.infra.repo.PokemonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun providesPokemonRepository(pokemonRepositoryImpl: PokemonRepositoryImpl): PokemonRepository
}
package com.example.filmesfavoritos.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable data object Splash : Route
    @Serializable data object Login : Route
    @Serializable data object Home : Route
    @Serializable data object Filmes : Route
    @Serializable data object Favoritos : Route
    @Serializable data object Perfil : Route
    @Serializable data class Detalhe(val filmeId: Int) : Route
}

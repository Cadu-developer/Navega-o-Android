package com.example.filmesfavoritos.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class Filme(
    val id: Int,
    val titulo: String,
    val genero: String,
    val ano: Int,
    val descricao: String
)

class FilmesViewModel : ViewModel() {
    private val _filmes = MutableStateFlow(
        listOf(
            Filme(1, "Interestelar", "Ficção científica", 2014, "Uma equipe viaja pelo espaço em busca de um novo lar para a humanidade."),
            Filme(2, "O Batman", "Ação", 2022, "Batman investiga uma sequência de crimes que ameaça Gotham City."),
            Filme(3, "Homem-Aranha", "Aventura", 2018, "Um jovem herói aprende a lidar com grandes responsabilidades."),
            Filme(4, "Duna", "Ficção científica", 2021, "Uma jornada épica pelo planeta desértico Arrakis.")
        )
    )
    val filmes = _filmes.asStateFlow()

    private val _favoritos = MutableStateFlow(setOf<Int>())
    val favoritos = _favoritos.asStateFlow()

    fun alternarFavorito(id: Int) {
        _favoritos.value = if (id in _favoritos.value) {
            _favoritos.value - id
        } else {
            _favoritos.value + id
        }
    }

    fun filmePorId(id: Int): Filme? = _filmes.value.find { it.id == id }
}

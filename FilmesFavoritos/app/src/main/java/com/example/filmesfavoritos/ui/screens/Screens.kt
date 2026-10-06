package com.example.filmesfavoritos.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.navigation.toRoute
import com.example.filmesfavoritos.navigation.Route
import com.example.filmesfavoritos.viewmodel.FilmesViewModel

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1200)
        onFinished()
    }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("🎬 Filmes Favoritos", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun LoginScreen(onLogin: () -> Unit) {
    Column(
        Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Filmes Favoritos", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(32.dp))
        Button(onClick = onLogin) { Text("Entrar") }
    }
}

@Composable
fun HomeScreen(navController: NavHostController, vm: FilmesViewModel) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val current = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                BottomItem("Filmes", Icons.Default.Movie, current?.contains("Filmes") == true) {
                    navController.navigate(Route.Filmes) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(Route.Filmes) { saveState = true }
                    }
                }
                BottomItem("Favoritos", Icons.Default.Star, current?.contains("Favoritos") == true) {
                    navController.navigate(Route.Favoritos) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(Route.Filmes) { saveState = true }
                    }
                }
                BottomItem("Perfil", Icons.Default.Person, current?.contains("Perfil") == true) {
                    navController.navigate(Route.Perfil) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(Route.Filmes) { saveState = true }
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Route.Filmes,
            modifier = Modifier.padding(padding)
        ) {
            composable<Route.Filmes> {
                FilmeListScreen(navController, vm, false)
            }
            composable<Route.Favoritos> {
                FilmeListScreen(navController, vm, true)
            }
            composable<Route.Perfil> {
                ProfileScreen()
            }
        }
    }
}

@Composable
private fun BottomItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = { Icon(icon, contentDescription = label) },
        label = { Text(label) }
    )
}

@Composable
fun FilmeListScreen(navController: NavHostController, vm: FilmesViewModel, onlyFavorites: Boolean) {
    val filmes by vm.filmes.collectAsState()
    val favoritos by vm.favoritos.collectAsState()
    val lista = if (onlyFavorites) filmes.filter { it.id in favoritos } else filmes

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            if (onlyFavorites) "Meus Favoritos" else "Filmes",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(16.dp))

        if (lista.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nenhum filme favorito ainda.")
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(lista, key = { it.id }) { filme ->
                    Card(
                        Modifier.fillMaxWidth().clickable {
                            navController.navigate(Route.Detalhe(filme.id))
                        }
                    ) {
                        Row(
                            Modifier.fillMaxWidth().padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(Modifier.weight(1f)) {
                                Text(filme.titulo, style = MaterialTheme.typography.titleLarge)
                                Text("${filme.genero} • ${filme.ano}")
                            }
                            IconButton(onClick = { vm.alternarFavorito(filme.id) }) {
                                Icon(
                                    if (filme.id in favoritos) Icons.Default.Star else Icons.Default.StarBorder,
                                    contentDescription = "Favoritar ${filme.titulo}"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailScreen(navController: NavHostController, vm: FilmesViewModel) {
    val route = navController.currentBackStackEntry?.toRoute<Route.Detalhe>()
    val filme = route?.let { vm.filmePorId(it.filmeId) }

    Column(Modifier.fillMaxSize().padding(24.dp)) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
        }

        if (filme == null) {
            Text("Filme não encontrado")
        } else {
            Text(filme.titulo, style = MaterialTheme.typography.headlineLarge)
            Spacer(Modifier.height(12.dp))
            Text("${filme.genero} • ${filme.ano}")
            Spacer(Modifier.height(20.dp))
            Text(filme.descricao)
            Spacer(Modifier.height(24.dp))
            Button(onClick = { vm.alternarFavorito(filme.id) }) {
                Text("Alternar favorito")
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Text("Perfil", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        Text("Usuário do aplicativo")
    }
}

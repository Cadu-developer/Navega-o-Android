package com.example.filmesfavoritos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.filmesfavoritos.navigation.Route
import com.example.filmesfavoritos.ui.screens.*
import com.example.filmesfavoritos.ui.theme.FilmesFavoritosTheme
import com.example.filmesfavoritos.viewmodel.FilmesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FilmesFavoritosTheme {
                val vm: FilmesViewModel = viewModel()
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Route.Splash
                ) {
                    composable<Route.Splash> {
                        SplashScreen {
                            val logado = false
                            navController.navigate(if (logado) Route.Home else Route.Login) {
                                popUpTo(Route.Splash) { inclusive = true }
                            }
                        }
                    }

                    composable<Route.Login> {
                        LoginScreen {
                            navController.navigate(Route.Home) {
                                popUpTo(Route.Login) { inclusive = true }
                            }
                        }
                    }

                    composable<Route.Home> {
                        HomeScreen(navController, vm)
                    }

                    composable<Route.Detalhe> {
                        DetailScreen(navController, vm)
                    }
                }
            }
        }
    }
}

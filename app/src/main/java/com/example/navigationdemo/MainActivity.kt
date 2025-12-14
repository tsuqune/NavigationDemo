package com.example.navigationdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.navigationdemo.screens.Home
import com.example.navigationdemo.screens.Profile
import com.example.navigationdemo.screens.Welcome

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(HomeScreen)

    val onNavigation: (NavKey) -> Unit = { navKey ->
        backStack.add(navKey)
    }

    val onClearBackStack: () -> Unit = {
        while (backStack.size > 1) {
            backStack.removeLastOrNull()
        }
    }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            // Экран Home
            entry<HomeScreen> {
                Home(onNavigation)
            }

            // Экран Welcome с передачей аргумента name
            entry<WelcomeScreen>(
                metadata = mapOf("extraDataKey" to "extraDataValue")
            ) { key ->
                Welcome(onNavigation, key.name)
            }

            // Экран Profile
            entry<ProfileScreen> {
                Profile(onClearBackStack)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    NavigationDemoTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MainScreen(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
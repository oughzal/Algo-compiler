package com.algocompiler.mobile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.algocompiler.mobile.ui.screens.EditorScreen
import com.algocompiler.mobile.ui.screens.FileExplorerScreen
import com.algocompiler.mobile.viewmodel.AlgoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlgoCompilerApp(viewModel: AlgoViewModel = viewModel()) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Fichiers", "Éditeur", "Console")
    val icons = listOf(
        Icons.Filled.Folder,
        Icons.Filled.Edit,
        Icons.Filled.Terminal
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Algo Compiler") },
                actions = {
                    if (selectedTab == 1) { // Editor tab
                        IconButton(onClick = { viewModel.saveCurrentFile() }) {
                            Icon(Icons.Filled.Save, contentDescription = "Enregistrer")
                        }
                        IconButton(onClick = { viewModel.runAlgorithm() }) {
                            Icon(Icons.Filled.PlayArrow, contentDescription = "Exécuter")
                        }
                    }
                    IconButton(onClick = { /* Settings */ }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Paramètres")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = title) },
                        label = { Text(title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> FileExplorerScreen(viewModel)
                1 -> EditorScreen(viewModel)
                2 -> ConsoleScreen(viewModel)
            }
        }
    }
}

@Composable
fun ConsoleScreen(viewModel: AlgoViewModel) {
    val consoleOutput by viewModel.consoleOutput.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Console",
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(onClick = { viewModel.clearConsole() }) {
                    Icon(Icons.Filled.Clear, contentDescription = "Effacer")
                }
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = consoleOutput,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


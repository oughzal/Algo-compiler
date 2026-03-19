package com.algocompiler.mobile.ui.screens
}
    }
        }
            )
                enabled = enabled
                ),
                    fontFamily = FontFamily.Monospace
                    fontSize = 14.sp,
                    color = Color(0xFFCCCCCC),
                textStyle = TextStyle(
                    .padding(8.dp),
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                modifier = Modifier
                onValueChange = onContentChange,
                value = content,
            BasicTextField(
            // Code area

            }
                }
                    )
                        )
                            fontFamily = FontFamily.Monospace
                            fontSize = 14.sp,
                            color = Color(0xFF858585),
                        style = TextStyle(
                        text = "${index + 1}",
                    Text(
                lines.forEachIndexed { index, _ ->
                val lines = content.split("\n")
            ) {
                    .padding(4.dp)
                    .verticalScroll(scrollState)
                    .background(Color(0xFF252526))
                    .fillMaxHeight()
                    .width(40.dp)
                modifier = Modifier
            Column(
            // Line numbers
        Row(modifier = Modifier.fillMaxSize()) {
    ) {
        color = Color(0xFF1E1E1E)
        modifier = modifier,
    Surface(

    val scrollState = rememberScrollState()
) {
    enabled: Boolean = true
    modifier: Modifier = Modifier,
    onContentChange: (String) -> Unit,
    content: String,
fun CodeEditor(
@Composable

}
    }
        }
            }
                }
                    }
                        )
                            modifier = Modifier.size(24.dp)
                        CircularProgressIndicator(
                    if (isRunning) {
                    }
                        Text("Exécuter")
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Filled.PlayArrow, contentDescription = null)
                    ) {
                        enabled = !isRunning
                        onClick = { viewModel.runAlgorithm() },
                    Button(
                    }
                        Text("Enregistrer")
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Filled.Save, contentDescription = null)
                    ) {
                        enabled = !isRunning
                        onClick = { viewModel.saveCurrentFile() },
                    Button(
                ) {
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                        .horizontalScroll(rememberScrollState()),
                        .padding(8.dp)
                    modifier = Modifier
                Row(
            ) {
                modifier = Modifier.fillMaxWidth()
                color = MaterialTheme.colorScheme.surfaceVariant,
            Surface(
            // Action buttons

            )
                enabled = !isRunning
                    .weight(1f),
                    .fillMaxWidth()
                modifier = Modifier
                onContentChange = { viewModel.updateEditorContent(it) },
                content = editorContent,
            CodeEditor(
            // Editor

            }
                }
                    )
                        style = MaterialTheme.typography.titleSmall
                        currentFile?.name ?: "",
                    Text(
                    Spacer(modifier = Modifier.width(8.dp))
                    )
                        modifier = Modifier.size(20.dp)
                        contentDescription = null,
                        Icons.Filled.Description,
                    Icon(
                ) {
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    modifier = Modifier.padding(8.dp),
                Row(
            ) {
                modifier = Modifier.fillMaxWidth()
                color = MaterialTheme.colorScheme.primaryContainer,
            Surface(
            // File name bar
        Column(modifier = Modifier.fillMaxSize()) {
    } else {
        }
            }
                )
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                    style = MaterialTheme.typography.bodyMedium,
                    "Sélectionnez un fichier dans l'onglet Fichiers",
                Text(
                )
                    style = MaterialTheme.typography.titleMedium
                    "Aucun fichier ouvert",
                Text(
                Spacer(modifier = Modifier.height(16.dp))
                )
                    tint = MaterialTheme.colorScheme.primary
                    modifier = Modifier.size(64.dp),
                    contentDescription = null,
                    Icons.Filled.Description,
                Icon(
            Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
        ) {
            contentAlignment = androidx.compose.ui.Alignment.Center
            modifier = Modifier.fillMaxSize(),
        Box(
    if (currentFile == null) {

    val isRunning by viewModel.isRunning.collectAsState()
    val editorContent by viewModel.editorContent.collectAsState()
    val currentFile by viewModel.currentFile.collectAsState()
fun EditorScreen(viewModel: AlgoViewModel) {
@Composable

import com.algocompiler.mobile.viewmodel.AlgoViewModel
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.background



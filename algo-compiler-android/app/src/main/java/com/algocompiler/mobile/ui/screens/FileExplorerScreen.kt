package com.algocompiler.mobile.ui.screens

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
import com.algocompiler.mobile.data.AlgoFile
import com.algocompiler.mobile.viewmodel.AlgoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileExplorerScreen(viewModel: AlgoViewModel) {
    val files by viewModel.files.collectAsState()
    var showNewFileDialog by remember { mutableStateOf(false) }
    var showNewFolderDialog by remember { mutableStateOf(false) }
    var selectedFile by remember { mutableStateOf<AlgoFile?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showRenameDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadFiles()
    }

    Scaffold(
        floatingActionButton = {
            Column {
                FloatingActionButton(
                    onClick = { showNewFolderDialog = true },
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Icon(Icons.Filled.CreateNewFolder, contentDescription = "Nouveau dossier")
                }
                FloatingActionButton(
                    onClick = { showNewFileDialog = true }
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Nouveau fichier")
                }
            }
        }
    ) { padding ->
        if (files.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Filled.Folder,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Aucun fichier",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        "Créez votre premier fichier .algo",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(files) { file ->
                    FileItem(
                        file = file,
                        onClick = {
                            if (file.isDirectory) {
                                // TODO: Navigate to folder
                            } else {
                                viewModel.openFile(file)
                            }
                        },
                        onLongClick = {
                            selectedFile = file
                        }
                    )
                    if (selectedFile == file) {
                        FileOptionsMenu(
                            file = file,
                            onDismiss = { selectedFile = null },
                            onRename = {
                                showRenameDialog = true
                            },
                            onDelete = {
                                showDeleteDialog = true
                            }
                        )
                    }
                }
            }
        }
    }

    if (showNewFileDialog) {
        CreateFileDialog(
            onDismiss = { showNewFileDialog = false },
            onConfirm = { name ->
                viewModel.createFile(name)
                showNewFileDialog = false
            }
        )
    }

    if (showNewFolderDialog) {
        CreateFolderDialog(
            onDismiss = { showNewFolderDialog = false },
            onConfirm = { name ->
                viewModel.createFolder(name)
                showNewFolderDialog = false
            }
        )
    }

    if (showDeleteDialog && selectedFile != null) {
        DeleteConfirmDialog(
            file = selectedFile!!,
            onDismiss = {
                showDeleteDialog = false
                selectedFile = null
            },
            onConfirm = {
                viewModel.deleteFile(selectedFile!!)
                showDeleteDialog = false
                selectedFile = null
            }
        )
    }

    if (showRenameDialog && selectedFile != null) {
        RenameDialog(
            file = selectedFile!!,
            onDismiss = {
                showRenameDialog = false
                selectedFile = null
            },
            onConfirm = { newName ->
                viewModel.renameFile(selectedFile!!, newName)
                showRenameDialog = false
                selectedFile = null
            }
        )
    }
}

@Composable
fun FileItem(
    file: AlgoFile,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    ListItem(
        headlineContent = { Text(file.name) },
        leadingContent = {
            Icon(
                if (file.isDirectory) Icons.Filled.Folder else Icons.Filled.Description,
                contentDescription = null,
                tint = if (file.isDirectory) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.secondary
                }
            )
        },
        trailingContent = {
            IconButton(onClick = onLongClick) {
                Icon(Icons.Filled.MoreVert, contentDescription = "Options")
            }
        },
        modifier = Modifier.clickable(onClick = onClick)
    )
    Divider()
}

@Composable
fun FileOptionsMenu(
    file: AlgoFile,
    onDismiss: () -> Unit,
    onRename: () -> Unit,
    onDelete: () -> Unit
) {
    DropdownMenu(
        expanded = true,
        onDismissRequest = onDismiss
    ) {
        DropdownMenuItem(
            text = { Text("Renommer") },
            onClick = {
                onDismiss()
                onRename()
            },
            leadingIcon = { Icon(Icons.Filled.Edit, contentDescription = null) }
        )
        DropdownMenuItem(
            text = { Text("Supprimer") },
            onClick = {
                onDismiss()
                onDelete()
            },
            leadingIcon = { Icon(Icons.Filled.Delete, contentDescription = null) }
        )
    }
}

@Composable
fun CreateFileDialog(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var fileName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nouveau fichier") },
        text = {
            OutlinedTextField(
                value = fileName,
                onValueChange = { fileName = it },
                label = { Text("Nom du fichier") },
                singleLine = true,
                suffix = { Text(".algo") }
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(fileName) },
                enabled = fileName.isNotBlank()
            ) {
                Text("Créer")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}

@Composable
fun CreateFolderDialog(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var folderName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nouveau dossier") },
        text = {
            OutlinedTextField(
                value = folderName,
                onValueChange = { folderName = it },
                label = { Text("Nom du dossier") },
                singleLine = true
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(folderName) },
                enabled = folderName.isNotBlank()
            ) {
                Text("Créer")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}

@Composable
fun DeleteConfirmDialog(file: AlgoFile, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirmer la suppression") },
        text = { Text("Êtes-vous sûr de vouloir supprimer ${file.name} ?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Supprimer")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}

@Composable
fun RenameDialog(file: AlgoFile, onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var newName by remember { mutableStateOf(file.name) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Renommer") },
        text = {
            OutlinedTextField(
                value = newName,
                onValueChange = { newName = it },
                label = { Text("Nouveau nom") },
                singleLine = true
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(newName) },
                enabled = newName.isNotBlank() && newName != file.name
            ) {
                Text("Renommer")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}


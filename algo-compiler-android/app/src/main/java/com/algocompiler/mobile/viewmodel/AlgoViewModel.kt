package com.algocompiler.mobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.algocompiler.mobile.data.AlgoFile
import com.algocompiler.mobile.data.FileManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlgoViewModel : ViewModel() {
    private val fileManager = FileManager()

    private val _files = MutableStateFlow<List<AlgoFile>>(emptyList())
    val files: StateFlow<List<AlgoFile>> = _files.asStateFlow()

    private val _currentFile = MutableStateFlow<AlgoFile?>(null)
    val currentFile: StateFlow<AlgoFile?> = _currentFile.asStateFlow()

    private val _editorContent = MutableStateFlow("")
    val editorContent: StateFlow<String> = _editorContent.asStateFlow()

    private val _consoleOutput = MutableStateFlow("")
    val consoleOutput: StateFlow<String> = _consoleOutput.asStateFlow()

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    init {
        loadFiles()
    }

    fun loadFiles() {
        viewModelScope.launch {
            _files.value = fileManager.getAllFiles()
        }
    }

    fun createFile(name: String, path: String = "") {
        viewModelScope.launch {
            val file = fileManager.createFile(name, path)
            if (file != null) {
                loadFiles()
                openFile(file)
            }
        }
    }

    fun createFolder(name: String, path: String = "") {
        viewModelScope.launch {
            fileManager.createFolder(name, path)
            loadFiles()
        }
    }

    fun openFile(file: AlgoFile) {
        viewModelScope.launch {
            _currentFile.value = file
            _editorContent.value = fileManager.readFile(file) ?: ""
        }
    }

    fun updateEditorContent(content: String) {
        _editorContent.value = content
    }

    fun saveCurrentFile() {
        viewModelScope.launch {
            val file = _currentFile.value
            if (file != null) {
                fileManager.writeFile(file, _editorContent.value)
                appendToConsole("Fichier sauvegardé : ${file.name}\n")
            }
        }
    }

    fun deleteFile(file: AlgoFile) {
        viewModelScope.launch {
            fileManager.deleteFile(file)
            if (_currentFile.value == file) {
                _currentFile.value = null
                _editorContent.value = ""
            }
            loadFiles()
        }
    }

    fun renameFile(file: AlgoFile, newName: String) {
        viewModelScope.launch {
            fileManager.renameFile(file, newName)
            loadFiles()
        }
    }

    fun runAlgorithm() {
        viewModelScope.launch {
            val file = _currentFile.value
            if (file == null) {
                appendToConsole("Erreur: Aucun fichier sélectionné\n")
                return@launch
            }

            _isRunning.value = true
            clearConsole()
            appendToConsole("Exécution de ${file.name}...\n\n")

            try {
                // Save before running
                saveCurrentFile()

                // Run the algorithm
                val result = fileManager.executeAlgorithm(file)
                appendToConsole(result)
                appendToConsole("\n\nExécution terminée.\n")
            } catch (e: Exception) {
                appendToConsole("\nErreur: ${e.message}\n")
            } finally {
                _isRunning.value = false
            }
        }
    }

    fun clearConsole() {
        _consoleOutput.value = ""
    }

    private fun appendToConsole(text: String) {
        _consoleOutput.value += text
    }
}


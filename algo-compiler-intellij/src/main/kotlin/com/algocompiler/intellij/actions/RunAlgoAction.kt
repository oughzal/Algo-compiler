package com.algocompiler.intellij.actions

import com.algocompiler.intellij.AlgoFileType
import com.algocompiler.intellij.settings.AlgoSettings
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ui.Messages
import java.io.File

class RunAlgoAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        if (file.fileType != AlgoFileType) {
            return
        }

        val settings = AlgoSettings.getInstance()
        val compilerPath = settings.compilerPath.ifEmpty {
            Messages.showErrorDialog(
                project,
                "Veuillez configurer le chemin du compilateur dans Settings → Tools → Algo Compiler",
                "Compilateur non configuré"
            )
            return
        }

        // Vérifier que le JAR existe
        if (!File(compilerPath).exists()) {
            Messages.showErrorDialog(
                project,
                "Le fichier compilateur n'existe pas : $compilerPath",
                "Erreur de configuration"
            )
            return
        }

        try {
            val commandLine = GeneralCommandLine()
                .withExePath(settings.javaPath)
                .withParameters("-jar", compilerPath, file.path)
                .withWorkDirectory(file.parent.path)
                .withCharset(Charsets.UTF_8)

            val processHandler = OSProcessHandler(commandLine)
            ProcessTerminatedListener.attach(processHandler)
            processHandler.startNotify()

            Messages.showInfoMessage(
                project,
                "Exécution de ${file.name} lancée.\nVoir la console du terminal.",
                "Algo Compiler"
            )
        } catch (ex: Exception) {
            Messages.showErrorDialog(
                project,
                "Erreur lors de l'exécution : ${ex.message}",
                "Erreur d'exécution"
            )
        }
    }

    override fun update(e: AnActionEvent) {
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE)
        e.presentation.isEnabledAndVisible = file?.fileType == AlgoFileType
    }
}


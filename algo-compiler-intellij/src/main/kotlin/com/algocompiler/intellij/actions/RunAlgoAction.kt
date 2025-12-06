package com.algocompiler.intellij.actions

import com.algocompiler.intellij.AlgoFileType
import com.algocompiler.intellij.settings.AlgoSettings
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.ProcessHandlerFactory
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.execution.ui.ConsoleView
import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.execution.ui.RunContentFactory
import com.intellij.execution.ui.RunContentDescriptor
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.wm.ToolWindowId
import com.intellij.openapi.wm.ToolWindowManager

class RunAlgoAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        if (file.fileType != AlgoFileType) {
            return
        }

        val settings = AlgoSettings.getInstance()
        val compilerPath = settings.compilerPath.ifEmpty {
            // Show error notification
            return
        }

        try {
            val commandLine = GeneralCommandLine()
                .withExePath(settings.javaPath)
                .withParameters("-jar", compilerPath, file.path)
                .withCharset(Charsets.UTF_8)

            val processHandler = ProcessHandlerFactory.getInstance()
                .createColoredProcessHandler(commandLine)

            ProcessTerminatedListener.attach(processHandler)

            // Create console view
            val consoleView = com.intellij.execution.ui.ConsoleViewImpl(project, true)
            consoleView.attachToProcess(processHandler)

            val descriptor = RunContentDescriptor(
                consoleView,
                processHandler,
                consoleView.component,
                "Algo: ${file.name}"
            )

            // Show in Run tool window
            val toolWindow = ToolWindowManager.getInstance(project).getToolWindow(ToolWindowId.RUN)
            toolWindow?.let {
                RunContentFactory.SERVICE.getInstance()
                    .createRunContent(descriptor, project)
            }

            processHandler.startNotify()
        } catch (ex: Exception) {
            // Handle error
        }
    }

    override fun update(e: AnActionEvent) {
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE)
        e.presentation.isEnabledAndVisible = file?.fileType == AlgoFileType
    }
}


package com.algocompiler.intellij.run

import com.algocompiler.intellij.settings.AlgoSettings
import com.intellij.execution.Executor
import com.intellij.execution.configurations.*
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import javax.swing.JComponent
import javax.swing.JLabel

class AlgoRunConfiguration(
    project: Project,
    factory: ConfigurationFactory,
    name: String
) : RunConfigurationBase<RunProfileState>(project, factory, name) {

    var scriptPath: String = ""

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> {
        return object : SettingsEditor<AlgoRunConfiguration>() {
            override fun resetEditorFrom(config: AlgoRunConfiguration) {}
            override fun applyEditorTo(config: AlgoRunConfiguration) {}
            override fun createEditor(): JComponent = JLabel("Algo Run Configuration")
        }
    }

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState {
        return object : CommandLineState(environment) {
            override fun startProcess(): ProcessHandler {
                val settings = AlgoSettings.getInstance()
                val compilerPath = settings.compilerPath.ifEmpty {
                    throw RuntimeException("Chemin du compilateur non configuré. Allez dans Settings → Tools → Algo Compiler")
                }

                val commandLine = GeneralCommandLine()
                    .withExePath(settings.javaPath)
                    .withParameters("-jar", compilerPath, scriptPath)
                    .withCharset(Charsets.UTF_8)

                val processHandler = OSProcessHandler(commandLine)
                ProcessTerminatedListener.attach(processHandler)
                return processHandler
            }
        }
    }
}


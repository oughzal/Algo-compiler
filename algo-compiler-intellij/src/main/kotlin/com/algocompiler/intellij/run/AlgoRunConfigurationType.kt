package com.algocompiler.intellij.run

import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationType
import com.intellij.icons.AllIcons
import javax.swing.Icon

class AlgoRunConfigurationType : ConfigurationType {
    override fun getDisplayName(): String = "Algo"
    override fun getConfigurationTypeDescription(): String = "Configuration d'exécution Algo"
    override fun getIcon(): Icon = AllIcons.FileTypes.Any_type
    override fun getId(): String = "ALGO_RUN_CONFIGURATION"
    override fun getConfigurationFactories(): Array<ConfigurationFactory> = arrayOf(AlgoConfigurationFactory(this))
}

class AlgoConfigurationFactory(type: ConfigurationType) : ConfigurationFactory(type) {
    override fun getId(): String = "Algo Configuration Factory"
    override fun createTemplateConfiguration(project: com.intellij.openapi.project.Project) =
        AlgoRunConfiguration(project, this, "Algo")
}


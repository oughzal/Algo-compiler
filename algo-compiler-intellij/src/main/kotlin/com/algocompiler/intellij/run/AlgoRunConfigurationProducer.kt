package com.algocompiler.intellij.run

import com.algocompiler.intellij.AlgoFileType
import com.intellij.execution.actions.ConfigurationContext
import com.intellij.execution.actions.LazyRunConfigurationProducer
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiElement

class AlgoRunConfigurationProducer : LazyRunConfigurationProducer<AlgoRunConfiguration>() {
    override fun getConfigurationFactory(): ConfigurationFactory {
        return AlgoRunConfigurationType().configurationFactories[0]
    }

    override fun isConfigurationFromContext(
        configuration: AlgoRunConfiguration,
        context: ConfigurationContext
    ): Boolean {
        val location = context.location ?: return false
        val file = location.virtualFile ?: return false
        return file.extension == "algo" && configuration.scriptPath == file.path
    }

    override fun setupConfigurationFromContext(
        configuration: AlgoRunConfiguration,
        context: ConfigurationContext,
        sourceElement: Ref<PsiElement>
    ): Boolean {
        val location = context.location ?: return false
        val file = location.virtualFile ?: return false

        if (file.fileType != AlgoFileType) {
            return false
        }

        configuration.scriptPath = file.path
        configuration.name = file.nameWithoutExtension
        return true
    }
}


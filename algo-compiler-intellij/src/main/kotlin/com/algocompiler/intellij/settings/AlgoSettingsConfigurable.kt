package com.algocompiler.intellij.settings

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

class AlgoSettingsConfigurable : Configurable {
    private var compilerPathField: TextFieldWithBrowseButton? = null
    private var javaPathField: JBTextField? = null
    private val settings = AlgoSettings.getInstance()

    override fun createComponent(): JComponent {
        compilerPathField = TextFieldWithBrowseButton().apply {
            addBrowseFolderListener(
                "Sélectionner le compilateur Algo",
                "Chemin vers algo-compiler-1.6.0.jar",
                null,
                FileChooserDescriptorFactory.createSingleFileDescriptor("jar")
            )
        }

        javaPathField = JBTextField()

        return FormBuilder.createFormBuilder()
            .addLabeledComponent(
                JBLabel("Chemin du compilateur JAR:"),
                compilerPathField!!,
                1,
                false
            )
            .addLabeledComponent(
                JBLabel("Commande Java:"),
                javaPathField!!,
                1,
                false
            )
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    override fun isModified(): Boolean {
        return compilerPathField?.text != settings.compilerPath ||
               javaPathField?.text != settings.javaPath
    }

    override fun apply() {
        settings.compilerPath = compilerPathField?.text ?: ""
        settings.javaPath = javaPathField?.text ?: "java"
    }

    override fun reset() {
        compilerPathField?.text = settings.compilerPath
        javaPathField?.text = settings.javaPath
    }

    override fun getDisplayName(): String = "Algo Compiler"
}


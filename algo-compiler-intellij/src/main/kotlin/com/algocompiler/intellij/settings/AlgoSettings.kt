package com.algocompiler.intellij.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "AlgoSettings",
    storages = [Storage("AlgoCompiler.xml")]
)
class AlgoSettings : PersistentStateComponent<AlgoSettings> {
    var compilerPath: String = ""
    var javaPath: String = "java"

    companion object {
        fun getInstance(): AlgoSettings {
            return ApplicationManager.getApplication().getService(AlgoSettings::class.java)
        }
    }

    override fun getState(): AlgoSettings = this

    override fun loadState(state: AlgoSettings) {
        XmlSerializerUtil.copyBean(state, this)
    }
}


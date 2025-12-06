plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.20"
    id("org.jetbrains.intellij") version "1.16.1"
}

group = "com.algocompiler"
version = "1.6.0"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
intellij {
    version.set("2023.2.5")
    type.set("IC") // Target IDE Platform (IC = IntelliJ IDEA Community Edition)
    plugins.set(listOf(/* Plugin Dependencies */))
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("232")
        untilBuild.set("241.*")

        pluginDescription.set("""
            Support pour le langage de pseudo-code Algo.
            <br><br>
            <b>Fonctionnalités :</b>
            <ul>
                <li>Coloration syntaxique pour les fichiers .algo</li>
                <li>Exécution directe des fichiers avec le compilateur Algo</li>
                <li>Snippets et complétion de code</li>
                <li>Support des types : entier, réel, chaîne, caractère, booléen</li>
                <li>Support des structures : si/alors/sinon, pour, tantque, répéter</li>
                <li>Support des tableaux et matrices 2D</li>
                <li>40+ fonctions intégrées</li>
            </ul>
        """)

        changeNotes.set("""
            <b>Version 1.6.0</b>
            <ul>
                <li>Support initial du langage Algo</li>
                <li>Coloration syntaxique complète</li>
                <li>Exécution directe des fichiers .algo</li>
                <li>Intégration avec algo-compiler-1.6.0.jar</li>
            </ul>
        """)
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}


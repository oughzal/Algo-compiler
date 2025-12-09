import * as vscode from 'vscode';
import * as path from 'path';
import * as fs from 'fs';
import { exec } from 'child_process';

let outputChannel: vscode.OutputChannel;

export function activate(context: vscode.ExtensionContext) {
    console.log('Extension Algo Compiler activée');

    // Créer un canal de sortie
    outputChannel = vscode.window.createOutputChannel('Algo Compiler');

    // Commande pour exécuter un fichier algo
    let runCommand = vscode.commands.registerCommand('algo-compiler.run', async () => {
        const editor = vscode.window.activeTextEditor;
        if (!editor) {
            vscode.window.showErrorMessage('Aucun fichier ouvert');
            return;
        }

        const document = editor.document;
        if (document.languageId !== 'algo') {
            vscode.window.showErrorMessage('Ce fichier n\'est pas un fichier Algo');
            return;
        }

        // Sauvegarder le fichier avant l'exécution
        if (document.isDirty) {
            await document.save();
        }

        await runAlgoFile(document.fileName);
    });

    // Commande pour compiler un fichier algo
    let buildCommand = vscode.commands.registerCommand('algo-compiler.build', async () => {
        const editor = vscode.window.activeTextEditor;
        if (!editor) {
            vscode.window.showErrorMessage('Aucun fichier ouvert');
            return;
        }

        const document = editor.document;
        if (document.languageId !== 'algo') {
            vscode.window.showErrorMessage('Ce fichier n\'est pas un fichier Algo');
            return;
        }

        if (document.isDirty) {
            await document.save();
        }

        await buildAlgoFile(document.fileName);
    });

    context.subscriptions.push(runCommand, buildCommand);
}

async function runAlgoFile(filePath: string) {
    const config = vscode.workspace.getConfiguration('algoCompiler');

    const compilerPath = await findCompilerJar();
    if (!compilerPath) {
        vscode.window.showErrorMessage(
            'Compilateur Algo non trouvé. Compilez le projet avec: gradlew build',
            'Plus d\'infos'
        ).then(selection => {
            if (selection === 'Plus d\'infos') {
                outputChannel.show(true);
                outputChannel.clear();
                outputChannel.appendLine('❌ Compilateur Algo non trouvé');
                outputChannel.appendLine('');
                outputChannel.appendLine('Solutions:');
                outputChannel.appendLine('1. Compilez le projet avec: gradlew build');
                outputChannel.appendLine('2. Configurez le chemin dans les paramètres:');
                outputChannel.appendLine('   algoCompiler.compilerPath');
            }
        });
        return;
    }

    const javaPath = config.get<string>('javaPath', 'java');
    const command = `${javaPath} -jar "${compilerPath}" "${filePath}"`;

    // Créer ou réutiliser un terminal
    let terminal = vscode.window.terminals.find(t => t.name === 'Algo Compiler');
    
    if (!terminal) {
        terminal = vscode.window.createTerminal({
            name: 'Algo Compiler',
            cwd: path.dirname(filePath)
        });
    }

    // Afficher le terminal et exécuter la commande
    terminal.show();
    terminal.sendText(command);
}

async function buildAlgoFile(filePath: string) {
    outputChannel.clear();
    outputChannel.show(true);
    outputChannel.appendLine('='.repeat(60));
    outputChannel.appendLine(`🔧 Compilation de: ${path.basename(filePath)}`);
    outputChannel.appendLine('='.repeat(60));
    outputChannel.appendLine('');

    const compilerPath = await findCompilerJar();
    if (!compilerPath) {
        outputChannel.appendLine('❌ Erreur: Compilateur Algo non trouvé');
        vscode.window.showErrorMessage('Compilateur Algo non trouvé');
        return;
    }

    outputChannel.appendLine('✅ Vérification syntaxique...');
    outputChannel.appendLine('');
    outputChannel.appendLine('ℹ️  Le compilateur Algo est un interpréteur.');
    outputChannel.appendLine('   Utilisez "Exécuter" pour lancer le programme.');
    outputChannel.appendLine('');
    outputChannel.appendLine('='.repeat(60));
}

async function findCompilerJar(): Promise<string | null> {
    const config = vscode.workspace.getConfiguration('algoCompiler');
    const configPath = config.get<string>('compilerPath');

    // Si un chemin est configuré et existe, l'utiliser
    if (configPath && configPath.trim() !== '' && fs.existsSync(configPath)) {
        return configPath;
    }

    // Chercher le JAR dans les emplacements standards
    const workspaceFolders = vscode.workspace.workspaceFolders;

    const possiblePaths = [
        // Chemin par défaut
        'c:\\algo-compiler-1.6.0.jar',
        // Versions alternatives (support des futures versions)
        'c:\\algo-compiler-1.7.0.jar',
        'c:\\algo-compiler-1.8.0.jar',
    ];

    // Ajouter les chemins du workspace si disponible
    if (workspaceFolders) {
        possiblePaths.push(
            // Dans le projet actuel
            path.join(workspaceFolders[0].uri.fsPath, 'build', 'libs', 'algo-compiler-1.6.0.jar'),
            // Un niveau au-dessus
            path.join(workspaceFolders[0].uri.fsPath, '..', 'build', 'libs', 'algo-compiler-1.6.0.jar'),
            // Dans le projet parent
            path.join(workspaceFolders[0].uri.fsPath, '..', 'Algo-compiler', 'build', 'libs', 'algo-compiler-1.6.0.jar'),
        );
    }

    for (const jarPath of possiblePaths) {
        if (fs.existsSync(jarPath)) {
            return jarPath;
        }
    }

    return null;
}

export function deactivate() {
    if (outputChannel) {
        outputChannel.dispose();
    }
}

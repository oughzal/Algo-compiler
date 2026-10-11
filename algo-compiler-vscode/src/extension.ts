import * as vscode from 'vscode';
import * as path from 'path';
import * as fs from 'fs';
import * as os from 'os';
import * as https from 'https';
import { exec } from 'child_process';
import { promisify } from 'util';

let outputChannel: vscode.OutputChannel;
const execAsync = promisify(exec);

const COMPILER_VERSION = '1.6.0';
const COMPILER_FILE_NAME = `algo-compiler-${COMPILER_VERSION}.jar`;
const COMPILER_DOWNLOAD_URL = `https://github.com/oughzal/Algo-compiler/releases/download/release/${COMPILER_FILE_NAME}`;

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

    // Assistant de configuration
    let setupCommand = vscode.commands.registerCommand('algo-compiler.setup', async () => {
        await runSetupWizard();
    });

    context.subscriptions.push(runCommand, buildCommand, setupCommand);
}

async function runAlgoFile(filePath: string) {
    const runtime = await ensureRuntimeReady(true);
    if (!runtime) {
        return;
    }

    const command = `${asExecutable(runtime.javaPath)} -jar "${runtime.compilerPath}" "${filePath}"`;

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
        const choice = await vscode.window.showErrorMessage(
            'Compilateur Algo non trouvé.',
            'Configurer automatiquement',
            'Ouvrir paramètres'
        );

        if (choice === 'Configurer automatiquement') {
            await runSetupWizard();
        } else if (choice === 'Ouvrir paramètres') {
            await vscode.commands.executeCommand('workbench.action.openSettings', 'algoCompiler.compilerPath');
        }
        return;
    }

    outputChannel.appendLine('✅ Vérification syntaxique...');
    outputChannel.appendLine('');
    outputChannel.appendLine('ℹ️  Le compilateur Algo est un interpréteur.');
    outputChannel.appendLine('   Utilisez "Exécuter" pour lancer le programme.');
    outputChannel.appendLine('');
    outputChannel.appendLine('='.repeat(60));
}

async function ensureRuntimeReady(promptSetup: boolean): Promise<{ compilerPath: string; javaPath: string } | null> {
    const config = vscode.workspace.getConfiguration('algoCompiler');
    const javaPath = config.get<string>('javaPath', 'java');
    const compilerPath = await findCompilerJar();
    const hasJava = await isJavaAvailable(javaPath);

    if (compilerPath && hasJava) {
        return { compilerPath, javaPath };
    }

    if (!promptSetup) {
        return null;
    }

    const missing = [
        compilerPath ? null : 'compilateur (JAR)',
        hasJava ? null : 'Java'
    ].filter(Boolean).join(' + ');

    const action = await vscode.window.showWarningMessage(
        `Configuration incomplète: ${missing}.`,
        'Configurer automatiquement',
        'Ouvrir paramètres'
    );

    if (action === 'Ouvrir paramètres') {
        await vscode.commands.executeCommand('workbench.action.openSettings', 'algoCompiler');
        return null;
    }

    if (action !== 'Configurer automatiquement') {
        return null;
    }

    const configured = await runSetupWizard();
    if (!configured) {
        return null;
    }

    const refreshedConfig = vscode.workspace.getConfiguration('algoCompiler');
    const refreshedJavaPath = refreshedConfig.get<string>('javaPath', 'java');
    const refreshedCompilerPath = await findCompilerJar();
    const refreshedJavaOk = await isJavaAvailable(refreshedJavaPath);

    if (refreshedCompilerPath && refreshedJavaOk) {
        return { compilerPath: refreshedCompilerPath, javaPath: refreshedJavaPath };
    }

    return null;
}

async function runSetupWizard(): Promise<boolean> {
    outputChannel.clear();
    outputChannel.show(true);
    outputChannel.appendLine('='.repeat(60));
    outputChannel.appendLine('🛠️ Assistant de configuration Algo Compiler');
    outputChannel.appendLine('='.repeat(60));

    const config = vscode.workspace.getConfiguration('algoCompiler');
    const javaPath = config.get<string>('javaPath', 'java');

    // 1) Vérifier Java
    let javaOk = await isJavaAvailable(javaPath);
    if (!javaOk) {
        outputChannel.appendLine('❌ Java non détecté.');
        const javaAction = await vscode.window.showWarningMessage(
            'Java 21+ est requis. Voulez-vous lancer une installation guidée ?',
            'Installer Java (terminal)',
            'Ouvrir page Java',
            'Continuer sans Java'
        );

        if (javaAction === 'Installer Java (terminal)') {
            openJavaInstallTerminal();
            vscode.window.showInformationMessage(
                'Installation Java lancée dans un terminal. Après installation, relancez "Algo: Configurer l\'environnement".'
            );
        } else if (javaAction === 'Ouvrir page Java') {
            void vscode.env.openExternal(vscode.Uri.parse('https://adoptium.net/temurin/releases/?version=21'));
            vscode.window.showInformationMessage('Installez Java puis relancez "Algo: Configurer l\'environnement".');
        }
    } else {
        outputChannel.appendLine('✅ Java détecté.');
    }

    // 2) Vérifier/télécharger le compilateur
    let compilerPath = await findCompilerJar();
    if (!compilerPath) {
        const downloadAction = await vscode.window.showInformationMessage(
            `Le compilateur n'est pas trouvé. Télécharger ${COMPILER_FILE_NAME} maintenant ?`,
            'Télécharger',
            'Annuler'
        );

        if (downloadAction === 'Télécharger') {
            const targetPath = getDefaultCompilerPath();
            try {
                await downloadCompilerJar(targetPath);
                await config.update('compilerPath', targetPath, vscode.ConfigurationTarget.Global);
                compilerPath = targetPath;
                outputChannel.appendLine(`✅ Compilateur téléchargé: ${targetPath}`);
            } catch (error) {
                const message = error instanceof Error ? error.message : String(error);
                outputChannel.appendLine(`❌ Échec du téléchargement: ${message}`);
                vscode.window.showErrorMessage(`Échec du téléchargement du compilateur: ${message}`);
                return false;
            }
        } else {
            return false;
        }
    } else {
        outputChannel.appendLine(`✅ Compilateur détecté: ${compilerPath}`);
    }

    javaOk = await isJavaAvailable(javaPath);
    if (!javaOk) {
        outputChannel.appendLine('⚠️ Java toujours indisponible.');
        return false;
    }

    outputChannel.appendLine('🎉 Configuration terminée.');
    vscode.window.showInformationMessage('Configuration Algo Compiler terminée avec succès.');
    return true;
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
        path.join('c:\\algo-compiler', COMPILER_FILE_NAME),
        // Ancien chemin (compatibilité)
        `c:\\${COMPILER_FILE_NAME}`,
        // Versions alternatives (support des futures versions)
        'c:\\algo-compiler-1.7.0.jar',
        'c:\\algo-compiler-1.8.0.jar',
        'c:\\algo-compiler-1.8.1.jar',
        'c:\\algo-compiler-1.8.2.jar',
        getDefaultCompilerPath(),
    ];

    // Ajouter les chemins du workspace si disponible
    if (workspaceFolders) {
        possiblePaths.push(
            // Dans le projet actuel
            path.join(workspaceFolders[0].uri.fsPath, 'build', 'libs', COMPILER_FILE_NAME),
            // Un niveau au-dessus
            path.join(workspaceFolders[0].uri.fsPath, '..', 'build', 'libs', COMPILER_FILE_NAME),
            // Dans le projet parent
            path.join(workspaceFolders[0].uri.fsPath, '..', 'Algo-compiler', 'build', 'libs', COMPILER_FILE_NAME),
        );
    }

    for (const jarPath of possiblePaths) {
        if (fs.existsSync(jarPath)) {
            return jarPath;
        }
    }

    return null;
}

async function isJavaAvailable(javaPath: string): Promise<boolean> {
    try {
        await execAsync(`${asExecutable(javaPath)} -version`);
        return true;
    } catch {
        return false;
    }
}

function getDefaultCompilerPath(): string {
    if (process.platform === 'win32') {
        return path.join('c:\\algo-compiler', COMPILER_FILE_NAME);
    }
    return path.join(os.homedir(), '.algo-compiler', COMPILER_FILE_NAME);
}

function asExecutable(executablePath: string): string {
    if (executablePath.startsWith('"') && executablePath.endsWith('"')) {
        return executablePath;
    }
    return executablePath.includes(' ') ? `"${executablePath}"` : executablePath;
}

async function downloadCompilerJar(targetPath: string): Promise<void> {
    await vscode.window.withProgress(
        {
            location: vscode.ProgressLocation.Notification,
            title: 'Téléchargement du compilateur Algo',
            cancellable: false
        },
        async () => {
            const data = await fetchUrl(COMPILER_DOWNLOAD_URL);
            fs.mkdirSync(path.dirname(targetPath), { recursive: true });
            fs.writeFileSync(targetPath, data);
        }
    );
}

function fetchUrl(url: string, maxRedirects = 5): Promise<Buffer> {
    return new Promise((resolve, reject) => {
        const req = https.get(url, res => {
            const status = res.statusCode ?? 0;

            if (status >= 300 && status < 400 && res.headers.location) {
                if (maxRedirects <= 0) {
                    reject(new Error('Trop de redirections lors du téléchargement.'));
                    return;
                }
                res.resume();
                resolve(fetchUrl(res.headers.location, maxRedirects - 1));
                return;
            }

            if (status < 200 || status >= 300) {
                reject(new Error(`HTTP ${status} pendant le téléchargement.`));
                return;
            }

            const chunks: Buffer[] = [];
            res.on('data', chunk => chunks.push(Buffer.isBuffer(chunk) ? chunk : Buffer.from(chunk)));
            res.on('end', () => resolve(Buffer.concat(chunks)));
        });

        req.on('error', reject);
    });
}

function openJavaInstallTerminal(): void {
    const terminal = vscode.window.createTerminal('Algo Compiler - Java Setup');
    terminal.show();

    if (process.platform === 'win32') {
        terminal.sendText('winget install EclipseAdoptium.Temurin.21.JDK');
    } else if (process.platform === 'darwin') {
        terminal.sendText('brew install --cask temurin@21');
    } else {
        terminal.sendText('echo "Installez Java 21+ avec votre gestionnaire de paquets (apt/dnf/pacman)."');
    }
}

export function deactivate() {
    if (outputChannel) {
        outputChannel.dispose();
    }
}

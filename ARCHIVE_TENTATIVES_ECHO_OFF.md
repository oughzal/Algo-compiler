# 📁 ARCHIVE - Tentatives @echo off (v1.7.1 à v1.7.4)

## ⚠️ Ces modifications ont été annulées et restaurées à v1.7.0

---

## 📊 Résumé des tentatives

Toutes les tentatives pour masquer les commandes avec `@echo off` ont échoué.

| Version | Date | Méthode | Résultat |
|---------|------|---------|----------|
| v1.7.1 | 7 déc 2024 | `@echo off` direct dans PowerShell | ❌ ParserError |
| v1.7.2 | 7 déc 2024 | `cmd /c` avec guillemets `\\"` | ❌ Jarfile not found |
| v1.7.3 | 7 déc 2024 | `cmd /c` avec échappement `^"` | ❌ Jarfile not found |
| v1.7.4 | 7 déc 2024 | Fichier batch temporaire | ❌ Ne fonctionnait pas |

---

## ❌ Problèmes rencontrés

### v1.7.1 - @echo off dans PowerShell
```powershell
@echo off & java.exe -jar "..." "..."
```
**Erreur** : `ParserError: Unexpected token 'off' in expression or statement.`  
**Cause** : `@echo off` est une commande CMD, pas PowerShell

### v1.7.2 - cmd /c avec \\"
```typescript
const command = `cmd /c "@echo off & ${javaPath} -jar \\"${compilerPath}\\" \\"${filePath}\\""`;
```
**Erreur** : `Error: Unable to access jarfile \ C:\...\algo-compiler-1.6.0.jar\`  
**Cause** : Échappement incorrect des guillemets

### v1.7.3 - cmd /c avec ^"
```typescript
const command = `cmd /c "@echo off && ${javaPath} -jar ^"${compilerPath}^" ^"${filePath}^""`;
```
**Erreur** : `Error: Unable to access jarfile  C:\...\algo-compiler-1.6.0.jar`  
**Cause** : Échappement avec `^"` ne fonctionne pas dans ce contexte

### v1.7.4 - Fichier batch temporaire
```typescript
const tempDir = require('os').tmpdir();
const batchFile = path.join(tempDir, 'algo-run-' + Date.now() + '.bat');
const batchContent = `@echo off\n"${javaPath}" -jar "${compilerPath}" "${filePath}"\ndel "%~f0"`;
fs.writeFileSync(batchFile, batchContent);
terminal.sendText(`"${batchFile}"`);
```
**Problème** : Ne fonctionnait pas correctement  
**Cause** : Complexité inutile, problèmes d'exécution

---

## 🎓 Leçons apprises

1. **PowerShell ≠ CMD** : Les commandes CMD comme `@echo off` ne fonctionnent pas dans PowerShell
2. **Échappement complexe** : Les guillemets dans `cmd /c "..."` sont très difficiles à gérer
3. **Simplicité** : Parfois la solution simple est la meilleure
4. **Priorité** : Un outil qui fonctionne > un outil esthétique qui ne fonctionne pas

---

## ✅ Solution finale

**Retour à v1.7.0** - Exécution directe simple sans `@echo off`

La commande est visible mais **tout fonctionne parfaitement**.

---

## 📝 Documentation des tentatives

Tous les détails des tentatives sont documentés dans :
- `MODIFICATION_EXTENSION_V1.7.3.md` - Historique v1.7.1 à v1.7.3
- `MODIFICATION_EXTENSION_V1.7.4_FINALE.md` - Tentative v1.7.4
- `VALIDATION_FINALE_V1.7.4.md` - Validation (avant annulation)

---

## 🔄 État actuel

**Version active** : 1.7.0 (restaurée)  
**État** : Stable et fonctionnel  
**Tentatives archivées** : v1.7.1 à v1.7.4  

---

**Date d'archivage** : 7 décembre 2024  
**Raison** : Tentatives non fonctionnelles, restauration à l'état stable


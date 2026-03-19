/**
 * Algo Syntax Highlighter v2.0
 */
class AlgoHighlighter {
    constructor() {
        this.keywords = ['algorithme', 'debut', 'début', 'fin', 'si', 'alors', 'sinon', 'sinonsi', 'finsi', 'pour', 'de', 'à', 'pas', 'faire', 'finpour', 'tantque', 'fintantque', 'repeter', 'répéter', 'jusqua', "jusqu'à", 'selon', 'cas', 'defaut', 'défaut', 'finselon', 'variables', 'var', 'constantes', 'const', 'fonction', 'procedure', 'procédure', 'retourner', 'tableau', 'ref'];
        this.types = ['entier', 'reel', 'réel', 'booleen', 'booléen', 'chaine', 'chaîne', 'caractere', 'caractère'];
        this.logicalOps = ['et', 'ou', 'non', 'ET', 'OU', 'NON'];
        this.builtins = ['ecrire', 'écrire', 'ecrireln', 'écrireln', 'lire', 'abs', 'racine', 'puissance', 'arrondi', 'min', 'max', 'longueur', 'ord', 'chr', 'versEntier', 'versReel', 'versChaine'];
        this.constants = ['vrai', 'faux', 'VRAI', 'FAUX'];
    }
    escape(t) { return t.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;').replace(/'/g,'&#039;'); }
    span(c,t) { return `<span class="${c}">${t}</span>`; }
    highlightLine(line) {
        if(!line.trim()) return line;
        if(/^\s*(\/\/|#)/.test(line)) return this.span('algo-comment', this.escape(line));
        const idx = this.findCommentStart(line);
        if(idx !== -1) return this.colorCode(line.substring(0,idx)) + this.span('algo-comment', this.escape(line.substring(idx)));
        return this.colorCode(line);
    }
    findCommentStart(line) {
        let inStr=false, inChr=false, esc=false;
        for(let i=0; i<line.length; i++) {
            if(esc) { esc=false; continue; }
            if(line[i]==='\\' && (inStr||inChr)) { esc=true; continue; }
            if(line[i]==='"' && !inChr) { inStr=!inStr; continue; }
            if(line[i]==="'" && !inStr) { inChr=!inChr; continue; }
            if(!inStr && !inChr && ((line[i]==='/' && line[i+1]==='/') || line[i]==='#')) return i;
        }
        return -1;
    }
    colorCode(line) {
        const tokens=[]; let curr='', inStr=false, inChr=false, esc=false;
        for(let i=0; i<line.length; i++) {
            const c=line[i];
            if(esc) { curr+=c; esc=false; continue; }
            if(c==='\\' && (inStr||inChr)) { curr+=c; esc=true; continue; }
            if(c==='"' && !inChr) {
                if(inStr) { curr+=c; tokens.push({type:'string',value:curr}); curr=''; inStr=false; }
                else { if(curr) tokens.push({type:'text',value:curr}); curr=c; inStr=true; }
                continue;
            }
            if(c==="'" && !inStr) {
                if(inChr) { curr+=c; tokens.push({type:'char',value:curr}); curr=''; inChr=false; }
                else { if(curr) tokens.push({type:'text',value:curr}); curr=c; inChr=true; }
                continue;
            }
            curr+=c;
        }
        if(curr) tokens.push({type:inStr?'string':inChr?'char':'text',value:curr});
        let res='';
        for(const t of tokens) {
            if(t.type==='string') res+=this.span('algo-string', this.escape(t.value));
            else if(t.type==='char') res+=this.span('algo-char', this.escape(t.value));
            else res+=this.colorText(t.value);
        }
        return res;
    }
    colorText(t) {
        t = this.escape(t);
        t = t.replace(/\b(\d+\.?\d*|\.\d+)\b/g, m=>this.span('algo-number',m));
        this.constants.forEach(c=>t=t.replace(new RegExp(`\\b${c}\\b`,'g'),m=>this.span('algo-constant',m)));
        this.keywords.forEach(k=>t=t.replace(new RegExp(`\\b${k.replace(/[.*+?^${}()|[\]\\]/g,'\\$&')}\\b`,'gi'),m=>this.span('algo-keyword',m)));
        this.logicalOps.forEach(o=>t=t.replace(new RegExp(`\\b${o}\\b`,'gi'),m=>this.span('algo-operator-logical',m)));
        this.types.forEach(tp=>t=t.replace(new RegExp(`\\b${tp.replace(/[.*+?^${}()|[\]\\]/g,'\\$&')}\\b`,'gi'),m=>this.span('algo-type',m)));
        this.builtins.forEach(f=>t=t.replace(new RegExp(`\\b${f.replace(/[.*+?^${}()|[\]\\]/g,'\\$&')}\\b`,'gi'),m=>this.span('algo-builtin',m)));
        const ops=[{f:'\\*\\*',t:'**'},{f:'\\^',t:'^'},{f:'&lt;=',t:'&lt;='},{f:'&gt;=',t:'&gt;='},{f:'==',t:'=='},{f:'!=',t:'!='},{f:'&lt;&gt;',t:'&lt;&gt;'},{f:'&lt;',t:'&lt;'},{f:'&gt;',t:'&gt;'},{f:':=',t:':='},{f:'\\bDIV\\b',t:'DIV'},{f:'\\bMOD\\b',t:'MOD'},{f:'%',t:'%'},{f:'=',t:'='},{f:'\\+',t:'+'},{f:'-',t:'-'},{f:'\\*',t:'*'},{f:'/',t:'/'}];
        ops.forEach(o=>t=t.replace(new RegExp(o.f,'g'),this.span('algo-operator',o.t)));
        return t;
    }
    highlight(code) { return code.split('\n').map(l=>this.highlightLine(l)).join('\n'); }
    highlightAll() {
        const blocks=document.querySelectorAll('code.algo, pre.algo code');
        console.log(`[AlgoHighlighter] ${blocks.length} bloc(s)`);
        blocks.forEach((b,i)=>{
            const code=b.textContent||b.innerText||'';
            if(!code.trim()) return;
            console.log(`[AlgoHighlighter] Bloc ${i}...`);
            b.innerHTML=this.highlight(code);
            b.classList.add('algo-highlighted');
            console.log(`[AlgoHighlighter] ✓ Bloc ${i}`);
        });
        console.log('[AlgoHighlighter] Terminé!');
    }
}
const algoHighlighter = new AlgoHighlighter();
if(typeof document!=='undefined') {
    if(document.readyState==='loading') document.addEventListener('DOMContentLoaded',()=>algoHighlighter.highlightAll());
    else algoHighlighter.highlightAll();
}
if(typeof module!=='undefined' && module.exports) module.exports=AlgoHighlighter;

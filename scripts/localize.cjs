/* Translate parsed string literals; never replace executable source text blindly. */
const fs = require('node:fs');
const path = require('node:path');
const acorn = require('acorn');
const dir = path.join(__dirname, '../src/main/resources/dist/js');
const dictionary = require('../localization/es.json');
const check = process.argv.includes('--check');
let changed = 0, pending = [];
function walk(node, visit, parent) {
  if (!node || typeof node !== 'object') return;
  visit(node, parent);
  for (const [key, value] of Object.entries(node)) {
    if (node.type === 'TemplateElement' && key === 'value') continue;
    if (Array.isArray(value)) value.forEach(x => walk(x, visit, node));
    else if (value && typeof value === 'object') walk(value, visit, node);
  }
}
for (const file of fs.readdirSync(dir).filter(x => x.endsWith('.js'))) {
  const target = path.join(dir, file);
  let source = fs.readFileSync(target, 'utf8');
  const ast = acorn.parse(source, {ecmaVersion:'latest',sourceType:'module'});
  const edits = [];
  if (!file.startsWith('chunk-vendors') && file !== 'es-locale.js') {
    walk(ast, (node, parent) => {
      const template = node.type === 'TemplateElement';
      const value = template ? node.value.cooked : node.type === 'Literal' ? node.value : null;
      if (typeof value !== 'string' || !/[\u3400-\u9fff]/u.test(value)) return;
      // This is a response classifier, not a user-visible label. Keep the upstream
      // term so old API replies remain recognizable; Spanish failures also match below.
      if (value === '失败' && parent?.type === 'CallExpression' && parent.callee?.property?.name === 'includes') return;
      if (!(value in dictionary)) throw new Error(`${file}: Missing translation ${JSON.stringify(value)}`);
      if (check) pending.push(file + ': ' + value);
      else {
        let text = JSON.stringify(dictionary[value]);
        if (template) text = text.slice(1,-1).replace(/`/g,'\\`').replace(/\$\{/g,'\\${');
        edits.push({start:node.start,end:node.end,text});
      }
    });
  }
  for (const edit of edits.sort((a,b) => b.start-a.start)) source = source.slice(0,edit.start)+edit.text+source.slice(edit.end);
  // Parse the complete modified module before writing it.
  acorn.parse(source, {ecmaVersion:'latest',sourceType:'module'});
  if (edits.length && !check) {fs.writeFileSync(target,source);changed+=edits.length;}
}
if (pending.length) throw new Error('Pending translations: '+pending.join('\n'));
console.log(check?'Spanish interface verified':`Translated ${changed} literals and locale objects`);

grammar ILPMLgrammar2;

@header {
    package antlr4;
}

// Redéfinition des programmes
prog returns [com.paracamplus.ilp2.interfaces.IASTprogram node] 
    : (defs+=globalFunDef ';'?)*  (exprs+=expr ';'?) * EOF
    ;

// Fonction globale
globalFunDef returns [com.paracamplus.ilp2.interfaces.IASTfunctionDefinition node]
    : 'function' name=IDENT '(' vars+=IDENT? (',' vars+=IDENT)* ')'
        body=expr
    ;
 
// Expressions enrichies
expr returns [com.paracamplus.ilp1.interfaces.IASTexpression node]
// expressions de la grammaire précédente
    : '(' exprs+=expr (';'? exprs+=expr)* ';'? ')' # Sequence
    | fun=expr '(' args+=expr? (',' args+=expr)* ')' # Invocation
    | op=('-' | '!') arg=expr # Unary
    | arg1=expr op=('*' | '/' | '%') arg2=expr # Binary
    | arg1=expr op=('+' | '-') arg2=expr # Binary
    | arg1=expr op=('<' | '<=' | '>' | '>=') arg2=expr # Binary
    | arg1=expr op=('==' | '!=') arg2=expr # Binary
    | arg1=expr op='&' arg2=expr # Binary
    | arg1=expr op=('|' | '^') arg2=expr # Binary
    | 'true' # ConstTrue
    | 'false' # ConstFalse
    | intConst=INT # ConstInteger
    | floatConst=FLOAT # ConstFloat
    | stringConst=STRING # ConstString
    | var=IDENT # Variable
    | 'let' vars+=IDENT '=' vals+=expr ('and' vars+=IDENT '=' vals+=expr)*
      'in' body=expr # Binding
    | 'if' condition=expr 'then' consequence=expr 
        ('else' alternant=expr)? # Alternative

// ajouts

// affectation de variable
    | var=IDENT '=' val=expr # VariableAssign

// boucle
    | 'while' condition=expr 'do' body=expr # Loop
    ;

/*
 * Règles lexicales.
 * 
 * ANTLR impose que le nom des règles lexicales commencent par
 * une majuscule. 
 * Ces règles prennent la forme d'expressions régulières.
 */

// Identificateurs 
IDENT : [a-zA-Z_] [a-zA-Z0-9_]* ;

// Constantes entières
INT : [0-9]+ ;

// Constantes flottantes
FLOAT : [0-9]* '.' [0-9]* ;

// Constantes chaînes de caractères
STRING : '"' (ESC | ~["\\])*  '"';
ESC : '\\' [\\nrt"];

// Commentaires
LINE_COMMENT : '//' (~[\r\n])* -> skip;
COMMENT : '/*' ('*' ~[/] | ~[*])* '*/' -> skip;

// Espaces
SPACE : [ \t\r\n]+ -> skip;
 

/*
Pour utiliser une grammaire ANTLR 4:

- installer ANTLR4 SDK depuis Help -> Marketplace...

- vérifiez que dans Widnow -> Preferences -> ANTLR4 -> Tools, 
"Generate parse tree listener" est bien sélectionné.

- cliquer sur le fichier .g4 avec : click droit -> Run As -> Generate ANTLR Recognizer
(un fichier target/generated-sources/antlr4/ILPMgrammar1BaseListener est généré)

- ajouter le chemin target/generated-sources/antl4  dans les source du projet
avec click droit sur antlr4 -> Build Path -> Use as Source Folder

- ajouter antlr-4.4complete.jar

*/

grammar ILPMLgrammar1;

@header {
    package antlr4;
}

/*
 * Règles sytaxiques.
 * 
 * ANTLR impose que le nom des règles syntaxique comment par 
 * une minuscule.
 * Ces règles ont la forme "BNF".
 * Chaque règle retourne un objet Java représentant un morceau d'AST.
 * La récursivité directe à gauche est autorisée.
 */

// Structure générale d'un programme
prog returns [com.paracamplus.ilp1.interfaces.IASTprogram node] 
    : (exprs+=expr ';'?) * EOF
    ;


/*
 * Expressions
 * 
 * Seule la récursivité directe à gauche est autorisée, ce qui nous
 * oblige à fusionner tous les cas dans une unique règle.
 */
expr returns [com.paracamplus.ilp1.interfaces.IASTexpression node]

// séquence d'instructions
    : '(' exprs+=expr (';'? exprs+=expr)* ';'? ')' # Sequence

// invocation
    | fun=expr '(' args+=expr? (',' args+=expr)* ')' # Invocation

// opérations
    | op=('-' | '!') arg=expr # Unary
    | arg1=expr op=('*' | '/' | '%') arg2=expr # Binary
    | arg1=expr op=('+' | '-') arg2=expr # Binary
    | arg1=expr op=('<' | '<=' | '>' | '>=') arg2=expr # Binary
    | arg1=expr op=('==' | '!=') arg2=expr # Binary
    | arg1=expr op='&' arg2=expr # Binary
    | arg1=expr op=('|' | '^') arg2=expr # Binary
       
// constantes
    | 'true' # ConstTrue
    | 'false' # ConstFalse
    | intConst=INT # ConstInteger
    | floatConst=FLOAT # ConstFloat
    | stringConst=STRING # ConstString

// variables
    | var=IDENT # Variable

// déclaration de variable locale
    | 'let' vars+=IDENT '=' vals+=expr ('and' vars+=IDENT '=' vals+=expr)* 
      'in' body=expr # Binding
 
 // alternative (if then else)
    | 'if' condition=expr 'then' consequence=expr 
        ('else' alternant=expr)? # Alternative
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

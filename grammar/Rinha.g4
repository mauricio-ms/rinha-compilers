grammar Rinha;

compilationUnit: (term eos)* ;

eos
    : ';' ('\r'? '\n')?
    | '\r'? '\n'
    | EOF
    ;

term
    : term '(' termList? ')'
    | uop=('+' | '-') term
    | term bop=('*' | '/' | '%') term
    | term bop=('+' | '-') term
    | term bop=('<=' | '>=' | '<' | '>') term
    | term bop=('==' | '!=') term
    | term bop='&&' term
    | term bop='||' term
    | let
    | functionDefinition
    | if
    | print
    | first
    | second
    | tuple
    | literal
    | id
    | '(' term ')'
    ;

termList
    : term (',' term)* ;

let : 'let' ID '=' term eos term ;

functionDefinition
    : 'fn' '(' formalParameterList? ')' '=' '>' block ;
formalParameterList
    : ID (',' ID)* ;
block
    : '{' (term eos)* '}' ;

if : 'if' '(' term ')' then 'else' otherwise ;
then : '{' term '}' ;
otherwise : '{' term '}' ;

print
    : 'print' '(' term ')' ;

first
    : 'first' '(' term ')' ;

second
    : 'second' '(' term ')' ;

tuple
    : '(' term ',' term ')' ;

literal
    : INT
    | BOOL
    | STRING
    ;

id: ID ;

INT: DIGIT+ ;
BOOL: 'true' | 'false' ;

ID
    : '_'
    | LETTER (LETTER|DIGIT|'_')* ;

fragment
    LETTER: 'a'..'z' | 'A'..'Z' ;
fragment
    DIGIT: '0'..'9' ;

STRING: '"' (ESC | ~["\\])* '"' ;
fragment ESC : '\\' ["\\/bfnrt] ;
WS: [ \t\n\r]+ -> skip ;
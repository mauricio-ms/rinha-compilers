grammar Rinha;

compilationUnit: (term eos)* ;

formalParameterList
    : ID (',' ID)* ;

eos
    : ';'
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
    | ifStatement
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

block
    : '{' (term eos)* '}' ;

tuple
    : '(' term ',' term ')' ;

literal
    : INT
    | BOOL
    | STRING
    ;

id: ID ;

ifStatement
    : 'if' '(' term ')' then 'else' otherwise ;

then : '{' term '}' ;
otherwise : '{' term '}' ;

print
    : 'print' '(' term ')' ;

first
    : 'first' '(' term ')' ;

second
    : 'second' '(' term ')' ;

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